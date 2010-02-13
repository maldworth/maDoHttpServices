package com.maldworth.toodledo.enums;

public enum Priority
{
	TOP(3),
	HIGH(2),
	MEDIUM(1),
	LOW(0),
	NEGATIVE(-1);
	
	
	private int _priorityValue;
	
	public int value() { return _priorityValue; }
	
	Priority(int value)
	{
		_priorityValue = value;
	}
	
	public static Priority getPriority(int value)
	{
		switch(value)
		{
		case -1:
			return NEGATIVE;
		case 0:
			return LOW;
		case 1:
			return MEDIUM;
		case 2:
			return HIGH;
		case 3:
			return TOP;
		default:
			return LOW;
		}
	}
}
