package com.maldworth.toodledo.response.models;


public class Note
{
	private long _id;
	private long _folder;//ID of a folder it belongs to
	private int _private;
	private String _title;
	private String _text;
	private long _added;
	private long _modified;
	
	public long getId() {
		return _id;
	}
	public long getFolder() {
		return _folder;
	}
	public boolean isPrivate() {
		return _private == 1;
	}
	public String getTitle() {
		return _title;
	}
	public String getText() {
		return _text;
	}
	public long getAdded() {
		return _added;
	}
	public long getModified() {
		return _modified;
	}
	public void setId(long id) {
		_id = id;
	}
	public void setFolder(long folder) {
		_folder = folder;
	}
	public void setPrivate(int isPrivate) {
		_private = isPrivate;
	}
	public void setTitle(String title) {
		_title = title;
	}
	public void setText(String text) {
		_text = text;
	}
	public void setAdded(long added) {
		_added = added;
	}
	public void setModified(long modified) {
		_modified = modified;
	}
}
