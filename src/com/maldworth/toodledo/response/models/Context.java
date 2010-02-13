package com.maldworth.toodledo.response.models;

public class Context
{
	private long _id;
	private String _content;
	/**
	 * @return The ID in the Context
	 */
	public long getId() {
		return _id;
	}
	/**
	 * @param The ID to set to the Context
	 */
	public void setId(long id) {
		_id = id;
	}
	/**
	 * @return The content in the Context
	 */
	public String getContent() {
		return _content;
	}
	/**
	 * @param The content in the Context
	 */
	public void setContent(String content) {
		_content = content;
	}
}
