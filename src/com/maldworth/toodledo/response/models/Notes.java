package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Notes extends ToodledoResponseModelBase
{
	private ArrayList<Note> _notes = new ArrayList<Note>();
	
	public ArrayList<Note> getNotes()
	{
		return _notes;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inNotesTag = false;
	private boolean _inNoteTag = false;
	private boolean _inIdTag = false;
	private boolean _inFolderTag = false;
	private boolean _inAddedTag = false;
	private boolean _inModifiedTag = false;
	private boolean _inTitleTag = false;
	private boolean _inTextTag = false;
	private boolean _inPrivateTag = false;
	
	private Note _tmpNote;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("notes".equals(theTagName)) {
			_inNotesTag = true;
		}else if("note".equals(theTagName)) {
			_inNoteTag = true;
			
			_tmpNote = new Note();
		}else if("id".equals(theTagName)) {
			_inIdTag = true;
		}else if("folder".equals(theTagName)) {
			_inFolderTag = true;
		}else if("added".equals(theTagName)) {
			_inAddedTag = true;
			
			long dateAdded = Long.parseLong(atts.getValue("seconds"));
			_tmpNote.setAdded(dateAdded);
		}else if("modified".equals(theTagName)) {
			_inModifiedTag = true;
			
			long dateModified = Long.parseLong(atts.getValue("seconds"));
			_tmpNote.setModified(dateModified);
		}else if("title".equals(theTagName)) {
			_inTitleTag = true;
		}else if("text".equals(theTagName)) {
			_inTextTag = true;
		}else if("private".equals(theTagName)) {
			_inPrivateTag = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("notes".equals(theTagName)) {
			_inNotesTag = false;
		}else if("note".equals(theTagName)) {
			_inNoteTag = false;
			_notes.add(_tmpNote);
			_tmpNote = null;
		}else if("id".equals(theTagName)) {
			_inIdTag = false;
		}else if("folder".equals(theTagName)) {
			_inFolderTag = false;
		}else if("added".equals(theTagName)) {
			_inAddedTag = false;
		}else if("modified".equals(theTagName)) {
			_inModifiedTag = false;
		}else if("title".equals(theTagName)) {
			_inTitleTag = false;
		}else if("text".equals(theTagName)) {
			_inTextTag = false;
		}else if("private".equals(theTagName)) {
			_inPrivateTag = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(_inNotesTag)
		{
			if(_inNoteTag)
			{
				if(_inIdTag) {
					_tmpNote.setId(Long.parseLong(new String(ch, start, length)));
				} else if(_inFolderTag) {
					_tmpNote.setFolder(Long.parseLong(new String(ch, start, length)));
				} else if(_inAddedTag) {
					//Already Set the UnixTime from Seconds attribute
				} else if(_inModifiedTag) {
					//Already Set the UnixTime from Seconds attribute
				} else if(_inTitleTag) {
					_tmpNote.setTitle(new String(ch, start, length));
				} else if(_inTextTag) {
					_tmpNote.setText(new String(ch, start, length));
				} else if(_inPrivateTag) {
					_tmpNote.setPrivate(Integer.parseInt(new String(ch, start, length)));
				}
			}
		}
	}
}
