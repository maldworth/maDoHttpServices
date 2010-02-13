package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Success extends ToodledoResponseModelBase
{
	private int _success = 0;

	/**
	 * @return The local time of the Toodledo Server (at the time of the api call)
	 */
	public boolean isSuccessful()
	{
		return _success == 1;
	}


	private void setSuccess(int success)
	{
		_success = success;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inSuccessTag = false;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("success".equals(theTagName)) {
			this._inSuccessTag = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("success".equals(theTagName)) {
			this._inSuccessTag = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inSuccessTag){
			setSuccess(Integer.parseInt(new String(ch, start, length)));
		}
	}
}
