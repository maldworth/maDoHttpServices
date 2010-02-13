package com.maldworth.toodledo.request.models;

import org.joda.time.DateTime;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;

public class GetDeleted extends BasicToodledoRequestModel
{
	public GetDeleted()
	{
		super("getDeleted", null, true);
	}
	
	public GetDeleted(DateTime after)
	{
		this();
		this.addParam("after", DateTimeHelper.toUnixTime(after));
	}
}
