package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Added extends ToodledoResponseModelBase
{
	private int _added = 0;

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public boolean isAdded()
	{
		return _added > 0;
	}
	
	public int getId()
	{
		return _added;
	}


	private void setAdded(int added)
	{
		_added = added;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inAddedTag = false;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("added".equals(theTagName)) {
			this._inAddedTag = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("added".equals(theTagName)) {
			this._inAddedTag = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inAddedTag){
			setAdded(Integer.parseInt(new String(ch, start, length)));
		}
	}
}
