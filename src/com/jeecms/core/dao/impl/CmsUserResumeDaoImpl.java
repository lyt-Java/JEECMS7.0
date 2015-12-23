package com.jeecms.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.core.dao.CmsUserResumeDao;
import com.jeecms.core.entity.CmsUserResume;

@Repository
public class CmsUserResumeDaoImpl extends HibernateBaseDao<CmsUserResume, Integer> implements CmsUserResumeDao {
	public CmsUserResume findById(Integer id) {
		CmsUserResume entity = get(id);
		return entity;
	}

	public CmsUserResume save(CmsUserResume bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserResume> getEntityClass() {
		return CmsUserResume.class;
	}
}