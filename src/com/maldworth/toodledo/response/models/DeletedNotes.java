package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;


public class DeletedNotes extends ToodledoResponseModelBase
{
	private ArrayList<DeletedNote> _deletedNotes = new ArrayList<DeletedNote>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<DeletedNote> getDeletedNotes()
	{
		return _deletedNotes;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inDeletedTag = false;
	private boolean _inDeletedNoteTag = false;
	private boolean _inIdTag = false;
	private boolean _inStampTag = false;
	private DeletedNote _tmpDeletedNote;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("deleted".equals(theTagName)) {
			this._inDeletedTag = true;
		}else if("note".equals(theTagName)){
			this._inDeletedNoteTag = true;
			_tmpDeletedNote = new DeletedNote();
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
		}else if("note".equals(theTagName)) {
			this._inDeletedNoteTag = false;
			_deletedNotes.add(_tmpDeletedNote);
			_tmpDeletedNote = null;
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
			if(this._inDeletedNoteTag)
			{
				if(this._inIdTag) {
					_tmpDeletedNote.setId(Long.parseLong(new String(ch, start, length)));
				}else if(this._inStampTag) {
//					_tmpDeletedNote.setTimeStamp(Long.parseLong(new String(ch, start, length)));
					_tmpDeletedNote.setTimeStamp(new String(ch, start, length));
				}
			}
		}
	}
}
