package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class ServerInfo extends ToodledoResponseModelBase
{
	private long _serverTime;
	private String _humanReadableServerTime;
	private double _tokenExpires;
	
	public ServerInfo()
	{
		super();
	}
	
	/**
	 * @return The local time of the Toodledo Server (at the time of the api call)
	 */
	public long getServerTime()
	{
		return _serverTime;
	}
	/**
	 * @return The human readable formatted local time of the Toodledo Server (at the time of the api call)
	 */
	public String getHumanReadableServerTime()
	{
		return _humanReadableServerTime;
	}
	/**
	 * @return The number of minutes before the token (and subsequently your key) will expire.
	 */
	public double getTokenExpires()
	{
		return _tokenExpires;
	}
	
	private void setTokenExpires(double tokenExpires)
	{
		_tokenExpires = tokenExpires;
	}
	
	private void setHumanReadableServerTime(String humanReadableServerTime)
	{
		_humanReadableServerTime = humanReadableServerTime;
	}
	
	private void setServerTime(long serverTime)
	{
		_serverTime = serverTime;
	}
	
	/*
	 * #####################################################################
	 */

	private boolean _inServerTag = false;
	private boolean _inUnixTimeTag = false;
	private boolean _inDateTag = false;
	private boolean _inTokenExpiresTag = false;
	
	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("server".equals(theTagName)) {
			this._inServerTag = true;
		}else if ("unixtime".equals(theTagName)) {
			this._inUnixTimeTag = true;
		}else if ("date".equals(theTagName)) {
			this._inDateTag = true;
		}else if ("tokenexpires".equals(theTagName)) {
			this._inTokenExpiresTag = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("server".equals(theTagName)) {
			this._inServerTag = false;
		}else if ("unixtime".equals(theTagName)) {
			this._inUnixTimeTag = false;
		}else if ("date".equals(theTagName)) {
			this._inDateTag = false;
		}else if ("tokenexpires".equals(theTagName)) {
			this._inTokenExpiresTag = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inServerTag)
		{
			if(this._inUnixTimeTag){
				setServerTime(Long.parseLong(new String(ch, start, length)));
			}else if(this._inDateTag){
				setHumanReadableServerTime(new String(ch, start, length));
			}else if(this._inTokenExpiresTag){
				setTokenExpires(Double.parseDouble(new String(ch, start, length)));
			}
		}
	}
}
