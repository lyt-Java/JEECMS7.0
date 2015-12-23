package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsPlugDao;
import com.jeecms.cms.entity.assist.CmsPlug;

@Repository
public class CmsPlugDaoImpl extends HibernateBaseDao<CmsPlug, Integer> implements CmsPlugDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	public List<CmsPlug> getList(String author,Boolean used){
		Finder f=Finder.create("from CmsPlug plug where 1=1 ");
		if(StringUtils.isNotBlank(author)){
			f.append("and plug.author=:author").setParam("author", author);
		}
		if(used!=null){
			if(used){
				f.append("and plug.used=true");
			}else{
				f.append("and plug.used=false");
			}
		}
		return find(f);
	}

	public CmsPlug findById(Integer id) {
		CmsPlug entity = get(id);
		return entity;
	}
	
	public CmsPlug findByPath(String plugPath){
		Finder f=Finder.create("from CmsPlug plug where plug.path=:path").setParam("path", plugPath);
		List<CmsPlug>list=find(f);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public CmsPlug save(CmsPlug bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsPlug deleteById(Integer id) {
		CmsPlug entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsPlug> getEntityClass() {
		return CmsPlug.class;
	}
}