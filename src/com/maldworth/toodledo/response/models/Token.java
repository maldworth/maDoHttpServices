package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Token extends ToodledoResponseModelBase
{
	private String _token;
	
	/**
	 * @param _token the _token to set
	 */
	private void setToken(String _token) {
		this._token = _token;
	}

	/**
	 * @return the _token
	 */
	public String getToken() {
		return _token;
	}

	public Token()
	{
		super();
	}
	
	/*
	 * #########################################################################3
	 */
	
	
	private boolean _inTokenTag = false;
	
	private Token _parent;
	
	@Override
	public void startDocument() throws SAXException
	{
		_parent = Token.this;
	}

	@Override
	public void characters(char[] ch, int start, int length)
	{
		if(_inTokenTag)
			_parent.setToken(new String(ch, start, length));
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(String namespaceURI, String localName,
			String qName) throws SAXException {
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if("token".equalsIgnoreCase(theTagName))
			_inTokenTag = false;
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if("token".equalsIgnoreCase(theTagName))
			_inTokenTag = true;
	}
}
