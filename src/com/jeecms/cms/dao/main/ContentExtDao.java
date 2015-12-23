package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.common.hibernate3.Updater;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}