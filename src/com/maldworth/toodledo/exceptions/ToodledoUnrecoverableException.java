package com.maldworth.toodledo.exceptions;

public class ToodledoUnrecoverableException extends Exception
{
	private static final long serialVersionUID = 1L;
	
//	private String _error;
//	
//	public ToodledoException()
//	{
//		super();
//		_error = "Unknown";
//	}
	
	public ToodledoUnrecoverableException(String message)
	{
		super(message);
//		_error = message;
	}

//	/**
//	 * @return The developer set Error
//	 */
//	public String get_error() {
//		return _error;
//	}
}
