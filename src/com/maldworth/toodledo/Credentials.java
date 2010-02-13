package com.maldworth.toodledo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.joda.time.DateTime;

import com.maldworth.toodledo.exceptions.ArgumentNullException;
import com.maldworth.toodledo.exceptions.ToodledoUnrecoverableException;
import com.maldworth.utils.Helpers;

public class Credentials
{
	private String _email;
	private String _password;
	private String _userId;
	private String _token; //4 hour token
	private DateTime _tokenLease; //Time the lease was obtained
	private String _key; //Hashed key

	/**
	 * @return the _key
	 */
	public String getKey()
	{
		return _key;
	}
	
	public String getUserId()
	{
		return _userId;
	}

	/**
	 * @param Sets the uniqueId for the instance. The uniqueId should be 15 to 16 characters in length.
	 * @throws ArgumentNullException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void setUserId(String userId) throws ArgumentNullException
	{
		if(Helpers.isNullOrEmptyOrBlank(userId))
			throw new ArgumentNullException("Oops, you forgot to provide a uniqueId");
		
		if(userId.length() != 15 && userId.length() != 16)
			throw new ArgumentNullException("Oops, uniqueId should be only 15 or 16 characters long.");
		
		_userId = userId;
	}
	
	public void setEmail(String email)
	{
		_email = email;
	}
	
	public void setPassword(String password)
	{
		_password = password;
	}
	
	public String getToken()
	{
		return _token;
	}

	public void setToken(String token) throws ToodledoUnrecoverableException 
	{
		//TODO verify token is not null and stuff
		//TODO also if _tokenExpire is set, then dono't allow this to be set unless the time is under 5 minutes until expirey
		_token = token;
		_tokenLease = new DateTime().minusMinutes(10);//So we know to get a new key slightly in advance.
		generateKey();
	}

	public Credentials(String email, String password) throws ArgumentNullException
	{
		if(Helpers.isNullOrEmptyOrBlank(email))
			throw new ArgumentNullException("Oops, you forgot to provide an e-mail.");
		
		if(Helpers.isNullOrEmptyOrBlank(password))
			throw new ArgumentNullException("Oops, you forgot to provide a password.");
		
		//TODO possibly remove those? because toodledo will throw back error if they are blank.
		
		_email = email;
		_password = password;
	}
	
	public Credentials(String email, String password, String uniqueId) throws ArgumentNullException
	{		
		if(Helpers.isNullOrEmptyOrBlank(email))
			throw new ArgumentNullException("Oops, you forgot to provide an e-mail.");
		
		//TODO if(is malformed email) Add a check for a legit e-mail
		
		if(Helpers.isNullOrEmptyOrBlank(password))
			throw new ArgumentNullException("Oops, you forgot to provide a password.");
		
		_email = email;
		_password = password;
		setUserId(uniqueId);
	}
	
	private void generateKey() throws ToodledoUnrecoverableException
	{
		try {
			_key = Helpers.md5(Helpers.md5(_password) + _token + _userId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new ToodledoUnrecoverableException("Error converting the password, token and userId into the md5 [Unsupported Algorithm] hashed secret Key. This is only due to a serious error, let maldworth know.\n");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ToodledoUnrecoverableException("Error converting the md5 hashed secret into a UFT-8 [Unsupported Encoding] acceptable string. Please let maldworth know.\n");
		}
	}

	public String getEmail() {
		return _email;
	}

	public String getPassword() {
		return _password;
	}

	/**
	 * @return The time at which the token was created
	 */
	public DateTime getTokenLease() {
		return _tokenLease;
	}
}
