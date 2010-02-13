package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class UserId extends ToodledoResponseModelBase
{
	private String _userId;
	
	/**
	 * @param _token the _token to set
	 */
	private void setUserId(String _token) {
		this._userId = _token;
	}

	/**
	 * @return the _token
	 */
	public String getUserId() {
		return _userId;
	}

	public UserId()
	{
		super();
	}
	
	/*
	 * ########################################################
	 */
	
	private boolean _inUserIdTag = false;
	
	private UserId _parent;
	
	@Override
	public void startDocument() throws SAXException
	{
		_parent = UserId.this;
	}

	@Override
	public void characters(char[] ch, int start, int length)
	{
		if(_inUserIdTag)
			_parent.setUserId(new String(ch, start, length));
	}

	@Override
	public void endElement(String namespaceURI, String localName,
			String qName) throws SAXException {
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if("userid".equalsIgnoreCase(theTagName))
			_inUserIdTag = false;
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if("userid".equalsIgnoreCase(theTagName))
			_inUserIdTag = true;
	}
}
