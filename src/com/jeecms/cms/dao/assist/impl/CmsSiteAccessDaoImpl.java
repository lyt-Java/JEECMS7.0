package com.jeecms.cms.dao.assist.impl;

import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_SOURCE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_ENGINE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_LINK;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_KEYWORD;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_AREA;

import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_IP;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_PV;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_VISITORS;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_VISITSECOND;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsSiteAccessDao;
import com.jeecms.cms.entity.assist.CmsSiteAccess;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessDaoImpl extends
		HibernateBaseDao<CmsSiteAccess, Integer> implements CmsSiteAccessDao {

	public CmsSiteAccess saveOrUpdate(CmsSiteAccess access) {
		getSession().saveOrUpdate(access);
		return access;
	}
	
	public Pagination findEnterPages(Integer siteId,Integer orderBy,Integer pageNo,Integer pageSize){
		Finder f = Finder.create("select bean.entryPage,sum(bean.visitPageCount),count(distinct bean.sessionId),sum(bean.visitSecond)/sum(bean.visitPageCount) " +
				"from CmsSiteAccess bean where bean.site.id=:siteId").setParam("siteId", siteId);
		f.append(" group by bean.entryPage ");
		String totalHql="select count(distinct bean.entryPage) from CmsSiteAccess bean where bean.site.id=:siteId";
		if(orderBy!=null){
			if(orderBy==2){
				//访客数降序
				f.append(" order by count(distinct bean.sessionId) desc");
			}else if(orderBy==3){
				//每次停留时间降序
				f.append(" order by sum(bean.visitSecond)/count(bean.entryPage) desc");
			}else{
				//pv降序
				f.append(" order by sum(bean.visitPageCount) desc");
			}
		}else{
			f.append(" order by sum(bean.visitPageCount) desc");
		}
		return find(f,totalHql,pageNo,pageSize);
	}

	public CmsSiteAccess findAccessBySessionId(String sessionId) {
		Finder f = Finder.create(
				"from CmsSiteAccess bean where bean.sessionId=:sessionId")
				.setParam("sessionId", sessionId);
		List<CmsSiteAccess> list = find(f);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public CmsSiteAccess findRecentAccess(Date date,Integer siteId){
		Finder f = Finder.create("from CmsSiteAccess access where  access.site.id=:siteId and access.accessDate!=:accessDate order by access.accessDate desc ")
		.setParam("siteId", siteId).setParam("accessDate", date);
		List<CmsSiteAccess> list = find(f);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Object[]> statisticByDay(Date date, Integer siteId) {
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond),'' from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}

	public List<Object[]> statisticByArea(Date date, Integer siteId) {
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond), access.area from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate and access.area!='' group by access.area")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<Object[]> statisticBySource(Date date, Integer siteId) {
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond), access.accessSource from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate  group by access.accessSource")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<Object[]> statisticByEngine(Date date,Integer siteId){
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond), access.engine from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate and  access.engine!='' group by access.engine")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<Object[]> statisticByLink(Date date,Integer siteId){
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond), access.externalLink from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate and  access.externalLink!='' group by access.externalLink")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<Object[]> statisticByKeyword(Date date,Integer siteId){
		Finder f = Finder.create("select sum(access.visitPageCount) as pv,count(distinct access.ip)as ip,count(distinct access.sessionId)as visitors ,sum(access.visitSecond), access.keyword from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate and  access.keyword!='' group by access.keyword")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<Object[]> statisticByPageCount(Date date,Integer siteId){
		Finder f = Finder.create("select count(distinct access.sessionId)as visitors ,access.visitPageCount from  CmsSiteAccess access where  access.site.id=:siteId and access.accessDate=:accessDate  group by access.visitPageCount order by count(distinct access.sessionId) desc")
		.setParam("siteId", siteId).setParam("accessDate", date);
		return find(f);
	}
	
	public List<String> findPropertyValues(String property,Integer siteId) {
		String hql="select distinct bean."+property+"  from CmsSiteAccess bean where bean.site.id=:siteId and  bean."+property+" !='' ";
		Finder f = Finder.create(hql).setParam("siteId", siteId);
		return find(f);
	}
	
	
	public List<Object[]> statisticToday(Integer siteId,String area){
		String hql="select sum(bean.visitPageCount)as pv,count(distinct bean.ip)as ip,count(distinct bean.sessionId)as visitors,avg(bean.visitSecond) as second, hour(bean.accessTime)as m from CmsSiteAccess bean where bean.site.id=:siteId ";
		Finder f = Finder.create(hql);
		//趋势分析，按时间分组
		if(StringUtils.isBlank(area)){
			f.append(" group by hour(bean.accessTime) order by hour(bean.accessTime) asc").setParam("siteId", siteId);
		}else{
			f.append(" and bean.area=:area group by bean.area").setParam("siteId", siteId).setParam("area", area);
		}
		return find(f);
	}
	
	public List<Object[]> statisticTodayByTarget(Integer siteId,Integer target,String statisticColumn,String statisticValue){
		String hql = "";
		if(target==STATISTIC_TARGET_PV){
			hql="select sum(bean.visitPageCount)as pv ,hour(bean.accessTime)as m";
		}else if(target==STATISTIC_TARGET_IP){
			 hql="select count(distinct bean.ip)as ip, hour(bean.accessTime)as m";
		}else if(target==STATISTIC_TARGET_VISITORS){
			 hql="select count(distinct bean.sessionId)as visitors, hour(bean.accessTime)as m";
		}else if(target==STATISTIC_TARGET_VISITSECOND){
			 hql="select sum(bean.visitSecond)as second, hour(bean.accessTime)as m";
		}
		hql+=" from CmsSiteAccess bean where bean.site.id=:siteId ";
		Finder f = Finder.create(hql);
		if(statisticColumn.equals(STATISTIC_SOURCE)){
			f.append(" and bean.accessSource=:accessSource").setParam("accessSource", statisticValue);
		}else if(statisticColumn.equals(STATISTIC_ENGINE)){
			f.append(" and bean.engine=:engine").setParam("engine", statisticValue);
		}else if(statisticColumn.equals(STATISTIC_AREA)){
			f.append(" and bean.area=:area").setParam("area", statisticValue);
		}else if(statisticColumn.equals(STATISTIC_LINK)){
			f.append(" and bean.externalLink=:externalLink").setParam("externalLink", statisticValue);
		}else if(statisticColumn.equals(STATISTIC_KEYWORD)){
			f.append(" and bean.keyword=:keyword").setParam("keyword", statisticValue);
		}
		f.append(" group by hour(bean.accessTime) order by hour(bean.accessTime) asc").setParam("siteId", siteId);
		return find(f);
	}

	public void clearByDate(Date date) {
		String hql="delete from CmsSiteAccess bean where bean.accessDate!=:date";
		getSession().createQuery(hql).setParameter("date",date).executeUpdate();
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

	@Override
	protected Class<CmsSiteAccess> getEntityClass() {
		return CmsSiteAccess.class;
	}
}
