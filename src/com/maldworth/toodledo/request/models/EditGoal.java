package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.enums.Level;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class EditGoal extends BasicToodledoRequestModel
{
	private String _title;
	private final long _id;
	private Level _level = null;
	private long _contributes = -1L;
	private int _isArchived = -1;
	
	public EditGoal(long id)
	{
		super("editGoal", null);
		
		_id = id;
		
		this.addParam("id", id);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		if(_level != null)
			this.addParam("level", _level.ordinal());
		
		if(_contributes != -1L)
			this.addParam("contributes", _contributes);
		
		if(_isArchived != -1)
			this.addParam("archived", _isArchived);
		
		return super.getQueryParams();
	}

	public String getTitle() {
		return _title;
	}

	public long getId() {
		return _id;
	}

	public Level getLevel() {
		return _level;
	}

	public long getContributes() {
		return _contributes;
	}

	public boolean isArchived() {
		return _isArchived == 1;
	}

	public void setTitle(String title) {
		_title = title;
	}
	
	public void setLevel(Level level) {
		_level = level;
	}

	public void setContributes(long contributes) {
		_contributes = contributes;
	}

	public void setIsArchived(boolean isArchived) {
		_isArchived = (isArchived)?1:0;
	}
}
