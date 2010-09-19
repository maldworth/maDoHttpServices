package com.maldworth.toodledo.response.models;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.enums.Priority;
import com.maldworth.toodledo.enums.Reminder;
import com.maldworth.toodledo.enums.Repeat;
import com.maldworth.toodledo.enums.Status;
import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class Tasks extends ToodledoResponseModelBase
{
	private int _number;
	private int _total;
	private int _start;
	private int _end;

	/**
	 * @return The number of tasks obtained from the current result set.
	 */
	public int getNumber() {
		return _number;
	}
	
	/**
	 * @return The total number of tasks that would be returned if start and end parameters weren't used.
	 */
	public int getTotal() {
		return _total;
	}
	
	/**
	 * @return The number that the tasks started at.
	 */
	public int getStart() {
		return _start;
	}
	
	/**
	 * @return the _number
	 */
	public int getEnd() {
		return _end;
	}
	
	private ArrayList<Task> _tasks = new ArrayList<Task>();

	/**
	 * @return True if added, false if a fail occurred.
	 */
	public ArrayList<Task> getTasks()
	{
		return _tasks;
	}

	/*
	 * #####################################################################
	 */

	private boolean _inTasksTag = false;
	private boolean _inTaskTag = false;
	private Task _tmpTask;
	
	private boolean _inIdTag = false;
	private boolean _inParentTag = false;
	private boolean _inChildrenTag = false;
	private boolean _inTitleTag = false;
	private boolean _inTagTag = false;
	private boolean _inFolderTag = false;
	private boolean _inContextTag = false;
	private boolean _inGoalTag = false;
	private boolean _inAddedTag = false;
	private boolean _inModifiedTag = false;
	private boolean _inStartDateTag = false;
	private boolean _inDueDateTag = false;
	private boolean _inDueTimeTag = false;
	private boolean _inStartTimeTag = false;
	private boolean _inReminderTag = false;
	private boolean _inCompletedTag = false;
	private boolean _inRepeatTag = false;
	private boolean _inRepeatAdvTag = false;
	private boolean _inStatusTag = false;
	private boolean _inStarTag = false;
	private boolean _inPriorityTag = false;
	private boolean _inLengthTag = false;
	private boolean _inTimerTag = false;
	private boolean _inNoteTag = false;

	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("tasks".equals(theTagName)) {
			this._inTasksTag = true;
			
			int numValue = Integer.parseInt(atts.getValue("num"));
			int totalValue = Integer.parseInt(atts.getValue("total"));
			int startValue = Integer.parseInt(atts.getValue("start"));
			int endValue = Integer.parseInt(atts.getValue("end"));
			
			_number = numValue;
			_total = totalValue;
			_start = startValue;
			_end = endValue;
		}else if("task".equals(theTagName)) {
			_inTaskTag = true;
			
			_tmpTask = new Task();
			
		}else if("id".equals(theTagName)) {
			_inIdTag = true;
		}else if("parent".equals(theTagName)) {
			_inParentTag = true;
		}else if("children".equals(theTagName)) {
			_inChildrenTag = true;
		}else if("title".equals(theTagName)) {
			_inTitleTag = true;
		}else if("tag".equals(theTagName)) {
			_inTagTag = true;
		}else if("folder".equals(theTagName)) {
			_inFolderTag = true;
		}else if("context".equals(theTagName)) {
			_inContextTag = true;
			
			long idValue = Long.parseLong(atts.getValue("id"));
			_tmpTask.setContextId(idValue);
		}else if("goal".equals(theTagName)) {
			_inGoalTag = true;
			
			long idValue = Long.parseLong(atts.getValue("id"));
			_tmpTask.setGoalId(idValue);
		}else if("added".equals(theTagName)) {
			_inAddedTag = true;
		}else if("modified".equals(theTagName)) {
			_inModifiedTag = true;
		}else if("startdate".equals(theTagName)) {
			_inStartDateTag = true;
		}else if("duedate".equals(theTagName)) {
			_inDueDateTag = true;
		}else if("duetime".equals(theTagName)) {
			_inDueTimeTag = true;
		}else if("starttime".equals(theTagName)) {
			_inStartTimeTag = true;
		}else if("reminder".equals(theTagName)) {
			_inReminderTag = true;
		}else if("completed".equals(theTagName)) {
			_inCompletedTag = true;
		}else if("repeat".equals(theTagName)) {
			_inRepeatTag = true;
		}else if("rep_advanced".equals(theTagName)) {
			_inRepeatAdvTag = true;
		}else if("status".equals(theTagName)) {
			_inStatusTag = true;
		}else if("star".equals(theTagName)) {
			_inStarTag = true;
		}else if("priority".equals(theTagName)) {
			_inPriorityTag = true;
		}else if("length".equals(theTagName)) {
			_inLengthTag = true;
		}else if("timer".equals(theTagName)) {
			_inTimerTag = true;
		}else if("note".equals(theTagName)) {
			_inNoteTag = true;
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("tasks".equals(theTagName)) {
			this._inTasksTag = false;
		}else if("task".equals(theTagName)) {
			this._inTaskTag = false;
			_tasks.add(_tmpTask);
			_tmpTask = null;
		}else if("id".equals(theTagName)) {
			_inIdTag = false;
		}else if("parent".equals(theTagName)) {
			_inParentTag = false;
		}else if("children".equals(theTagName)) {
			_inChildrenTag = false;
		}else if("title".equals(theTagName)) {
			_inTitleTag = false;
		}else if("tag".equals(theTagName)) {
			_inTagTag = false;
		}else if("folder".equals(theTagName)) {
			_inFolderTag = false;
		}else if("context".equals(theTagName)) {
			_inContextTag = false;
		}else if("goal".equals(theTagName)) {
			_inGoalTag = false;
		}else if("added".equals(theTagName)) {
			_inAddedTag = false;
		}else if("modified".equals(theTagName)) {
			_inModifiedTag = false;
		}else if("startdate".equals(theTagName)) {
			_inStartDateTag = false;
		}else if("duedate".equals(theTagName)) {
			_inDueDateTag = false;
		}else if("duetime".equals(theTagName)) {
			_inDueTimeTag = false;
		}else if("starttime".equals(theTagName)) {
			_inStartTimeTag = false;
		}else if("reminder".equals(theTagName)) {
			_inReminderTag = false;
		}else if("completed".equals(theTagName)) {
			_inCompletedTag = false;
		}else if("repeat".equals(theTagName)) {
			_inRepeatTag = false;
		}else if("rep_advanced".equals(theTagName)) {
			_inRepeatAdvTag = false;
		}else if("status".equals(theTagName)) {
			_inStatusTag = false;
		}else if("star".equals(theTagName)) {
			_inStarTag = false;
		}else if("priority".equals(theTagName)) {
			_inPriorityTag = false;
		}else if("length".equals(theTagName)) {
			_inLengthTag = false;
		}else if("timer".equals(theTagName)) {
			_inTimerTag = false;
		}else if("note".equals(theTagName)) {
			_inNoteTag = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
	{
		if(_inTasksTag)
		{
			if(_inTaskTag)
			{
				if(_inIdTag) {
					_tmpTask.setId(Long.parseLong(new String(ch, start, length)));
				}else if(_inParentTag) {
					_tmpTask.setParent(Long.parseLong(new String(ch, start, length)));
				}else if(_inChildrenTag) {
					_tmpTask.setChildren(Integer.parseInt(new String(ch, start, length)));
				}else if(_inTitleTag) {
					_tmpTask.setTitle(new String(ch, start, length));
				}else if(_inTagTag) {
					_tmpTask.setTag(new String(ch, start, length));
				}else if(_inFolderTag) {
					_tmpTask.setFolder(Long.parseLong(new String(ch, start, length)));
				}else if(_inContextTag) {
					_tmpTask.setContext(new String(ch, start, length));
				}else if(_inGoalTag) {
					_tmpTask.setGoal(new String(ch, start, length));
				}else if(_inAddedTag) {
					_tmpTask.setAdded(Long.parseLong(new String(ch, start, length)));
				}else if(_inModifiedTag) {
					_tmpTask.setModified(Long.parseLong(new String(ch, start, length)));
				}else if(_inStartDateTag) {
					_tmpTask.setStartDate(Long.parseLong(new String(ch, start, length)));
				}else if(_inDueDateTag) {
					_tmpTask.setDueDate(Long.parseLong(new String(ch, start, length)));
				}else if(_inDueTimeTag) {
					_tmpTask.setDueTime(Long.parseLong(new String(ch, start, length)));
				}else if(_inStartTimeTag) {
					_tmpTask.setStartTime(Long.parseLong(new String(ch, start, length)));
				}else if(_inReminderTag) {
					_tmpTask.setReminder(Reminder.getReminder(Integer.parseInt(new String(ch, start, length))));
				}else if(_inCompletedTag) {
					_tmpTask.setCompleted(Long.parseLong(new String(ch, start, length)));
				}else if(_inRepeatTag) {
					_tmpTask.setRepeat(Repeat.getRepeat(Integer.parseInt(new String(ch, start, length))));
				}else if(_inRepeatAdvTag) {
					//TODO what strings are acceptable
				}else if(_inStatusTag) {
					_tmpTask.setStatus(Status.getStatus(Integer.parseInt(new String(ch, start, length))));
				}else if(_inStarTag) {
					_tmpTask.setStar(Integer.parseInt(new String(ch, start, length)));
				}else if(_inPriorityTag) {
					_tmpTask.setPriority(Priority.getPriority(Integer.parseInt(new String(ch, start, length))));
				}else if(_inLengthTag) {
					_tmpTask.setLength(Integer.parseInt(new String(ch, start, length)));
				}else if(_inTimerTag) {
					//TODO some funky stuff described in the api docs
				}else if(_inNoteTag) {
					_tmpTask.appendNote(new String(ch, start, length));
				}
			}
		}
	}
}
