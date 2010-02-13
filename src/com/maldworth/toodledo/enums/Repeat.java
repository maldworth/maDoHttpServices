package com.maldworth.toodledo.enums;

public enum Repeat
{
	NOREPEAT(0),
	WEEKLY(1),
	MONTHLY(2),
	YEARLY(3),
	DAILY(4),
	BIWEEKLY(5),
	BIMONTHLY(6),
	SEMIANNUALLY(7),
	QUARTERLY(8),
	WITHPARENT(9),
	ADVANCED(50),
	C_NOREPEAT(100),
	C_WEEKLY(101),
	C_MONTHLY(102),
	C_YEARLY(103),
	C_DAILY(104),
	C_BIWEEKLY(105),
	C_BIMONTHLY(106),
	C_SEMIANNUALLY(107),
	C_QUARTERLY(108),
	C_WITHPARENT(109),
	C_ADVANCED(150);
	//TODO add the 100 to any number for repeat from completion date, rather than due date (by default)
	
	private int _priorityValue;
	
	public int value() { return _priorityValue; }
	
	Repeat(int value)
	{
		_priorityValue = value;
	}
	
	public static Repeat getRepeat(int value)
	{
		switch(value)
		{
		case 0:
			return NOREPEAT;
		case 1:
			return WEEKLY;
		case 2:
			return MONTHLY;
		case 3:
			return YEARLY;
		case 4:
			return DAILY;
		case 5:
			return BIWEEKLY;
		case 6:
			return BIMONTHLY;
		case 7:
			return SEMIANNUALLY;
		case 8:
			return QUARTERLY;
		case 9:
			return WITHPARENT;
		case 50:
			return ADVANCED;
		case 100:
			return C_NOREPEAT;
		case 101:
			return C_WEEKLY;
		case 102:
			return C_MONTHLY;
		case 103:
			return C_YEARLY;
		case 104:
			return C_DAILY;
		case 105:
			return C_BIWEEKLY;
		case 106:
			return C_BIMONTHLY;
		case 107:
			return C_SEMIANNUALLY;
		case 108:
			return C_QUARTERLY;
		case 109:
			return C_WITHPARENT;
		case 150:
			return C_ADVANCED;
		default:
			return NOREPEAT;
		}
	}
}
