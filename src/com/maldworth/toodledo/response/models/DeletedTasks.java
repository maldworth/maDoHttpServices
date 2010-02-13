package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;


public class DeletedTasks extends ToodledoResponseModelBase
{
	private ArrayList<DeletedTask> _deletedTasks = new ArrayList<DeletedTask>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<DeletedTask> getDeletedTasks()
	{
		return _deletedTasks;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inDeletedTag = false;
	private boolean _inDeletedTaskTag = false;
	private boolean _inIdTag = false;
	private boolean _inStampTag = false;
	private DeletedTask _tmpDeletedTask;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("deleted".equals(theTagName)) {
			this._inDeletedTag = true;
		}else if("task".equals(theTagName)){
			this._inDeletedTaskTag = true;
			_tmpDeletedTask = new DeletedTask();
		}else if("id".equals(theTagName)){
			this._inIdTag = true;
		}else if("stamp".equals(theTagName)){
			this._inStampTag = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("deleted".equals(theTagName)) {
			this._inDeletedTag = false;
		}else if("task".equals(theTagName)) {
			this._inDeletedTaskTag = false;
			_deletedTasks.add(_tmpDeletedTask);
			_tmpDeletedTask = null;
		}else if("id".equals(theTagName)){
			this._inIdTag = false;
		}else if("stamp".equals(theTagName)){
			this._inStampTag = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inDeletedTag)
		{
			if(this._inDeletedTaskTag)
			{
				if(this._inIdTag) {
					_tmpDeletedTask.setId(Long.parseLong(new String(ch, start, length)));
				}else if(this._inStampTag) {
//					_tmpDeletedTask.setTimeStamp(Long.parseLong(new String(ch, start, length)));
				}
			}
		}
	}
}
