package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class EditFolder extends BasicToodledoRequestModel
{
	private String _title;
	private final long _id;
	private int _isPrivate = -1;
	private int _isArchived = -1;
	
	public EditFolder(long id)
	{
		super("editFolder", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		if(_isPrivate !=  -1)
			this.addParam("private", _isPrivate);
		
		if(_isArchived != -1)
			this.addParam("archived", _isArchived);
		
		return super.getQueryParams();
	}

	public long getId() {
		return _id;
	}

	public String getTitle() {
		return _title;
	}

	public boolean isPrivate() {
		return _isPrivate == 1;
	}

	public boolean isArchived() {
		return _isArchived == 1;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setIsPrivate(boolean isPrivate) {
		_isPrivate = (isPrivate)?1:0;
	}

	public void setIsArchived(boolean isArchived) {
		_isArchived = (isArchived)?1:0;
	}
}
