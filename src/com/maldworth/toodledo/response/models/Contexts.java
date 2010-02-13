package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Contexts extends ToodledoResponseModelBase
{
	private ArrayList<Context> _contexts = new ArrayList<Context>();

	/**
	 * @return The ArrayList of Contexts retrieved from the server.
	 */
	public ArrayList<Context> getContexts()
	{
		return _contexts;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inContextsTag = false;
	private boolean _inContextTag = false;
	private Context _tmpContext;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("contexts".equals(theTagName)) {
			this._inContextsTag = true;
		}else if("context".equals(theTagName)){
			this._inContextTag = true;
			
			_tmpContext = new Context();
			
			long idValue = Long.parseLong(atts.getValue("id"));
			_tmpContext.setId(idValue);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("contexts".equals(theTagName)) {
			this._inContextsTag = false;
		}else if("context".equals(theTagName)) {
			this._inContextTag = false;
			_contexts.add(_tmpContext);
			_tmpContext = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inContextsTag)
		{
			if(this._inContextTag)
			{
				_tmpContext.setContent(new String(ch, start, length));
			}
		}
	}
}
