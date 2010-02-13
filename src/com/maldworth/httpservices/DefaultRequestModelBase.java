package com.maldworth.httpservices;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public abstract class DefaultRequestModelBase
{
	private final List<NameValuePair> _queryParams;
	
	public DefaultRequestModelBase(String key, String value)
	{
		_queryParams = new ArrayList<NameValuePair>();
		addParam(key, value);
	}
	
	public List<NameValuePair> getQueryParams()
	{
		return _queryParams;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return True if successfully added, False if the key already exists in the List
	 */
	protected final boolean addParam(String key, String value)
	{
		for(NameValuePair nvp : _queryParams)
		{
			if(key.equals(nvp.getName()))
			{
				return false;
			}
		}
		_queryParams.add(new BasicNameValuePair(key,value));
		return true;
	}
	
	protected final boolean addParam(String key, int value)
	{
		return addParam(key, String.valueOf(value));
	}
	
	protected final boolean addParam(String key, boolean value)
	{
		return addParam(key, ((value)?"1":"0") );
	}
	
	protected final boolean addParam(String key, long value)
	{
		return addParam(key, String.valueOf(value));
	}
}
