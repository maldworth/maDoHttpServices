package com.maldworth.toodledo.request.models;

import com.maldworth.httpservices.DefaultRequestModelBase;

public class GetUserId extends DefaultRequestModelBase
{
	public GetUserId(String email, String password)
	{
		super("method", "getUserid");
		this.addParam("email", email);
		this.addParam("pass", password);
	}
}
