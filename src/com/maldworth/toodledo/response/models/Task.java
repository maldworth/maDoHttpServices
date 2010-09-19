package com.maldworth.toodledo.response.models;

import com.maldworth.toodledo.enums.Priority;
import com.maldworth.toodledo.enums.Reminder;
import com.maldworth.toodledo.enums.Repeat;
import com.maldworth.toodledo.enums.Status;
import com.maldworth.utils.Helpers;

public class Task
{
	private long _id;
	private long _parent;
	private int _children;
	private String _title;
	private String _tag;
	private long _folder;
	private long _contextId;
	private String _context;
	private long _goalId;
	private String _goal;
	private long _added;
	private long _modified;
	private long _startDate;
	private String _dueDateModifier;
	private long _dueDate;
	private long _dueTime;
	private long _startTime;
	private long _completed;
	private Reminder _reminder;
	private Repeat _repeat;
	private String _repeatAdv;
	private Status _status;
	private int _star;
	private Priority _priority;
	private int _length;
	private int _timerOnFor;
	private int _timer;
	private String _note;
	
	/**
	 * @return The task's unique ID
	 */
	public long getId() {
		return _id;
	}
	/**
	 * @return This is used to Pro accounts that have access to subtasks. The parent ID is unique to the parent task that owns this subtask.
	 */
	public long getParent() {
		return _parent;
	}
	/**
	 * @return The number of subtasks that this current task is the parent of.
	 */
	public int getChildren() {
		return _children;
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
	public long getContextId() {
		return _contextId;
	}
	public String getContext() {
		return _context;
	}
	public long getGoalId() {
		return _goalId;
	}
	public String getGoal() {
		return _goal;
	}
	public long getAdded() {
		return _added;
	}
	public long getModified() {
		return _modified;
	}
	public long getStartDate() {
		return _startDate;
	}
	public long getCompleted() {
		return _completed;
	}
	public String getDueDateModifier() {
		return _dueDateModifier;
	}
	public long getDueDate() {
		return _dueDate;
	}
	public long getDueTime() {
		return _dueTime;
	}
	public long getStartTime() {
		return _startTime;
	}
	public Reminder getReminder() {
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
	public int getTimerOnFor() {
		return _timerOnFor;
	}
	public int getTimer() {
		return _timer;
	}
	public String getNote() {
		return _note;
	}
	
	public void setId(long id) {
		_id = id;
	}
	public void setParent(long parent) {
		_parent = parent;
	}
	public void setChildren(int children) {
		_children = children;
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
	public void setContextId(long contextId) {
		_contextId = contextId;
	}
	public void setContext(String context) {
		_context = context;
	}
	public void setGoalId(long goalId) {
		_goalId = goalId;
	}
	public void setGoal(String goal) {
		_goal = goal;
	}
	public void setAdded(long added) {
		_added = added;
	}
	public void setModified(long modified) {
		_modified = modified;
	}
	public void setStartDate(long startDate) {
		_startDate = startDate;
	}
	public void setCompleted(long completed) {
		_completed = completed;
	}
	public void setDueDateModifier(String dueDateModifier) {
		_dueDateModifier = dueDateModifier;
	}
	public void setDueDate(long dueDate) {
		_dueDate = dueDate;
	}
	public void setDueTime(long dueTime) {
		_dueTime = dueTime;
	}
	public void setStartTime(long startTime) {
		_startTime = startTime;
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
	public void setTimerOnFor(int timerOnFor) {
		_timerOnFor = timerOnFor;
	}
	public void setTimer(int timer) {
		_timer = timer;
	}
	public void setNote(String note) {
		_note = note;
	}
	public void appendNote(String note)
	{
		if(Helpers.isNullOrEmptyOrBlank(_note))
			_note = note;
		else
			_note.concat(note);
	}
}
