package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.utils.Helpers;

public class EditNote extends BasicToodledoRequestModel
{
	private String _title;
	private final long _id;
	private long _folder = -1L;
	private int _private = -1;
	private String _note;
	
	public EditNote(long id)
	{
		super("editNote", null);
		
		_id = id;
		
		this.addParam("id", id);
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{		
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		if(_private != -1)
			this.addParam("private", _private);
		
		if(_folder != -1L)
			this.addParam("folder", _folder);
		
		if(_note != null)
			this.addParam("note", Helpers.encode(_note));
		
		return super.getQueryParams();
	}

	public String getTitle() {
		return _title;
	}

	public long getId() {
		return _id;
	}

	public long getFolder() {
		return _folder;
	}

	public boolean isPrivate() {
		return _private == 1;
	}
	
	public String getNote()	{
		return _note;
	}

	public void setTitle(String title) {
		_title = title;
	}
	
	public void setFolder(long folder) {
		_folder = folder;
	}

	public void setPrivate(boolean isPrivate) {
		_private = (isPrivate)?1:0;
	}
	
	public void setNote(String note) {
		_note = note;
	}
}
