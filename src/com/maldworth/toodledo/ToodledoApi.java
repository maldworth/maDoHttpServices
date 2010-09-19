package com.maldworth.toodledo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.maldworth.httpservices.DefaultRequest;
import com.maldworth.httpservices.HttpMethod;
import com.maldworth.toodledo.exceptions.*;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.request.models.*;
import com.maldworth.toodledo.response.ToodledoResponseModelBase;
import com.maldworth.toodledo.response.models.*;
import com.maldworth.utils.Helpers;


public class ToodledoApi extends DefaultRequest
{
//	public Credentials _credentials;
	
	private String _email;
	private String _password;//This will always be saved in MD5
	private String _userId;
	private String _token; //4 hour token
//	private DateTime _tokenLease; //Time the lease was obtained
	private String _key; //Hashed key
	
	private boolean _authenticated = false;
	
	public boolean isAuthenticated()
	{
		return _authenticated;
	}
	
	public void Authenticate(String email, String password) throws InvalidUsernameOrPassword, ToodledoUnrecoverableException
	{		
		UserId uid = getUserId(email, password);
		
		//This would only be thrown if toodledo api changes it's getUserId call. Check toodledo documentation
		if(uid.isError() == true)
			throw new ToodledoUnrecoverableException("An unknown error was returned by the getUserId method in ToodledoApi: { " + uid.errorMessage() + " }");
		
		//If the userid is == 1, then it was a bad username or password.
		if(uid.getUserId().length() < 2)
			throw new InvalidUsernameOrPassword(null);
		
		//We assign email, password, and userId, as they are legitimate
		_email = email;
		_password = password;
		_userId = uid.getUserId();
		
		Authenticate(_email, _password, _userId, false);
	}
	
	
	/**
	 * @param email
	 * @param password Can be plaintext or md5 hashed. The reason is, if we are absolutely sure the userId is correct, then we can obtain a token and generate the key for communication
	 * @param userId
	 * @throws ToodledoUnrecoverableException 
	 * @throws InvalidUsernameOrPassword 
	 */
	public void Authenticate(String email, String password, String userId, boolean passIsMD5) throws ToodledoUnrecoverableException, InvalidUsernameOrPassword
	{
		//This means we have a token, but the getAccountInfo, so the userId could be bad, and we must
		//If we know it was a legit token that was used before, than we would have set it blank and will enter
		if(Helpers.isNullOrEmptyOrBlank(_token) == false)
		{
			//This is the case where we already fetched a token once, and then getAccountInfo failed.
			//So we have 2 cases
			//1. The userId is somebody elses (you can theoretically fetch tokens for anybodies account if you know their User ID
			//2. The userId is yours, but your password has likely changed.
			//3. The third case which it could have been was that the token has simply expired, but then we would have cleared the _token, so it doesn't enter here.
			if(passIsMD5 == false)
				Authenticate(email, password);//This starts from scratch, verifying the UserID is correct. 
			else
				throw new ToodledoUnrecoverableException("Token was requested and we received one. However a key was generated and used with getAccountInfo, which failed. So the password is likely incorrect, but we couldn't verify the UserID because the password was passed in MD5. Please pass it in plaintext and try again.");
		}
		else
		{
			
			Token tok = getToken(userId);
			
			if(tok.isError() == true)
			{
//				System.out.println(tok.getUnParsedEntity());
				//The userid is bad
				if(tok.errorMessage().contains("invalid userid"))
				{
					if(passIsMD5 == true)
					{
						throw new ToodledoUnrecoverableException("Token requested with an invalid userId, and the password was given in MD5, so we cannot fetch the userId for you. You can double check your userId, or call the method with a plaintext password.");
					} else {
						//We can call the original authenticate
//						System.out.println("Getting the userid that should be good");
						Authenticate(email, password);
						return;//Because Authenticate(email,password) had already called this method, so we needn't go through finishing the call.
					}
				} else {
					throw new ToodledoUnrecoverableException("An unknown error was returned by the getToken method in ToodledoApi: {" + tok.errorMessage() + "}");
				}
			}
			else
			{
				_userId = userId;		//We know this is a legit userId
				_token = tok.getToken();//And this is the matching token
				_email = email;			//But we don't know if this email because this method could have been called externally
			}
			
			Authenticate(_email, password, _userId, _token, passIsMD5);
		}
	}
	
	public void Authenticate(String email, String password, String userId, String token, boolean passIsMD5) throws ToodledoUnrecoverableException, InvalidUsernameOrPassword
	{
		String key = generateKey(password, userId, token, passIsMD5);
		
		AccountInfo ai = getAccountInfo(key);
		
		if(ai.isError() == true)
		{
			System.out.println("Error trying getAccountInfo");
			_token = null;//This will ensure that we first try to get the token once more, and if that fails we revert back to checking the UserID and password
			Authenticate(email, password, userId, passIsMD5);
//			if(passIsMD5 == true)
		}
		else
		{
			//Lets save all variables, because if it made it this far, we can assume they are all legit. Although saving them is somewhat useless, as all we need saved is the key.
			_email = email;
			_userId = userId;
			_token = token;
			_key = key;
			System.out.println(_token);
			
			if(passIsMD5 == true)
			{
				_password = password;	//and if this password, are the corresponding account of the userId, until we make the key and try it.
			} else {
				try {
					_password = Helpers.md5(password);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					throw new ToodledoUnrecoverableException("There was an error generating the md5 hash of the password, please try again: {" + e.getMessage() +"}");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new ToodledoUnrecoverableException("There was an error generating the md5 hash of the password, please try again: {" + e.getMessage() +"}");
				}
			}
			
			_authenticated = true;
		}
	}
	
	public void Authenticate(String passwordInMD5, String userId, String token) throws ToodledoUnrecoverableException
	{
		_key = generateKey(passwordInMD5, userId, token, true);
		
		_authenticated = true;
		
		//Nothing further, we assume this key is legit.
	}
	
	private String generateKey(String password, String userId, String token, boolean passIsMD5) throws ToodledoUnrecoverableException
	{
		String pass = password;
		
		try 
		{
			if(passIsMD5 == false)
				pass = Helpers.md5(password);
			return Helpers.md5(pass + token + userId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new ToodledoUnrecoverableException("Error converting the password, token and userId into the md5 [Unsupported Algorithm] hashed secret Key. This is only due to a serious error, let maldworth know.\n");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ToodledoUnrecoverableException("Error converting the md5 hashed secret into a UFT-8 [Unsupported Encoding] acceptable string. Please let maldworth know.\n");
		}
	}

//	private void Authenticate()
//	{
//		
//	}
	
//	public ToodledoApi(String email, String password) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
//	{
//		this();
//		
//		_credentials = new Credentials(email, password);
//		
//		authenticate();
//	}
//	
//	public ToodledoApi(String email, String password, String userid) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
//	{
//		this();
//		
//		_credentials = new Credentials(email, password, userid);
//		_passedUserId = true;
//		
//		authenticate();
//	}
//	
//	public ToodledoApi(String email, String password, String userid, String token) throws ArgumentNullException, ToodledoUnrecoverableException
//	{
//		this();
//		
//		_credentials = new Credentials(email, password, userid);
//		_credentials.setToken(token);
//		
//		_authenticated = true;
//	}
	
//	public void authenticate(String email, String password) throws InvalidUsernameOrPassword, InvalidUserId, ArgumentNullException, ToodledoUnrecoverableException
//	{
//		if(_authenticated == false)
//		{
//			_credentials.setEmail(email);
//			_credentials.setPassword(password);
//			authenticate();
//		}
//	}
//	
//	public void authenticate(String email, String password, String userid) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
//	{
//		if(_authenticated == false)
//		{
//			_credentials.setEmail(email);
//			_credentials.setPassword(password);
//			_credentials.setUserId(userid);
//			authenticate();
//		}
//	}
//
//	private void authenticate() throws InvalidUsernameOrPassword, InvalidUserId, ArgumentNullException, ToodledoUnrecoverableException
//	{
//		if(_authenticated == false)
//		{
//			UserId uid = getUserId();
//			String userid = uid.getUserId();
//			
//			if("0".equals(userid))
//			{
//				throw new InvalidUsernameOrPassword("The Email or Password was blank or empty using the call '?method=getUserid'.");
//			}
//			else if("1".equals(userid))
//			{
//				throw new InvalidUsernameOrPassword("Please check the username and password, then call authorize() to attempt authorization again.");
//			}
//			else
//			{
//				if(_passedUserId && (!uid.getUserId().equalsIgnoreCase(_credentials.getUserId())) )
//				{
//					throw new InvalidUserId("The UserId you passed in the constructor ("+_credentials.getUserId()+") was different than the one retrieved from Toodledo ("+uid.getUserId()+"). ");
//				}
////				System.out.println("Userid: "+uid.getUnParsedEntity());
//				_credentials.setUserId(userid);
//			}
//		}
//		Token token = getToken();
//		
//		if(token.isError())
//		{
//			throw new ToodledoUnrecoverableException("Too many syncs within the last hour, please wait before synchronizing again");
//		}
////		System.out.println(token.getUnParsedEntity());
//		_credentials.setToken(token.getToken());
//		System.out.println("Token: "+ token.getToken());
//		_authenticated = true;
//	}
	
	public ToodledoApi()
	{
		super("http","api.toodledo.com","api.php");
		
		//They accept & or ;
		//this.replaceQueryDelimiter(";");
		this.setHttpMethod(HttpMethod.POST);
	}
	
//	/**
//	 * This can be used when the developer wants to re-establish a Token with Toodledo. Therefore, this is usually only called when the 4 hour token has expired (or is just about to). After calling this method, you must call an authenticate() method.
//	 * @param reEstablishAuthentication Set this to True, if you want to re-authenticate using the existing credentials. Otherwise False, if you want to use a different username and password.
//	 */
//	public void removeAuthentication(boolean reEstablishAuthentication)	{
//		if(_authenticated == true)
//		{
//			_authenticated = false;
//			
//			if(reEstablishAuthentication == true)
//			{
//				//This should only be entered when we know there was a previous Legitimate authentication, thus we can just call authenticate() as we know the credentials are good.
//				
//				//These exceptions are essentially swallowed here because I know that they shouldn't be thrown, as the authentication worked earlier with the same credentials.
//				//Thus there is no reason for them to be thrown.
//				try {
//					authenticate();
//				} catch (InvalidUsernameOrPassword e) {
//					e.printStackTrace();
//				} catch (InvalidUserId e) {
//					e.printStackTrace();
//				} catch (ArgumentNullException e) {
//					e.printStackTrace();
//				} catch (ToodledoUnrecoverableException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	private final <T extends ToodledoResponseModelBase> T performExecution(T responseType, BasicToodledoRequestModel queryParams)
	{
		T response = execute(responseType, queryParams.getQueryParams());
		
		if(checkResponseKeyValidity(response) == false)
		{
			_authenticated = false;
			try {
				Authenticate(_email, _password, _userId, true);
			} catch (InvalidUsernameOrPassword e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ToodledoUnrecoverableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = execute(responseType, queryParams.getQueryParams());
		}
		
		return response;
	}
	
	private final <T extends ToodledoResponseModelBase> boolean checkResponseKeyValidity(T response)
	{
		if(response.isError() == false)
			return true;
		
		if(response.errorMessage().contentEquals("key did not validate"))
		{
			//Reauthenticate, then return false so the method knows to call it again.
			/*_authenticated = false;
			
			try {
				Authenticate(_email, _password, _userId, true);
			} catch (InvalidUsernameOrPassword e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ToodledoUnrecoverableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			return false;
		}
		
		//This will typically return when an error still occurred, however it isn't a "key did not validate", so we want to return that error to the user
		return true;
	}
	
	//These methods don't use performExecute because they aren't using keys
	public UserId getUserId()
	{
		GetUserId ruid = new GetUserId(_email, _password);
		return execute(new UserId(), ruid.getQueryParams());
	}
	
	public UserId getUserId(String email, String password)
	{
		GetUserId ruid = new GetUserId(email, password);
		return execute(new UserId(), ruid.getQueryParams());
	}

	public Token getToken()
	{
		System.out.println("Called gettoken");
		GetToken rtok = new GetToken(_userId);
		return execute(new Token(), rtok.getQueryParams());
	}
	
	public Token getToken(String userId)
	{
		System.out.println("Called gettoken(string)");
		GetToken rtok = new GetToken(userId);
		return execute(new Token(), rtok.getQueryParams());
	}
	
	protected AccountInfo getAccountInfo(String key)
	{
		BasicToodledoRequestModel acctinfo = new BasicToodledoRequestModel("getAccountInfo", key, true);
		return execute(new AccountInfo(), acctinfo.getQueryParams());
	}

	public AccountInfo getAccountInfo()
	{
		BasicToodledoRequestModel acctinfo = new BasicToodledoRequestModel("getAccountInfo", _key, true);
		return performExecution(new AccountInfo(), acctinfo);
	}
	
	public ServerInfo getServerInfo()
	{
		BasicToodledoRequestModel serverinfo = new BasicToodledoRequestModel("getServerInfo", _key);
		return performExecution(new ServerInfo(), serverinfo);
	}
	
	//################# Untested below here.
	public Folders getFolders()
	{
		BasicToodledoRequestModel folders = new BasicToodledoRequestModel("getFolders", _key);
		return performExecution(new Folders(), folders);
	}
	
	public Contexts getContexts()
	{
		BasicToodledoRequestModel contexts = new BasicToodledoRequestModel("getContexts", _key);
		return performExecution(new Contexts(), contexts);
	}
	
	public Goals getGoals()
	{
		BasicToodledoRequestModel goals = new BasicToodledoRequestModel("getGoals", _key);
		return performExecution(new Goals(), goals);
	}
	
	public GoalNotes getGoalNotes()
	{
		BasicToodledoRequestModel goalNotes = new BasicToodledoRequestModel("getGoalNotes", _key);
		return performExecution(new GoalNotes(), goalNotes);
	}
	
	public Added addFolder(AddFolder addFolder)
	{
		 addFolder.setKey(_key);
		 return performExecution(new Added(), addFolder);
	}
	
	public Added addContext(AddContext addContext)
	{
		addContext.setKey(_key);
		return performExecution(new Added(), addContext);
	}
	
	public Added addGoal(AddGoal addGoal)
	{
		addGoal.setKey(_key);
		return performExecution(new Added(), addGoal);
	}
	
	public Success editFolder(EditFolder editFolder)
	{
		editFolder.setKey(_key);
		return performExecution(new Success(), editFolder);
	}
	
	public Success editContext(EditContext editContext)
	{
		editContext.setKey(_key);
		return performExecution(new Success(), editContext);
	}
	
	public Success editGoal(EditGoal editGoal)
	{
		editGoal.setKey(_key);
		return performExecution(new Success(), editGoal);
	}
	
	public Success deleteFolder(DeleteFolder deleteFolder)
	{
		deleteFolder.setKey(_key);
		return performExecution(new Success(), deleteFolder);
	}
	
	public Success deleteContext(DeleteContext deleteContext)
	{
		deleteContext.setKey(_key);
		return performExecution(new Success(), deleteContext);
	}
	
	public Success deleteGoal(DeleteGoal deleteGoal)
	{
		deleteGoal.setKey(_key);
		return performExecution(new Success(), deleteGoal);
	}
	
	public Success deleteTask(DeleteTask deleteTask)
	{
		deleteTask.setKey(_key);
		return performExecution(new Success(), deleteTask);
	}
	
	public DeletedTasks getDeleted(GetDeleted getDeleted)
	{
		getDeleted.setKey(_key);
		return performExecution(new DeletedTasks(), getDeleted);
	}
	
	//TODO test if calling getQueryParams twice, will be smart enough not to add that param again
	public Tasks getTasks(GetTasks getTasks)
	{
		getTasks.setKey(_key);
		return performExecution(new Tasks(), getTasks);
	}
	
	
	public Added addTask(AddTask addTask)
	{
		addTask.setKey(_key);
		return performExecution(new Added(), addTask);
	}
	
	public Success editTask(EditTask editTask)
	{
		editTask.setKey(_key);
		return performExecution(new Success(), editTask);
	}
	
	public DeletedNotes getDeletedNotes(GetDeletedNotes getDeletedNotes)
	{
		getDeletedNotes.setKey(_key);
		return performExecution(new DeletedNotes(), getDeletedNotes);
	}
	
	public Success deleteNote(DeleteNote deleteNote)
	{
		deleteNote.setKey(_key);
		return performExecution(new Success(), deleteNote);
	}
	
	public Success editNote(EditNote editNote)
	{
		editNote.setKey(_key);
		return performExecution(new Success(), editNote);
	}
	
	public Success addNote(AddNote addNote)
	{
		addNote.setKey(_key);
		return performExecution(new Success(), addNote);
	}
	
	public Notes getNotes(GetNotes getNotes)
	{
		getNotes.setKey(_key);
		return performExecution(new Notes(), getNotes);
	}
	
	//CreateAccount Stuff
	public static UserId createAccount(CreateAccount createAccount)
	{
		return new CreateAccountRequest().execute(new UserId(), createAccount.getQueryParams());
	}
	
	public static class CreateAccountRequest extends DefaultRequest
	{
		public CreateAccountRequest() {
			super("http", "api.toodledo.com", "api.php");
			
		}
	}
}
