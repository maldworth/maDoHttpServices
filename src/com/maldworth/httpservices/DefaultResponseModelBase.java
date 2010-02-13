package com.maldworth.httpservices;

import org.apache.http.client.ResponseHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.maldworth.httpservices.exceptions.HttpServicesException;

public abstract class DefaultResponseModelBase<U> extends DefaultHandler
{
	private ResponseHandler<U> _httpResponseHandler;
	
	private U _unParsedEntity;
	private boolean _isUnParsedEntitySet = false;
	
	public <T extends ResponseHandler<U>> DefaultResponseModelBase(T tRespHandler)
	{
		_httpResponseHandler = tRespHandler;
	}
	
	public abstract void parseEntityToObject(U unParsedEntity) throws HttpServicesException;
	
//	public abstract List<NameValuePair> parseType(); // takes in the object with population, and makes the qparams

	public ResponseHandler<U> getRespHandler()
	{
		return _httpResponseHandler;
	}

	public U getUnParsedEntity()
	{
		return _unParsedEntity;
	}

	public void setUnParsedEntity(U unParsedEntity)
	{
		if(_isUnParsedEntitySet == false)
		{
			_unParsedEntity = unParsedEntity;
			_isUnParsedEntitySet = true;
		}
		else
		{
			//TODO throw an exception saying it is already set.
		}
	}
	
	//TODO add a malformed field and getter/setter. Make default = malformed. So the programmer has to set it to properly formed. This way when a ParseType is called and it is malformed, you get an error thrown. MalformedModel exception 
}
