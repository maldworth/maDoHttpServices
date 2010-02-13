package com.maldworth.toodledo.response.models;


public class DeletedTask
{
	private long _id;
	private long _timeStamp;
	/**
	 * @return the _id
	 */
	public long getId()
	{
		return _id;
	}
	/**
	 * @param id the _id to set
	 */
	public void setId(long id)
	{
		_id = id;
	}
	/**
	 * @param _timeStamp the _timeStamp to set
	 */
	public void setTimeStamp(long timeStamp)
	{
		_timeStamp = timeStamp;
	}
	/**
	 * @return The time the task was deleted (according to Toodledo's server).
	 */
	public long getTimeStamp()
	{
		return _timeStamp;
	}
}
