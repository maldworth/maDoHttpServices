package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class AddContext extends BasicToodledoRequestModel
{
	private String _title;
	
	public AddContext(String title)
	{
		super("addContext", null);
		
		_title = title;
	}
	
	@Override
	public List<NameValuePair> getQueryParams() {
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		return super.getQueryParams();
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getTitle() {
		return _title;
	}
}
