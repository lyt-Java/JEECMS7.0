package com.jeecms.cms.dao.assist.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsUserMenuDao;
import com.jeecms.cms.entity.assist.CmsUserMenu;

@Repository
public class CmsUserMenuDaoImpl extends HibernateBaseDao<CmsUserMenu, Integer> implements CmsUserMenuDao {
	public Pagination getPage(Integer userId,int pageNo, int pageSize) {
		String hql="from CmsUserMenu menu ";
		Finder f=Finder.create(hql);
		if(userId!=null){
			f.append(" where menu.user.id=:userId").setParam("userId", userId);
		}
		f.append(" order by menu.priority asc");
		return find(f, pageNo, pageSize);
	}
	
	public List<CmsUserMenu> getList(Integer userId,int count){
		String hql="from CmsUserMenu menu ";
		Finder f=Finder.create(hql);
		if(userId!=null){
			f.append(" where menu.user.id=:userId").setParam("userId", userId);
		}
		f.append(" order by menu.priority asc");
		f.setFirstResult(0);
		f.setMaxResults(count);
		return find(f);
	}

	public CmsUserMenu findById(Integer id) {
		CmsUserMenu entity = get(id);
		return entity;
	}

	public CmsUserMenu save(CmsUserMenu bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsUserMenu deleteById(Integer id) {
		CmsUserMenu entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsUserMenu> getEntityClass() {
		return CmsUserMenu.class;
	}
}