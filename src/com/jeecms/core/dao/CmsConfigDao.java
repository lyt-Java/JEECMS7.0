package com.jeecms.core.dao;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.entity.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}