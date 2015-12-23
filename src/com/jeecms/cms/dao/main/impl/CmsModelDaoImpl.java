package com.jeecms.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.main.CmsModelDao;
import com.jeecms.cms.entity.main.CmsModel;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsModelDaoImpl extends HibernateBaseDao<CmsModel, Integer>
		implements CmsModelDao {
	@SuppressWarnings("unchecked")
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent) {
		Finder f = Finder.create("from CmsModel bean");
		if (!containDisabled) {
			f.append(" where bean.disabled=false");
		}
		if(hasContent!=null){
			if(hasContent){
				f.append(" and bean.hasContent=true");
			}else{
				f.append(" and bean.hasContent=false");
			}
		}
		f.append(" order by bean.priority");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public CmsModel getDefModel() {
		String hql = "from CmsModel bean where bean.def=true";
		List<CmsModel> list = getSession().createQuery(hql).setMaxResults(1)
				.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CmsModel findById(Integer id) {
		CmsModel entity = get(id);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public CmsModel findByPath(String path){
		String hql = "from CmsModel bean where bean.path=:path";
		List<CmsModel> list = getSession().createQuery(hql).setParameter("path", path).setMaxResults(1).list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CmsModel save(CmsModel bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsModel deleteById(Integer id) {
		CmsModel entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsModel> getEntityClass() {
		return CmsModel.class;
	}
}