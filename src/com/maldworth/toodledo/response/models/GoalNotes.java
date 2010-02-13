package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class GoalNotes extends ToodledoResponseModelBase
{
	private ArrayList<GoalNote> _goalNotes = new ArrayList<GoalNote>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<GoalNote> getGoalNotes()
	{
		return _goalNotes;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inGoalNotesTag = false;
	private boolean _inGoalNoteTag = false;
	private GoalNote _tmpGoalNote;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("goalNotes".equals(theTagName)) {
			this._inGoalNotesTag = true;
		}else if("goal".equals(theTagName)){
			this._inGoalNoteTag = true;
			
			_tmpGoalNote = new GoalNote();
			
			long idValue = Long.parseLong(atts.getValue("id"));
			_tmpGoalNote.setId(idValue);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("goalNotes".equals(theTagName)) {
			this._inGoalNotesTag = false;
		}else if("goal".equals(theTagName)) {
			this._inGoalNoteTag = false;
			_goalNotes.add(_tmpGoalNote);
			_tmpGoalNote = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inGoalNotesTag)
		{
			if(this._inGoalNoteTag)
			{
				_tmpGoalNote.setContent(new String(ch, start, length));
			}
		}
	}
}
