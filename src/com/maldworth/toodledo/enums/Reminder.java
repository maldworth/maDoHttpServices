package com.maldworth.toodledo.enums;

public enum Reminder
{
	None(0),
	QuarterHour(15),
	HalfHour(30),
	ThreeQuarterHour(45),
	Hour(60),
	HourAndAHalf(90),
	TwoHours(120),
	ThreeHours(180),
	FourHours(240),
	Day(1440),
	TwoDays(2880),
	ThreeDays(4320),
	FourDays(5760),
	FiveDays(7200),
	SixDays(8640),
	SevenDays(10080),
	FourteenDays(20160),
	ThirtyDays(43200);
	
	private int _reminderValue;
	
	public int value() { return _reminderValue; }
	
	Reminder(int value)
	{
		_reminderValue = value;
	}
	
	public static Reminder getReminder(int value)
	{
		switch(value)
		{
		case 0:
			return None;
		case 15:
			return QuarterHour;
		case 30:
			return HalfHour;
		case 45:
			return ThreeQuarterHour;
		case 60:
			return Hour;
		case 90:
			return HourAndAHalf;
		case 120:
			return TwoHours;
		case 180:
			return ThreeHours;
		case 240:
			return FourHours;
		case 1440:
			return Day;
		case 2880:
			return TwoDays;
		case 4320:
			return ThreeDays;
		case 5760:
			return FourDays;
		case 7200:
			return FiveDays;
		case 8640:
			return SixDays;
		case 10080:
			return SevenDays;
		case 20160:
			return FourteenDays;
		case 43200:
			return ThirtyDays;
		default:
			return None;
		}
	}
}
