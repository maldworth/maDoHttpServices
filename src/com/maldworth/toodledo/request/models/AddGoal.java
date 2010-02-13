package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.enums.Level;
import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class AddGoal extends BasicToodledoRequestModel
{
	private String _title;
	private Level _level;
	private long _contributes = -1L;//id of a higher level goal this one contributes to
	
	public AddGoal(String title)
	{
		super("addGoal", null);
		
		_title = title;
	}
	
	public AddGoal()
	{
		this(null);
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
		
		return super.getQueryParams();
	}
	
	public String getTitle() {
		return _title;
	}

	public Level getLevel() {
		return _level;
	}
	
	public long getContributes() {
		return _contributes;
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

	
}
