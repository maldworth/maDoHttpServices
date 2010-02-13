package com.maldworth.toodledo;

import com.maldworth.httpservices.DefaultRequest;
import com.maldworth.httpservices.HttpMethod;
import com.maldworth.toodledo.exceptions.*;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.request.models.*;
import com.maldworth.toodledo.response.models.*;
import com.maldworth.utils.Helpers;


public class ToodledoApi extends DefaultRequest
{
	public Credentials _credentials;
	private boolean _authenticated = false;
	private boolean _passedUserId = false;
	
	public boolean isAuthenticated()
	{
		if(Helpers.isNullOrEmpty(_credentials.getKey()))
			return false;
		else
			return true;
	}
	
	public ToodledoApi(String email, String password) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
	{
		this();
		
		_credentials = new Credentials(email, password);
		
		authenticate();
	}
	
	public ToodledoApi(String email, String password, String userid) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
	{
		this();
		
		_credentials = new Credentials(email, password, userid);
		_passedUserId = true;
		
		authenticate();
	}
	
	public ToodledoApi(String email, String password, String userid, String token) throws ArgumentNullException, ToodledoUnrecoverableException
	{
		this();
		
		_credentials = new Credentials(email, password, userid);
		_credentials.setToken(token);
		
		_authenticated = true;
	}
	
	public void authenticate(String email, String password) throws InvalidUsernameOrPassword, InvalidUserId, ArgumentNullException, ToodledoUnrecoverableException
	{
		if(_authenticated == false)
		{
			_credentials.setEmail(email);
			_credentials.setPassword(password);
			authenticate();
		}
	}
	
	public void authenticate(String email, String password, String userid) throws ArgumentNullException, InvalidUsernameOrPassword, InvalidUserId, ToodledoUnrecoverableException
	{
		if(_authenticated == false)
		{
			_credentials.setEmail(email);
			_credentials.setPassword(password);
			_credentials.setUserId(userid);
			authenticate();
		}
	}

	private void authenticate() throws InvalidUsernameOrPassword, InvalidUserId, ArgumentNullException, ToodledoUnrecoverableException
	{
		if(_authenticated == false)
		{
			UserId uid = getUserId();
			String userid = uid.getUserId();
			
			if("0".equals(userid))
			{
				throw new InvalidUsernameOrPassword("The Email or Password was blank or empty using the call '?method=getUserid'.");
			}
			else if("1".equals(userid))
			{
				throw new InvalidUsernameOrPassword("Please check the username and password, then call authorize() to attempt authorization again.");
			}
			else
			{
				if(_passedUserId && (!uid.getUserId().equalsIgnoreCase(_credentials.getUserId())) )
				{
					throw new InvalidUserId("The UserId you passed in the constructor ("+_credentials.getUserId()+") was different than the one retrieved from Toodledo ("+uid.getUserId()+"). ");
				}
//				System.out.println("Userid: "+uid.getUnParsedEntity());
				_credentials.setUserId(userid);
			}
		}
		Token token = getToken();
		
		if(token.isError())
		{
			throw new ToodledoUnrecoverableException("Too many syncs within the last hour, please wait before synchronizing again");
		}
//		System.out.println(token.getUnParsedEntity());
		_credentials.setToken(token.getToken());
		System.out.println("Token: "+ token.getToken());
		_authenticated = true;
	}
	
	private ToodledoApi()
	{
		super("http","api.toodledo.com","api.php");
		
		//They accept & or ;
		//this.replaceQueryDelimiter(";");
		this.setHttpMethod(HttpMethod.POST);
	}
	
	/**
	 * This can be used when the developer wants to re-establish a Token with Toodledo. Therefore, this is usually only called when the 4 hour token has expired (or is just about to). After calling this method, you must call an authenticate() method.
	 * @param reEstablishAuthentication Set this to True, if you want to re-authenticate using the existing credentials. Otherwise False, if you want to use a different username and password.
	 */
	public void removeAuthentication(boolean reEstablishAuthentication)	{
		if(_authenticated == true)
		{
			_authenticated = false;
			
			if(reEstablishAuthentication == true)
			{
				//This should only be entered when we know there was a previous Legitimate authentication, thus we can just call authenticate() as we know the credentials are good.
				
				//These exceptions are essentially swallowed here because I know that they shouldn't be thrown, as the authentication worked earlier with the same credentials.
				//Thus there is no reason for them to be thrown.
				try {
					authenticate();
				} catch (InvalidUsernameOrPassword e) {
					e.printStackTrace();
				} catch (InvalidUserId e) {
					e.printStackTrace();
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				} catch (ToodledoUnrecoverableException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public UserId getUserId()
	{
		RequestUserId ruid = new RequestUserId(_credentials);
		return execute(new UserId(), ruid.getQueryParams());
	}

	public Token getToken()
	{
		RequestToken rtok = new RequestToken(_credentials);
		return execute(new Token(), rtok.getQueryParams());
	}

	public AccountInfo getAccountInfo()
	{
		BasicToodledoRequestModel acctinfo = new BasicToodledoRequestModel("getAccountInfo", _credentials, true);
		return execute(new AccountInfo(), acctinfo.getQueryParams());
	}
	
	public ServerInfo getServerInfo()
	{
		BasicToodledoRequestModel serverinfo = new BasicToodledoRequestModel("getServerInfo", _credentials);
		return execute(new ServerInfo(), serverinfo.getQueryParams());
	}
	
	//################# Untested below here.
	public Folders getFolders()
	{
		BasicToodledoRequestModel folders = new BasicToodledoRequestModel("getFolders", _credentials);
		return execute(new Folders(), folders.getQueryParams());
	}
	
	public Contexts getContexts()
	{
		BasicToodledoRequestModel contexts = new BasicToodledoRequestModel("getContexts", _credentials);
		return execute(new Contexts(), contexts.getQueryParams());
	}
	
	public Goals getGoals()
	{
		BasicToodledoRequestModel goals = new BasicToodledoRequestModel("getGoals", _credentials);
		return execute(new Goals(), goals.getQueryParams());
	}
	
	public GoalNotes getGoalNotes()
	{
		BasicToodledoRequestModel goalNotes = new BasicToodledoRequestModel("getGoalNotes", _credentials);
		return execute(new GoalNotes(), goalNotes.getQueryParams());
	}
	
	public Added addFolder(AddFolder addFolder)
	{
		 addFolder.setCredentials(_credentials);
		 return execute(new Added(), addFolder.getQueryParams());
	}
	
	public Added addContext(AddContext addContext)
	{
		addContext.setCredentials(_credentials);
		
		return execute(new Added(), addContext.getQueryParams());
	}
	
	public Added addGoal(AddGoal addGoal)
	{
		addGoal.setCredentials(_credentials);
		return execute(new Added(), addGoal.getQueryParams());
	}
	
	public Success editFolder(EditFolder editFolder)
	{
		editFolder.setCredentials(_credentials);
		return execute(new Success(), editFolder.getQueryParams());
	}
	
	public Success editContext(EditContext editContext)
	{
		editContext.setCredentials(_credentials);
		return execute(new Success(), editContext.getQueryParams());
	}
	
	public Success editGoal(EditGoal editGoal)
	{
		editGoal.setCredentials(_credentials);
		return execute(new Success(), editGoal.getQueryParams());
	}
	
	public Success deleteFolder(DeleteFolder deleteFolder)
	{
		deleteFolder.setCredentials(_credentials);
		return execute(new Success(), deleteFolder.getQueryParams());
	}
	
	public Success deleteContext(DeleteContext deleteContext)
	{
		deleteContext.setCredentials(_credentials);
		return execute(new Success(), deleteContext.getQueryParams());
	}
	
	public Success deleteGoal(DeleteGoal deleteGoal)
	{
		deleteGoal.setCredentials(_credentials);
		return execute(new Success(), deleteGoal.getQueryParams());
	}
	
	public Success deleteTask(DeleteTask deleteTask)
	{
		deleteTask.setCredentials(_credentials);
		return execute(new Success(), deleteTask.getQueryParams());
	}
	
	public DeletedTasks getDeleted(GetDeleted getDeleted)
	{
		getDeleted.setCredentials(_credentials);
		return execute(new DeletedTasks(), getDeleted.getQueryParams());
	}
	
	//TODO test if calling getQueryParams twice, will be smart enough not to add that param again
	public Tasks getTasks(GetTasks getTasks)
	{
		getTasks.setCredentials(_credentials);
		return execute(new Tasks(), getTasks.getQueryParams());
	}
	
	
	public Added addTask(AddTask addTask)
	{
		addTask.setCredentials(_credentials);
		return execute(new Added(), addTask.getQueryParams());
	}
	
	public Success editTask(EditTask editTask)
	{
		editTask.setCredentials(_credentials);
		return execute(new Success(), editTask.getQueryParams());
	}
	
	public DeletedNotes getDeletedNotes(GetDeletedNotes getDeletedNotes)
	{
		getDeletedNotes.setCredentials(_credentials);
		return execute(new DeletedNotes(), getDeletedNotes.getQueryParams());
	}
	
	public Success deleteNote(DeleteNote deleteNote)
	{
		deleteNote.setCredentials(_credentials);
		return execute(new Success(), deleteNote.getQueryParams());
	}
	
	public Success editNote(EditNote editNote)
	{
		editNote.setCredentials(_credentials);
		return execute(new Success(), editNote.getQueryParams());
	}
	
	public Success addNote(AddNote addNote)
	{
		addNote.setCredentials(_credentials);
		return execute(new Success(), addNote.getQueryParams());
	}
	
	public Notes getNotes(GetNotes getNotes)
	{
		getNotes.setCredentials(_credentials);
		return execute(new Notes(), getNotes.getQueryParams());
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
