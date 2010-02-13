package com.maldworth.httpservices;

public abstract class DefaultResponse<T extends DefaultResponseModelBase<?>> //T is whatever data object that the user wants to hold the response in (and parse it in as well)
{
	T _responseData;
	
	public T getType()
	{
		return _responseData;
	}
	
	public void setType(T et)
	{
		this._responseData = et;
	}
	
	public DefaultResponse(Class<T> tClass)
	{
		try {
			_responseData = tClass.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Perhaps alert the user that their method that extends doesn't have a default constructor
			e.printStackTrace();
		}
	}
}
