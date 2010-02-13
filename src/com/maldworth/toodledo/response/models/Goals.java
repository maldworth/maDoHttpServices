package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Goals extends ToodledoResponseModelBase
{
	private ArrayList<Goal> _goals = new ArrayList<Goal>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<Goal> getGoals()
	{
		return _goals;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inGoalsTag = false;
	private boolean _inGoalTag = false;
	private Goal _tmpGoal;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("goals".equals(theTagName)) {
			this._inGoalsTag = true;
		}else if("goal".equals(theTagName)){
			this._inGoalTag = true;
			
			_tmpGoal = new Goal();
			
			long idValue = Long.parseLong(atts.getValue("id"));
			int levelValue = Integer.parseInt(atts.getValue("level"));
			int archivedValue = Integer.parseInt(atts.getValue("archived"));
			
			_tmpGoal.setId(idValue);
			_tmpGoal.setLevel(levelValue);
			_tmpGoal.setArchived(archivedValue);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("goals".equals(theTagName)) {
			this._inGoalsTag = false;
		}else if("goal".equals(theTagName)) {
			this._inGoalTag = false;
			_goals.add(_tmpGoal);
			_tmpGoal = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inGoalsTag)
		{
			if(this._inGoalTag)
			{
				_tmpGoal.setContent(new String(ch, start, length));
			}
		}
	}
}
