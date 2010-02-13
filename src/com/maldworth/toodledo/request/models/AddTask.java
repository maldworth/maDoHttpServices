package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.maldworth.toodledo.enums.DueModifier;
import com.maldworth.toodledo.enums.Priority;
import com.maldworth.toodledo.enums.Reminder;
import com.maldworth.toodledo.enums.Repeat;
import com.maldworth.toodledo.enums.Status;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;
import com.maldworth.utils.Helpers;

public class AddTask extends BasicToodledoRequestModel
{
	private long _parent = -1L;//
	private String _title;//
	private String _tag;//
	private long _folder = -1L;//
	private long _contextId = -1L;
	private long _goalId = -1L;//
	private long _startDate = -1L;//
	private String _dueDateString;//If this is null, then we use _dueDate and duedatemodifier
	private DueModifier _dueDateModifier; // =, ? or >
	private long _dueDate = -1L;//
	private long _dueTime = -1L;//TODO potentially get rid of this and use _dueDate to extract the duetime from it
	private long _startTime = -1L;//Same with Start Date
	private Reminder _reminder;//
	private Repeat _repeat;//
	private String _repeatAdv;//
	private Status _status;//
	private int _star = -1;//
	private Priority _priority;//
	private int _length = -1;//
	private String _note;//Encode
	
	public AddTask(String title) {
		super("addTask", null);
		
		_title = title;
	}
	
	
	/**
	 * Remember, title is the one required parameter, so please don't forget to include it.
	 */
	public AddTask()
	{
		this(null);
	}
	
	/**
	 * @return This is used to Pro accounts that have access to subtasks. The parent ID is unique to the parent task that owns this subtask.
	 */
	public long getParent() {
		return _parent;
	}
	/**
	 * @return The title of the task.
	 */
	public String getTitle() {
		return _title;
	}
	/**
	 * @return A tag that are associated with the task.
	 */
	public String getTag() {
		return _tag;
	}
	/**
	 * @return The ID of the folder that this task belongs to.
	 */
	public long getFolder() {
		return _folder;
	}
	public long getContext() {
		return _contextId;
	}
	public long getGoal() {
		return _goalId;
	}
	public long getStartDate() {
		return _startDate;
	}
	public long getStartTime() {
		return _startTime;
	}
	public DueModifier getDueDateModifier() {
		return _dueDateModifier;
	}
	public String getDueDateString() {
		return _dueDateString;
	}
	public long getDueDate() {
		return _dueDate;
	}
	public long getDueTime() {
		return _dueTime;
	}
	public Reminder getRemainder() {
		return _reminder;
	}
	public Repeat getRepeat() {
		return _repeat;
	}
	public String getRepeatAdv() {
		return _repeatAdv;
	}
	public Status getStatus() {
		return _status;
	}
	public int getStar() {
		return _star;
	}
	public Priority getPriority() {
		return _priority;
	}
	public int getLength() {
		return _length;
	}
	public String getNote() {
		return _note;
	}
	public void setParent(long parent) {
		_parent = parent;
	}
	public void setTitle(String title) {
		_title = title;
	}
	public void setTag(String tag) {
		_tag = tag;
	}
	public void setFolder(long folder) {
		_folder = folder;
	}
	public void setContext(long contextId) {
		_contextId = contextId;
	}
	public void setGoal(long goalId) {
		_goalId = goalId;
	}
	public void setStartDate(long startDate) {
		_startDate = startDate;
	}
	public void setStartDate(DateTime startDate) {
		_startDate = DateTimeHelper.toUnixTime(startDate);
	}
	public void setStartTime(long startTime) {
		_startTime = startTime;
	}
	public void setStartTime(DateTime startTime) {
		_startTime = DateTimeHelper.toUnixTime(startTime);
	}
	public void setDueDateModifier(DueModifier dueDateModifier) {
		_dueDateModifier = dueDateModifier;
	}
	public void setDueDateString(String dueDate) {
		_dueDateString = dueDate;
	}
	public void setDueDate(long dueDate) {
		_dueDate = dueDate;
	}
	public void setDueDate(DateTime dueDate) {
		_dueDate = DateTimeHelper.toUnixTime(dueDate);
	}
	public void setDueTime(long dueTime) {
		_dueTime = dueTime;
	}
	public void setDueTime(DateTime dueTime) {
		_dueTime = DateTimeHelper.toUnixTime(dueTime);
	}
	public void setReminder(Reminder reminder) {
		_reminder = reminder;
	}
	public void setRepeat(Repeat repeat) {
		_repeat = repeat;
	}
	public void setRepeatAdv(String repeatAdv) {
		_repeatAdv = repeatAdv;
	}
	public void setStatus(Status status) {
		_status = status;
	}
	public void setStar(int star) {
		_star = star;
	}
	public void setPriority(Priority priority) {
		_priority = priority;
	}
	public void setLength(int length) {
		_length = length;
	}
	public void setNote(String note) {
		_note = note;
	}
	
	@Override
	public List<NameValuePair> getQueryParams()	{		
		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", _title);
		
		if(Helpers.isNullOrEmptyOrBlank(_tag) == false)
			this.addParam("tag", _tag);
		
		if(_folder != -1L)
			this.addParam("folder", _folder);
		
		if(_contextId != -1L)
			this.addParam("context", _contextId);
		
		if(_goalId != -1L)
			this.addParam("goal", _goalId);
		
		if(_parent != -1L)
			this.addParam("parent", _parent);
		
		if(Helpers.isNullOrEmptyOrBlank(_dueDateString))
		{
			String modifier = (_dueDateModifier == null)? "" : _dueDateModifier.value();
			if(_dueDate > 0L)
				this.addParam("duedate", modifier+DateTimeHelper.toYMD(DateTimeHelper.fromUnixTime(_dueDate)));
		}
		else
		{
			this.addParam("duedate", _dueDateString);
		}
		
		if(_dueTime > 0L)//if it is 0L, we don't care to add that as that's the default
			this.addParam("duetime", DateTimeHelper.tohmmaa(DateTimeHelper.fromUnixTime(_dueTime)));
		
		if(_startDate > 0L)
			this.addParam("startdate", DateTimeHelper.toYMD(DateTimeHelper.fromUnixTime(_startDate)));
		
		if(_startTime > 0L)
			this.addParam("starttime", DateTimeHelper.tohmmaa(DateTimeHelper.fromUnixTime(_startTime)));
		
		if(_reminder != null)
			this.addParam("reminder", _reminder.value());
		
		if(_repeat != null)
			this.addParam("repeat", _repeat.value());
		
		//TODO rep advanced
		
		if(_status != null)
			this.addParam("status", _status.ordinal());
		
		if(_length != -1)
			this.addParam("length", _length);
		
		if(_priority != null)
			this.addParam("priority", _priority.value());
		
		if(_star != -1)
			this.addParam("star", _star);
		
		if(Helpers.isNullOrEmptyOrBlank(_note) == false)
			this.addParam("note", Helpers.encode(_note));
		
		return super.getQueryParams();
	}
}
