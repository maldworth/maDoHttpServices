package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;

public class GetNotes extends BasicToodledoRequestModel
{
	private long _id = -1;
	private DateTime _modBefore;
	private DateTime _modAfter;
	
	public GetNotes()
	{
		super("getNotes", null);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{
		if(_id != -1L)
			this.addParam("id", _id);
		
		if(_modBefore != null)
			this.addParam("modbefore", DateTimeHelper.toYMDHMS(_modBefore));
		
		if(_modAfter != null)
			this.addParam("modafter", DateTimeHelper.toYMDHMS(_modAfter));
		
		return super.getQueryParams();
	}

	public long getId() {
		return _id;
	}

	public DateTime getModBefore() {
		return _modBefore;
	}

	public DateTime getModAfter() {
		return _modAfter;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setModBefore(DateTime modBefore) {
		_modBefore = modBefore;
	}

	public void setModAfter(DateTime modAfter) {
		_modAfter = modAfter;
	}

	
}
