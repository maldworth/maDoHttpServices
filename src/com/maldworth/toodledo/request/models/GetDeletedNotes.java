package com.maldworth.toodledo.request.models;

import org.joda.time.DateTime;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;

public class GetDeletedNotes extends BasicToodledoRequestModel
{
	public GetDeletedNotes()
	{
		super("getDeletedNotes", null, true);
	}
	
	public GetDeletedNotes(DateTime after)
	{
		this();
		this.addParam("after", DateTimeHelper.toUnixTime(after));
	}
}
