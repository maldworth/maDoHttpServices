package com.maldworth.toodledo.request.models;

import com.maldworth.httpservices.DefaultRequestModelBase;
import com.maldworth.toodledo.Credentials;

public class RequestToken extends DefaultRequestModelBase
{
	public RequestToken(Credentials credentials)
	{
		super("method", "getToken");
		this.addParam("userid", credentials.getUserId());
	}
}
