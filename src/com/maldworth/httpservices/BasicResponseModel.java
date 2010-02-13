package com.maldworth.httpservices;

import org.apache.http.impl.client.BasicResponseHandler;

public abstract class BasicResponseModel extends DefaultResponseModelBase<String>
{
	public BasicResponseModel()
	{
		super(new BasicResponseHandler());
	}
}
