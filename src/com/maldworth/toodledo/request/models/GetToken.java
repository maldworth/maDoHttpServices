package com.maldworth.toodledo.request.models;

import com.maldworth.httpservices.DefaultRequestModelBase;

public class GetToken extends DefaultRequestModelBase
{
	public GetToken(String userId)
	{
		super("method", "getToken");
		this.addParam("userid", userId);
	}
}
