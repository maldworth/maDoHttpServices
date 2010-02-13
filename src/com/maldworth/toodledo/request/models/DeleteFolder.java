package com.maldworth.toodledo.request.models;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;

public class DeleteFolder extends BasicToodledoRequestModel
{
	private final long _id;
	
	public DeleteFolder(long id)
	{
		super("deleteFolder", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}

	public long getId() {
		return _id;
	}
}
