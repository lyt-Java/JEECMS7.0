package com.jeecms.cms.dao.assist;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.entity.assist.CmsScoreItem;

public interface CmsScoreItemDao {
	public Pagination getPage(Integer groupId,int pageNo, int pageSize);

	public CmsScoreItem findById(Integer id);

	public CmsScoreItem save(CmsScoreItem bean);

	public CmsScoreItem updateByUpdater(Updater<CmsScoreItem> updater);

	public CmsScoreItem deleteById(Integer id);
}