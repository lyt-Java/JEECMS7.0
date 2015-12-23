package com.jeecms.cms.dao.assist;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.entity.assist.CmsScoreGroup;

public interface CmsScoreGroupDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsScoreGroup findById(Integer id);
	
	public CmsScoreGroup findDefault(Integer siteId);

	public CmsScoreGroup save(CmsScoreGroup bean);

	public CmsScoreGroup updateByUpdater(Updater<CmsScoreGroup> updater);

	public CmsScoreGroup deleteById(Integer id);
}