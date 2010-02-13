package com.maldworth.toodledo.enums;

public enum Status
{
	NONE,
	NEXT_ACTION,
	ACTIVE,
	PLANNING,
	DELEGATED,
	WAITING,
	HOLD,
	POSTPONED,
	SOMEDAY,
	CANCELED,
	REFERENCED;
	
	public static Status getStatus(int value)
	{
		switch(value)
		{
		case 0:
			return NONE;
		case 1:
			return NEXT_ACTION;
		case 2:
			return ACTIVE;
		case 3:
			return PLANNING;
		case 4:
			return DELEGATED;
		case 5:
			return WAITING;
		case 6:
			return HOLD;
		case 7:
			return POSTPONED;
		case 8:
			return SOMEDAY;
		case 9:
			return CANCELED;
		case 10:
			return REFERENCED;
		default:
			return NONE;
		}
	}
}
