package com.maldworth.toodledo.response.models;

public class Folder
{
	private long _id;
	private int _private;
	private int _archived;
	private int _order;
	private String _name;
	/**
	 * @return The Folder ID
	 */
	public long getId() {
		return _id;
	}
	/**
	 * @param Shouldn't set this
	 */
	public void setId(long id) {
		_id = id;
	}
	/**
	 * @return True if the folder is archived.
	 */
	public boolean isArchived() {
		return _archived == 1;
	}
	/**
	 * @param Don't Set This
	 */
	public void setArchived(int archived) {
		_archived = archived;
	}
	/**
	 * @return True if the name
	 */
	public String getName() {
		return _name;
	}
	/**
	 * @param content the _content to set
	 */
	public void setName(String name) {
		_name = name;
	}
	public void setPrivate(int value) {
		_private = value;
	}
	public boolean isPrivate() {
		return _private == 1;
	}
	public void setOrder(int order) {
		_order = order;
	}
	public int getOrder() {
		return _order;
	}
}
