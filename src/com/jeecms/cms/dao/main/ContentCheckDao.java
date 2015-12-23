package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.ContentCheck;
import com.jeecms.common.hibernate3.Updater;

public interface ContentCheckDao {
	public ContentCheck findById(Long id);

	public ContentCheck save(ContentCheck bean);

	public ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}