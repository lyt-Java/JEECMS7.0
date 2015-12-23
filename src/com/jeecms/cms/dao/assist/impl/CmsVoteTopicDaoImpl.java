package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsVoteTopicDao;
import com.jeecms.cms.entity.assist.CmsVoteTopic;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class CmsVoteTopicDaoImpl extends
		HibernateBaseDao<CmsVoteTopic, Integer> implements CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsVoteTopic bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsVoteTopic> getList(Boolean def,Integer siteId, int count){
		Finder f = Finder.create("from CmsVoteTopic bean where 1=1");
		if(def!=null){
			if(def){
				f.append(" and bean.def=true");
			}else{
				f.append(" and bean.def=false");
			}
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		f.setMaxResults(count);
		return find(f);
	}

	public CmsVoteTopic getDefTopic(Integer siteId) {
		Finder f = Finder.create("from CmsVoteTopic bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" and bean.def=true");
		f.setMaxResults(1);
		return (CmsVoteTopic) f.createQuery(getSession()).uniqueResult();
	}

	public CmsVoteTopic findById(Integer id) {
		CmsVoteTopic entity = get(id);
		return entity;
	}

	public CmsVoteTopic save(CmsVoteTopic bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsVoteTopic deleteById(Integer id) {
		CmsVoteTopic entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteTopic> getEntityClass() {
		return CmsVoteTopic.class;
	}
}