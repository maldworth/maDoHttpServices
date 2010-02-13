package com.maldworth.toodledo.request.models;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;

public class DeleteNote extends BasicToodledoRequestModel
{
	private final long _id;
	
	public DeleteNote(long id)
	{
		super("deleteNote", null);
		
		_id = id;
		
		this.addParam("id", _id);
	}

	public long getId() {
		return _id;
	}
}
