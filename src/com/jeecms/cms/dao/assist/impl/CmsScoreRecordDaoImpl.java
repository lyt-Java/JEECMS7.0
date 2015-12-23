package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsScoreRecordDao;
import com.jeecms.cms.entity.assist.CmsScoreRecord;

@Repository
public class CmsScoreRecordDaoImpl extends HibernateBaseDao<CmsScoreRecord, Integer> implements CmsScoreRecordDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}
	
	public List<CmsScoreRecord> findListByContent(Integer contentId){
		String hql="from CmsScoreRecord bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(contentId!=null){
			f.append(" and bean.content.id=:contentId").setParam("contentId", contentId);
		}
		return find(f);
	}
	
	public CmsScoreRecord findByScoreItemContent(Integer itemId,Integer contentId){
		String hql="from CmsScoreRecord bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(itemId!=null){
			f.append(" and bean.item.id=:itemId").setParam("itemId", itemId);
		}
		if(contentId!=null){
			f.append(" and bean.content.id=:contentId").setParam("contentId", contentId);
		}
		List<CmsScoreRecord>list=find(f);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public CmsScoreRecord findById(Integer id) {
		CmsScoreRecord entity = get(id);
		return entity;
	}

	public CmsScoreRecord save(CmsScoreRecord bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsScoreRecord deleteById(Integer id) {
		CmsScoreRecord entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsScoreRecord> getEntityClass() {
		return CmsScoreRecord.class;
	}
}