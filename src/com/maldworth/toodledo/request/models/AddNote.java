package com.maldworth.toodledo.request.models;

import java.util.List;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.maldworth.toodledo.request.BasicToodledoRequestModel;
import com.maldworth.toodledo.utils.DateTimeHelper;
import com.maldworth.utils.Helpers;

public class AddNote extends BasicToodledoRequestModel
{
	private String _title;
	private long _folder = -1L;
	private int _private = -1;
	private String _note;
	private DateTime _addedOn;
	
	
	public AddNote(String title)
	{
		super("addNote", null);
		
		_title = title;
	}
	
	@Override
	public List<NameValuePair> getQueryParams()
	{
		if(Helpers.isNullOrEmptyOrBlank(_title) == false)
			this.addParam("title", Helpers.encode(_title));
		
		if(_folder != -1L)
			this.addParam("folder", _folder);
		
		if(_private != -1)
			this.addParam("private", _private);
		
		if(Helpers.isNullOrEmpty(_note) == false)
			this.addParam("note", Helpers.encode(_note));
		
		if(_addedOn != null)
			this.addParam("addedon", DateTimeHelper.toYMD(_addedOn));
		
		return super.getQueryParams();
	}

	public String getTitle() {
		return _title;
	}

	public long getFolder() {
		return _folder;
	}

	public boolean isPrivate() {
		return _private == 1;
	}

	public String getNote() {
		return _note;
	}
	
//	public DateTime getAddedOn() {
//		return _addedOn;
//	}

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
	
//	public void setAddedOn(DateTime addedOn) {
//		_addedOn = addedOn;
//	}
}
