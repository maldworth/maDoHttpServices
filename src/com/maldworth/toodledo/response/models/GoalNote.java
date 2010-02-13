package com.maldworth.toodledo.response.models;

public class GoalNote
{
	private long _id;
	private String _content;
	
	/**
	 * @return Gets the ID of the Goal Note
	 */
	public long getId()
	{
		return _id;
	}
	/**
	 * @return Gets the content of the GoalNote
	 */
	public String getContent()
	{
		return _content;
	}
	
	/**
	 * @param Sets the ID of the GoalNote
	 */
	public void setId(long id)
	{
		_id = id;
	}
	/**
	 * @param Sets the content of the GoalNote
	 */
	public void setContent(String content) {
		_content = content;
	}
	
	
}
