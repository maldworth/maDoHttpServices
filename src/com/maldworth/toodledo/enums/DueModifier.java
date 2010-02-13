package com.maldworth.toodledo.enums;

public enum DueModifier {
	DUEON("="),
	DUEAFTER(">"),
	OPTIONAL("?");
	
	private String _modifier;
	
	public String value(){ return _modifier; }
	
	DueModifier(String value)
	{
		_modifier = value;
	}
	
	public static DueModifier getModifier(String value)
	{
		if("=".equals(value))
		{
			return DUEON;
		}
		else if(">".equals(value))
		{
			return DUEAFTER;
		}
		else if("?".equals(value))
		{
			return OPTIONAL;
		}
		return null;
	}
}
