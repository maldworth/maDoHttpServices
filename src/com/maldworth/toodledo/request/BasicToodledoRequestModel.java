package com.maldworth.toodledo.request;

import org.apache.http.NameValuePair;

import com.maldworth.httpservices.DefaultRequestModelBase;
import com.maldworth.utils.Helpers;

public class BasicToodledoRequestModel extends DefaultRequestModelBase
{
	public BasicToodledoRequestModel(String method, String key)
	{
		this(method, key, false);
	}
	
	public BasicToodledoRequestModel(String method, String key, boolean unixTime)
	{
		super("method", method);
		
		if(Helpers.isNullOrEmpty(key) == false)
			setKey(key);
		
		if(unixTime == true)
			this.addParam("unix", unixTime);
	}
	
	public void setKey(String key)
	{
		this.addParam("key", key);
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
