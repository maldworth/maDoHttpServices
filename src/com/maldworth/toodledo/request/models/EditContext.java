package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class EditContext extends BasicToodledoRequestModel
{
	private String _title;
	private final long _id;
	
	public EditContext(long id)
	{
		super("editContext", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		return super.getQueryParams();
	}

	public long getId() {
		return _id;
	}
	
	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}
}
