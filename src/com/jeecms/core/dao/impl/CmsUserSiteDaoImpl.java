package com.jeecms.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.core.dao.CmsUserSiteDao;
import com.jeecms.core.entity.CmsUserSite;

@Repository
public class CmsUserSiteDaoImpl extends HibernateBaseDao<CmsUserSite, Integer>
		implements CmsUserSiteDao {
	public CmsUserSite findById(Integer id) {
		CmsUserSite entity = get(id);
		return entity;
	}

	public CmsUserSite save(CmsUserSite bean) {
		getSession().save(bean);
		return bean;
	}

	public int deleteBySiteId(Integer siteId) {
		String hql = "delete from CmsUserSite bean where bean.site.id=:siteId";
		return getSession().createQuery(hql).setParameter("siteId", siteId)
				.executeUpdate();
	}

	public CmsUserSite deleteById(Integer id) {
		CmsUserSite entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public void delete(CmsUserSite entity) {
		getSession().delete(entity);
	}

	@Override
	protected Class<CmsUserSite> getEntityClass() {
		return CmsUserSite.class;
	}
}