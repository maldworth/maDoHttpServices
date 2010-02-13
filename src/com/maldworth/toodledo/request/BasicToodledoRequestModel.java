package com.maldworth.toodledo.request;

import org.apache.http.NameValuePair;

import com.maldworth.httpservices.DefaultRequestModelBase;
import com.maldworth.toodledo.Credentials;

public class BasicToodledoRequestModel extends DefaultRequestModelBase
{
	public BasicToodledoRequestModel(String method, Credentials credentials)
	{
		super("method", method);
		
		if(credentials != null)
			setCredentials(credentials);
	}
	
	public BasicToodledoRequestModel(String method, Credentials credentials, boolean unixTime)
	{
		super("method", method);
		
		if(credentials != null)
			setCredentials(credentials);
		
		if(unixTime == true)
			this.addParam("unix", unixTime);
	}
	
	public void setCredentials(Credentials credentials)
	{
		this.addParam("key", credentials.getKey());
	}
	
	protected boolean hasKey()
	{
		for(NameValuePair nvp : super.getQueryParams())
		{
			if("key".equals(nvp.getName()))
			{
				return true;
			}
		}
		return false;
	}
}
