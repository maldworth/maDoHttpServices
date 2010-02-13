package com.maldworth.toodledo.request.models;

import com.maldworth.httpservices.DefaultRequestModelBase;
import com.maldworth.utils.Helpers;

public class CreateAccount extends DefaultRequestModelBase
{
	private final String _email;
	private final String _password;
	
	public CreateAccount(String email, String password)
	{
		super("method","createAccount");
		
		_email = email;
		_password = password;
		
		this.addParam("email", _email);
		this.addParam("pass", Helpers.encode(_password));
	}

	public String getEmail() {
		return _email;
	}

	public String getPassword() {
		return _password;
	}
}
