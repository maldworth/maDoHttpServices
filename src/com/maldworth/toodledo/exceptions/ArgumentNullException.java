package com.maldworth.toodledo.exceptions;

public class ArgumentNullException extends ToodledoException
{
	private static final long serialVersionUID = 1L;

	public ArgumentNullException(String message)
	{
		super(message);
	}
}
