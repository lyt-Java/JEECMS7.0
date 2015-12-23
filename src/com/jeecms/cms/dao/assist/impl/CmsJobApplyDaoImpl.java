package com.jeecms.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsJobApplyDao;
import com.jeecms.cms.entity.assist.CmsJobApply;

@Repository
public class CmsJobApplyDaoImpl extends HibernateBaseDao<CmsJobApply, Integer>
		implements CmsJobApplyDao {
	public Pagination getPage(Integer userId, Integer contentId,
			Integer siteId, boolean cacheable, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsJobApply apply where 1=1 ");
		if (userId != null) {
			f.append(" and apply.user.id=:userId").setParam("userId", userId);
		}
		if (contentId != null) {
			f.append(" and apply.content.id=:contentId").setParam("contentId",
					contentId);
		}
		if (siteId != null) {
			f.append(" and apply.content.site.id=:siteId").setParam("siteId",
					siteId);
		}
		f.setCacheable(cacheable);
		Pagination page = find(f, pageNo, pageSize);
		return page;
	}

	public CmsJobApply findById(Integer id) {
		CmsJobApply entity = get(id);
		return entity;
	}

	public CmsJobApply save(CmsJobApply bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsJobApply deleteById(Integer id) {
		CmsJobApply entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsJobApply> getEntityClass() {
		return CmsJobApply.class;
	}
}