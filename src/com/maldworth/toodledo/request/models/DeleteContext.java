package com.maldworth.toodledo.request.models;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;

public class DeleteContext extends BasicToodledoRequestModel
{
	private final long _id;
	
	public DeleteContext(long id)
	{
		super("deleteContext", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}

	public long getId() {
		return _id;
	}
}
