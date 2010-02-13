package com.maldworth.toodledo.request.models;

import com.maldworth.httpservices.DefaultRequestModelBase;
import com.maldworth.toodledo.Credentials;

public class RequestUserId extends DefaultRequestModelBase
{
	public RequestUserId(Credentials credentials)
	{
		super("method", "getUserid");
		this.addParam("email", credentials.getEmail());
		this.addParam("pass", credentials.getPassword());
	}
}
