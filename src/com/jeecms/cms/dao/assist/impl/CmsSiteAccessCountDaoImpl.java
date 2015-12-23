package com.jeecms.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsSiteAccessCountDao;
import com.jeecms.cms.entity.assist.CmsSiteAccessCount;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessCountDaoImpl extends
		HibernateBaseDao<CmsSiteAccessCount, Integer> implements
		CmsSiteAccessCountDao {


	public List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin,Date end){
		String hql="select sum(bean.visitors),bean.pageCount from CmsSiteAccessCount bean where bean.site.id=:siteId";
		Finder f=Finder.create(hql).setParam("siteId", siteId);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:begin").setParam("begin", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:end").setParam("end", end);
		}
		f.append(" group by  bean.pageCount");
		return find(f);
	}

	public List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year) {
		String hql="select sum(bean.visitors),bean.pageCount from CmsSiteAccessCount bean where bean.site.id=:siteId";
		Finder f=Finder.create(hql).setParam("siteId", siteId);
		if(year!=null){
			f.append(" and  year(bean.statisticDate)=:year").setParam("year", year);
		}
		f.append(" group by  bean.pageCount");
		return find(f);
	}
	
	

	public CmsSiteAccessCount save(CmsSiteAccessCount bean) {
		getSession().save(bean);
		return bean;
	}

	protected Class<CmsSiteAccessCount> getEntityClass() {
		return CmsSiteAccessCount.class;
	}

}
