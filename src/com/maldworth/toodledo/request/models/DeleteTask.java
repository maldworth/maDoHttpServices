package com.maldworth.toodledo.request.models;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;

public class DeleteTask extends BasicToodledoRequestModel
{
	private final long _id;
	
	public DeleteTask(long id)
	{
		super("deleteTask", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}

	public long getId() {
		return _id;
	}
}
