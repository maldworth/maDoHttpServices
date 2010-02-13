package com.maldworth.toodledo.response.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.maldworth.toodledo.enums.DateFormat;
import com.maldworth.toodledo.enums.Priority;
import com.maldworth.toodledo.response.ToodledoResponseModelBase;

public class AccountInfo extends ToodledoResponseModelBase
{
	private String _userId;
	private String _alias;
	private int _pro;
	private DateFormat _dateFormat;
	private int _timeZone;
	private int _hideMonths;
	private Priority _hotListPriority;
	private int _hotListDueDate;
	private long _lastAddEdit;
	private long _lastDelete;
	private long _lastFolderEdit;
	private long _lastContextEdit;
	private long _lastGoalEdit;
	
	public AccountInfo()
	{
		super();
	}
	
	/**
	 * @return The unique userID of the current user.
	 */
	public String getUserId() {
		return _userId;
	}

	/**
	 * @return The alias the user chose to be displayed (Could be full name, or a  nickname).
	 */
	public String getAlias() {
		return _alias;
	}

	/**
	 * @return Whether or not the user is a Pro member. You need to know this if you want to use subtasks.
	 */
	public boolean isPro()
	{
		return _pro == 1;
	}
	
	/**
	 * @return The user's preferred format for representing dates.
	 */
	public DateFormat getDateFormat()
	{
		return _dateFormat;
	}

	/**
	 * @return The number of half hours that the user's timezone is offset from the server's timezone. A value of -4 means that the user's timezone is 2 hours earlier than the server's timezone.
	 */
	public int getTimeZone()
	{
		return _timeZone;
	}

	/**
	 * @return If the task is due this many months into the future, the user wants them to be hidden.
	 */
	public int getHideMonths() {
		return _hideMonths;
	}

	/**
	 * @return The priority value above which tasks should appear on the hotlist.
	 */
	public Priority getHotListPriority() {
		return _hotListPriority;
	}

	/**
	 * @return The due date lead-time by which tasks should will appear on the hotlist.
	 */
	public int getHotListDueDate() {
		return _hotListDueDate;
	}

	/**
	 * @return A timestamp that indicates the last time that any task was added or edited on this account. You can quickly check this field to determine if you need to download updates.
	 */
	public long getLastAddEdit() {
		return _lastAddEdit;
	}

	/**
	 * @return A timestamp that indicates the last time that any task was deleted from this account. You can quickly check this field to determine if you need to identify and remove tasks from your application.
	 */
	public long getLastDelete() {
		return _lastDelete;
	}

	/**
	 * @return A timestamp that indicates the last time that a folder was added, edited or deleted. You can quickly check this field to determine if you need to call getFolders to refresh your cached folder list.
	 */
	public long getLastFolderEdit() {
		return _lastFolderEdit;
	}

	/**
	 * @return A timestamp that indicates the last time that a context was added, edited or deleted. You can quickly check this field to determine if you need to call getContexts to refresh your cached context list.
	 */
	public long getLastContextEdit() {
		return _lastContextEdit;
	}

	/**
	 * @return A timestamp that indicates the last time that a goal was added, edited or deleted. You can quickly check this field to determine if you need to call getGoals to refresh your cached goal list.
	 */
	public long getLastGoalEdit() {
		return _lastGoalEdit;
	}
	
	private void setUserId(String userId) {
		_userId = userId;
	}
	
	private void setAlias(String alias) {
		_alias = alias;
	}
	
	private void setPro(int pro) {
		_pro = pro;
	}
	
	private void setDateFormat(int dateFormat) {
		this._dateFormat = DateFormat.values()[dateFormat];
	}
	
	private void setTimeZone(int timeZone) {
		_timeZone = timeZone;
	}
	
	private void setHideMonths(int hideMonths) {
		_hideMonths = hideMonths;
	}
	
	private void setHotListPriority(int hotListPriority) {
		_hotListPriority = Priority.values()[hotListPriority];
	}
	
	private void setHotListDueDate(int hotListDueDate) {
		_hotListDueDate = hotListDueDate;
	}
	
	private void setLastAddEdit(long lastAddEdit) {
		_lastAddEdit = lastAddEdit;
	}
	
	private void setLastDelete(long lastDelete) {
		_lastDelete = lastDelete;
	}
	
	private void setLastFolderEdit(long lastFolderEdit) {
		_lastFolderEdit = lastFolderEdit;
	}
	
	private void setLastContextEdit(long lastContextEdit) {
		_lastContextEdit = lastContextEdit;
	}
	
	private void setLastGoalEdit(long lastGoalEdit) {
		_lastGoalEdit = lastGoalEdit;
	}

	/*
	 * #####################################################################
	 */

	boolean _inAccountTag = false;
	boolean _inUserIdTag = false;
	boolean _inAliasTag = false;
	boolean _inProTag = false;
	boolean _inDateFormatTag = false;
	boolean _inTimeZoneTag = false;
	boolean _inHideMonthsTag = false;
	boolean _inHotListPriorityTag = false;
	boolean _inHotListDueDateTag = false;
	boolean _inLastAddEditTag = false;
	boolean _inLastDeleteTag = false;
	boolean _inLastFolderEditTag = false;
	boolean _inLastContextEditTag = false;
	boolean _inLastGoalEditTag = false;
	
	@Override
	public void startElement(String namespaceURI, String localName,	String qName, Attributes atts)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("account".equals(theTagName)) {
			this._inAccountTag = true;
		}else if ("userid".equals(theTagName)) {
			this._inUserIdTag = true;
		}else if ("alias".equals(theTagName)) {
			this._inAliasTag = true;
		}else if ("pro".equals(theTagName)) {
			this._inProTag = true;
		}else if ("dateformat".equals(theTagName)) {
			this._inDateFormatTag = true;
		}else if ("timezone".equals(theTagName)) {
			this._inTimeZoneTag = true;
		}else if ("hidemonths".equals(theTagName)) {
			this._inHideMonthsTag = true;
		}else if ("hotlistpriority".equals(theTagName)) {
			this._inHotListPriorityTag = true;
		}else if ("hotlistduedate".equals(theTagName)) {
			this._inHotListDueDateTag = true;
		}else if ("lastaddedit".equals(theTagName)) {
			this._inLastAddEditTag = true;
		}else if ("lastdelete".equals(theTagName)) {
			this._inLastDeleteTag = true;
		}else if ("lastfolderedit".equals(theTagName)) {
			this._inLastFolderEditTag = true;
		}else if ("lastcontextedit".equals(theTagName)) {
			this._inLastContextEditTag = true;
		}else if ("lastgoaledit".equals(theTagName)) {
			this._inLastGoalEditTag = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
		String theTagName = (this.isAndroid() == true)? localName : qName;
		
		if ("account".equals(theTagName)) {
			this._inAccountTag = false;
		}else if ("userid".equals(theTagName)) {
			this._inUserIdTag = false;
		}else if ("alias".equals(theTagName)) {
			this._inAliasTag = false;
		}else if ("pro".equals(theTagName)) {
			this._inProTag = false;
		}else if ("dateformat".equals(theTagName)) {
			this._inDateFormatTag = false;
		}else if ("timezone".equals(theTagName)) {
			this._inTimeZoneTag = false;
		}else if ("hidemonths".equals(theTagName)) {
			this._inHideMonthsTag = false;
		}else if ("hotlistpriority".equals(theTagName)) {
			this._inHotListPriorityTag = false;
		}else if ("hotlistduedate".equals(theTagName)) {
			this._inHotListDueDateTag = false;
		}else if ("lastaddedit".equals(theTagName)) {
			this._inLastAddEditTag = false;
		}else if ("lastdelete".equals(theTagName)) {
			this._inLastDeleteTag = false;
		}else if ("lastfolderedit".equals(theTagName)) {
			this._inLastFolderEditTag = false;
		}else if ("lastcontextedit".equals(theTagName)) {
			this._inLastContextEditTag = false;
		}else if ("lastgoaledit".equals(theTagName)) {
			this._inLastGoalEditTag = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if(this._inAccountTag)
		{
			if(this._inUserIdTag){
				setUserId(new String(ch, start, length));
			}else if(this._inAliasTag){
				setAlias(new String(ch, start, length));
			}else if(this._inProTag){
				setPro(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inDateFormatTag){
				setDateFormat(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inTimeZoneTag){
				setTimeZone(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inHideMonthsTag){
				setHideMonths(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inHotListPriorityTag){
				setHotListPriority(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inHotListDueDateTag){
				setHotListDueDate(Integer.parseInt(new String(ch, start, length)));
			}else if(this._inLastAddEditTag){
				setLastAddEdit(Long.parseLong(new String(ch, start, length)));
			}else if(this._inLastDeleteTag){
				setLastDelete(Long.parseLong(new String(ch, start, length)));
			}else if(this._inLastFolderEditTag){
				setLastFolderEdit(Long.parseLong(new String(ch, start, length)));
			}else if(this._inLastContextEditTag){
				setLastContextEdit(Long.parseLong(new String(ch, start, length)));
			}else if(this._inLastGoalEditTag){
				setLastGoalEdit(Long.parseLong(new String(ch, start, length)));
			}
		}
	}
}
