package com.jeecms.cms.dao.assist;

import java.util.List;

import com.jeecms.cms.entity.assist.CmsComment;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;

public interface CmsCommentDao{
	public Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize, boolean cacheable);

	public List<CmsComment> getList(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int count, boolean cacheable);

	public CmsComment findById(Integer id);

	public int deleteByContentId(Integer contentId);

	public CmsComment save(CmsComment bean);

	public CmsComment updateByUpdater(Updater<CmsComment> updater);

	public CmsComment deleteById(Integer id);

	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize,boolean cacheable);

	public List<CmsComment> getListForDel(Integer siteId, Integer userId,
			Integer commentUserId, String ip);
}