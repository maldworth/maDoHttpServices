package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.maldworth.toodledo.enums.Priority;
import com.maldworth.toodledo.enums.Repeat;
import com.maldworth.toodledo.enums.Status;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;
import com.maldworth.utils.Helpers;

public class GetTasks extends BasicToodledoRequestModel
{
	private long _id = -1;//
	private long _parent = -1;//
	private String _title;//
	private String _tag;//
	private long _folder = -1;//
	private long _context = -1;//
	private long _goal = -1;//
	private Repeat _repeat = null;//
	private Status _status = null;//
	private Priority _priority = null;//
	private int _shorter = -1;//
	private int _longer = -1;//
	private DateTime _before;//
	private DateTime _after;//
	private DateTime _startBefore;//
	private DateTime _startAfter;//
	private DateTime _modBefore;//
	private DateTime _modAfter;//
	private DateTime _compBefore;//
	private DateTime _compAfter;//
	private boolean _omitCompleted = false;//
	private int _star = -1;//
	private int _start = -1;// default is 0, but why transmit 0 if it is default
	private int _end = -1;//max is 1000
	
	public GetTasks()
	{
		super("getTasks", null, true);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{
		if(_id != -1L)
			this.addParam("id", _id);
		
		if(_parent != -1L)
			this.addParam("parent", _parent);
		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", _title);
		
		if(Helpers.isNullOrEmptyOrBlank(_tag) == false)
			this.addParam("tag", _tag);
		
		if(_folder != -1L)
			this.addParam("folder", _folder);
		
		if(_context != -1L)
			this.addParam("context", _context);
		
		if(_goal != -1L)
			this.addParam("goal", _goal);
		
		if(_repeat != null)
			this.addParam("repeat", _repeat.value());
		
		if(_status != null)
			this.addParam("status", _status.ordinal());
		
		if(_priority != null)
			this.addParam("priority", _priority.value());
		
		if(_shorter != -1)
			this.addParam("shorter", _shorter);
		
		if(_longer != -1)
			this.addParam("longer", _longer);
		
		if(_before != null)
			this.addParam("before", DateTimeHelper.toYMD(_before));
		
		if(_after != null)
			this.addParam("after", DateTimeHelper.toYMD(_after));
		
		if(_startBefore != null)
			this.addParam("startbefore", DateTimeHelper.toYMD(_startBefore));
		
		if(_startAfter != null)
			this.addParam("startafter", DateTimeHelper.toYMD(_startAfter));
		
		if(_modBefore != null)
			this.addParam("modbefore", DateTimeHelper.toYMDHMS(_modBefore));
		
		if(_modAfter != null)
			this.addParam("modafter", DateTimeHelper.toUnixTime(_modAfter));
		
		if(_compBefore != null)
			this.addParam("compbefore", DateTimeHelper.toYMD(_compBefore));
		
		if(_compAfter != null)
			this.addParam("compafter", DateTimeHelper.toYMD(_compAfter));
		
		if(_omitCompleted != false)
			this.addParam("notcomp", _omitCompleted);
		
		if(_star != -1)
			this.addParam("star", _star);
		
		if(_start != -1)
			this.addParam("start", _start);
		
		if(_end != -1)
			this.addParam("end", _end);
		
		return super.getQueryParams();
	}

	public void setTitle(String title) {
		_title = Helpers.encode(title);
	}

	public void setId(long id) {
		_id = id;
	}

	public void setParent(long parent) {
		_parent = parent;
	}

	public void setTag(String tag) {
		_tag = Helpers.encode(tag);
	}

	public void setFolder(long folder) {
		_folder = folder;
	}

	public void setContext(long context) {
		_context = context;
	}

	public void setGoal(long goal) {
		_goal = goal;
	}

	public void setRepeat(Repeat repeat) {
		_repeat = repeat;
	}

	public void setStatus(Status status) {
		_status = status;
	}

	public void setPriority(Priority priority) {
		_priority = priority;
	}

	public void setShorter(int shorter) {
		_shorter = shorter;
	}

	public void setLonger(int longer) {
		_longer = longer;
	}

	public void setBefore(DateTime before) {
		_before = before;
	}

	public void setAfter(DateTime after) {
		_after = after;
	}

	public void setStartBefore(DateTime startBefore) {
		_startBefore = startBefore;
	}

	public void setStartAfter(DateTime startAfter) {
		_startAfter = startAfter;
	}

	public void setModBefore(DateTime modBefore) {
		_modBefore = modBefore;
	}

	public void setModAfter(DateTime modAfter) {
		_modAfter = modAfter;
	}

	public void setCompBefore(DateTime compBefore) {
		_compBefore = compBefore;
	}

	public void setCompAfter(DateTime compAfter) {
		_compAfter = compAfter;
	}

	public void setOmitCompleted(boolean omitCompleted) {
		_omitCompleted = omitCompleted;
	}

	public void setStar(int star) {
		_star = star;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setEnd(int end) {
		_end = end;
	}
}
