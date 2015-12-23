package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsPlug;



public class CmsPlug extends BaseCmsPlug {
	private static final long serialVersionUID = 1L;
	
	public boolean getUsed(){
		return isUsed();
	}
	
	public boolean getFileConflict () {
		return isFileConflict();
	}
	
	public boolean getCanInstall(){
		if(!getUsed()&&!getFileConflict()){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean getCanUnInstall(){
		if(getUsed()&&!getFileConflict()){
			return true;
		}else{
			return false;
		}
	}

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsPlug () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsPlug (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsPlug (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String path,
		java.util.Date uploadTime,
		boolean fileConflict,
		boolean used) {

		super (
			id,
			name,
			path,
			uploadTime,
			fileConflict,
			used);
	}

/*[CONSTRUCTOR MARKER END]*/


}