package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class AddFolder extends BasicToodledoRequestModel
{
	private String _title;
	private boolean _isPrivate = false;
	
	public AddFolder(String title)
	{
		super("addFolder", null);
		
		_title = title;
	}
	
	public AddFolder()
	{
		this(null);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		this.addParam("private", _isPrivate);
		
		return super.getQueryParams();
	}
	
	public String getTitle() {
		return _title;
	}

	public boolean isPrivate() {
		return _isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		_isPrivate = isPrivate;
	}

	public void setTitle(String title) {
		_title = title;
	}
}
