package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsDirectiveTplDao;
import com.jeecms.cms.entity.assist.CmsDirectiveTpl;

@Repository
public class CmsDirectiveTplDaoImpl extends HibernateBaseDao<CmsDirectiveTpl, Integer> implements CmsDirectiveTplDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	public List<CmsDirectiveTpl> getList(int count){
		String hql="from CmsDirectiveTpl";
		Finder f=Finder.create(hql);
		f.setFirstResult(0);
		f.setMaxResults(count);
		return find(f);
	}

	public CmsDirectiveTpl findById(Integer id) {
		CmsDirectiveTpl entity = get(id);
		return entity;
	}

	public CmsDirectiveTpl save(CmsDirectiveTpl bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsDirectiveTpl deleteById(Integer id) {
		CmsDirectiveTpl entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsDirectiveTpl> getEntityClass() {
		return CmsDirectiveTpl.class;
	}
}