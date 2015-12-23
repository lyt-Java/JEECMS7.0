package com.jeecms.cms.dao.assist.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsSiteAccessPagesDao;
import com.jeecms.cms.entity.assist.CmsSiteAccessPages;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessPagesDaoImpl extends
		HibernateBaseDao<CmsSiteAccessPages, Integer> implements
		CmsSiteAccessPagesDao {

	public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex) {
		Finder f = Finder.create("from CmsSiteAccessPages bean where bean.sessionId=:sessionId and bean.pageIndex=:pageIndex")
				.setParam("sessionId", sessionId).setParam("pageIndex",
						pageIndex);
		List<CmsSiteAccessPages> pages = find(f);
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		} else {
			return null;
		}
	}
	
	public Pagination findPages(Integer siteId,Integer orderBy,Integer pageNo,Integer pageSize){
		Finder f = Finder.create("select bean.accessPage,count(bean.accessPage),count(distinct bean.sessionId),sum(bean.visitSecond)/count(bean.accessPage) " +
				"from CmsSiteAccessPages bean where bean.site.id=:siteId").setParam("siteId", siteId);
		f.append(" group by bean.accessPage ");
		String totalHql="select count(distinct bean.accessPage) from CmsSiteAccessPages bean where bean.site.id=:siteId";
		if(orderBy!=null){
			if(orderBy==2){
				//访客数降序
				f.append(" order by count(distinct bean.sessionId) desc");
			}else if(orderBy==3){
				//每次停留时间降序
				f.append(" order by sum(bean.visitSecond)/count(bean.accessPage) desc");
			}else{
				//pv降序
				f.append(" order by count(bean.accessPage) desc");
			}
		}else{
			f.append(" order by count(bean.accessPage) desc");
		}
		return find(f,totalHql,pageNo,pageSize);
	}

	public void clearByDate(Date date) {
		//只保留当天数据
		String hql="delete from CmsSiteAccessPages bean where bean.accessDate!=:date";
		getSession().createQuery(hql).setParameter("date",date).executeUpdate();
	}

	public CmsSiteAccessPages save(CmsSiteAccessPages bean) {
		getSession().save(bean);
		return bean;
	}

	
	private Pagination find(Finder finder,String totalHql, int pageNo, int pageSize) {
		int totalCount = countQueryResult(finder,totalHql);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		List list = query.list();
		for(int i=0;i<list.size();i++){
			Object[]o=(Object[]) list.get(i);
		}
		p.setList(list);
		return p;
	}
	
	private int countQueryResult(Finder finder,String hql) {
		Query query = getSession().createQuery(hql);
		finder.setParamsToQuery(query);
		if (finder.isCacheable()) {
			query.setCacheable(true);
		}
		return ((Number) query.iterate().next()).intValue();
	}

	protected Class<CmsSiteAccessPages> getEntityClass() {
		return CmsSiteAccessPages.class;
	}

}
