package com.maldworth.toodledo.response.models;

import com.maldworth.toodledo.enums.Level;

public class Goal
{
	private long _id;
	private Level _level;
	private long _contributes;//Other goal id's that this contributes to
	private int _archived;
	private String _content;
	/**
	 * @return the _id
	 */
	public long getId() {
		return _id;
	}
	/**
	 * @param id the _id to set
	 */
	public void setId(long id) {
		_id = id;
	}
	/**
	 * @return the _level
	 */
	public Level getLevel() {
		return _level;
	}
	/**
	 * @param level the _level to set
	 */
	public void setLevel(int level) {
		_level = Level.values()[level];
	}
	/**
	 * @return the _contributes
	 */
	public long getContributes() {
		return _contributes;
	}
	/**
	 * @param contributes the _contributes to set
	 */
	public void setContributes(long contributes) {
		_contributes = contributes;
	}
	/**
	 * @return the _archived
	 */
	public int getArchived() {
		return _archived;
	}
	/**
	 * @param archived the _archived to set
	 */
	public void setArchived(int archived) {
		_archived = archived;
	}
	/**
	 * @return the _content
	 */
	public String getContent() {
		return _content;
	}
	/**
	 * @param content the _content to set
	 */
	public void setContent(String content) {
		_content = content;
	}
	
	
}
