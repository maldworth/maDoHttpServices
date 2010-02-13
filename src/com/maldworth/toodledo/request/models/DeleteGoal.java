package com.maldworth.toodledo.request.models;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;

public class DeleteGoal extends BasicToodledoRequestModel
{
	private final long _id;
	
	public DeleteGoal(long id)
	{
		super("deleteGoal", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}

	public long getId() {
		return _id;
	}
}
