package com.jeecms.core.dao;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.entity.CmsUserSite;

public interface CmsUserSiteDao {
	public CmsUserSite findById(Integer id);

	public CmsUserSite save(CmsUserSite bean);

	public CmsUserSite updateByUpdater(Updater<CmsUserSite> updater);

	public int deleteBySiteId(Integer siteId);

	public CmsUserSite deleteById(Integer id);

	public void delete(CmsUserSite entity);
}