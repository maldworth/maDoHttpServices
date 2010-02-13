package com.maldworth.toodledo.exceptions;

public class UniqueIdAlreadySet extends ToodledoException
{
	private static final long serialVersionUID = 1L;

	public UniqueIdAlreadySet(String message)
	{
		super(message);
	}
}
