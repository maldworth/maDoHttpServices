package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Folders extends ToodledoResponseModelBase
{
	private ArrayList<Folder> _folders = new ArrayList<Folder>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<Folder> getFolders()
	{
		return _folders;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inFoldersTag = false;
	private boolean _inFolderTag = false;
	private Folder _tmpFolder;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("folders".equals(theTagName)) {
			this._inFoldersTag = true;
		}else if("folder".equals(theTagName)){
			this._inFolderTag = true;
			
			_tmpFolder = new Folder();
			
			long idValue = Long.parseLong(atts.getValue("id"));
			int orderValue = Integer.parseInt(atts.getValue("order"));
			int archivedValue = Integer.parseInt(atts.getValue("archived"));
			int privateValue = Integer.parseInt(atts.getValue("private"));
			
			_tmpFolder.setId(idValue);
			_tmpFolder.setPrivate(privateValue);
			_tmpFolder.setArchived(archivedValue);
			_tmpFolder.setOrder(orderValue);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("folders".equals(theTagName)) {
			this._inFoldersTag = false;
		}else if("folder".equals(theTagName)) {
			this._inFolderTag = false;
			_folders.add(_tmpFolder);
			_tmpFolder = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inFoldersTag)
		{
			if(this._inFolderTag)
			{
				_tmpFolder.setName(new String(ch, start, length));
			}
		}
	}
}
