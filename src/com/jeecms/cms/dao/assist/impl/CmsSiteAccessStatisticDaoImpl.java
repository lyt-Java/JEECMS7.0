package com.jeecms.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsSiteAccessStatisticDao;
import com.jeecms.cms.entity.assist.CmsSiteAccessStatistic;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_ALL;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_SOURCE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_ENGINE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_LINK;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_KEYWORD;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_AREA;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_IP;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_PV;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_VISITORS;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_TARGET_VISITSECOND;

/**
 * @author Tom
 */
@Repository
public class CmsSiteAccessStatisticDaoImpl extends
		HibernateBaseDao<CmsSiteAccessStatistic, Integer> implements
		CmsSiteAccessStatisticDao {

	public CmsSiteAccessStatistic save(CmsSiteAccessStatistic bean) {
		getSession().save(bean);
		return bean;
	}

	public List<Object[]> statistic(Date begin, Date end,
			Integer siteId, String statisticType,String statisticValue) {
		Finder f = Finder.create("select bean.pv,bean.ip,bean.visitors,bean.visitSecondAver,bean.statisticDate from CmsSiteAccessStatistic bean where bean.site.id=:siteId "
				+ "and bean.statisitcType=:statisticType ")
		.setParam("siteId", siteId).setParam("statisticType",statisticType);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:beginDate ").setParam("beginDate", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:endDate ").setParam("endDate",  end);
		}
		if(StringUtils.isNotBlank(statisticValue)){
			f.append(" and bean.statisticColumnValue=:statisticColumnValue").setParam("statisticColumnValue", statisticValue);
		}
		f.append(" order by bean.statisticDate asc");
		return find(f);
	}
	
	public List<Object[]> statisticTotal(Date begin, Date end,Integer siteId, String statisticType,String statisticValue,Integer orderBy) {
		Finder f = Finder.create("select sum(bean.pv) as pv ,count(distinct bean.ip) as ip ,sum(bean.visitors) as visitors,(sum(bean.visitSecondAver * bean.visitors)/sum(bean.visitors)) as second from CmsSiteAccessStatistic bean where bean.site.id=:siteId "
				+ "and bean.statisitcType=:statisticType ")
		.setParam("siteId", siteId).setParam("statisticType",statisticType);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:beginDate ").setParam("beginDate", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:endDate ").setParam("endDate",  end);
		}
		if(StringUtils.isNotBlank(statisticValue)){
			f.append(" and bean.statisticColumnValue=:statisticColumnValue").setParam("statisticColumnValue", statisticValue);
		}
		if(orderBy!=null){
			if(orderBy==0){
				f.append(" order by bean.pv desc");
			}else if(orderBy==1){
				f.append(" order by bean.ip desc");
			}else if(orderBy==2){
				f.append(" order by bean.visitors desc");
			}else if(orderBy==3){
				f.append(" order by bean.second desc");
			}
		}else{
			f.append(" order by bean.pv desc");
		}
		return find(f);
	}
	
	public List<Object[]> statisticByTarget(Date begin, Date end,Integer siteId,Integer target, String statisticType,String statisticValue) {
		String hql="";
		if(target==STATISTIC_TARGET_PV){
			hql="select sum(bean.pv) as pv,bean.statisticDate";
		}else if(target==STATISTIC_TARGET_IP){
			 hql="select count(distinct bean.ip) as ip,bean.statisticDate";
		}else if(target==STATISTIC_TARGET_VISITORS){
			 hql="select sum(bean.visitors) as visitors, bean.statisticDate";
		}else if(target==STATISTIC_TARGET_VISITSECOND){
			 hql="select sum(bean.visitSecondAver) as second, bean.statisticDate";
		}
		hql+=" from CmsSiteAccessStatistic bean where bean.site.id=:siteId and bean.statisitcType=:statisticType";
		Finder f = Finder.create(hql).setParam("siteId", siteId).setParam("statisticType",statisticType);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:beginDate ").setParam("beginDate", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:endDate ").setParam("endDate",  end);
		}
		if(StringUtils.isNotBlank(statisticValue)){
			f.append(" and bean.statisticColumnValue=:statisticColumnValue").setParam("statisticColumnValue", statisticValue);
		}
		f.append(" group by bean.statisticDate order by bean.statisticDate asc");
		return find(f);
	}
	
	public List<String> findStatisticColumnValues(Date begin, Date end,Integer siteId, String statisticType) {
		String hql="select bean.statisticColumnValue  from CmsSiteAccessStatistic bean where bean.site.id=:siteId  and bean.statisitcType=:statisticType";
		Finder f = Finder.create(hql).setParam("siteId", siteId).setParam("statisticType",statisticType);
		if(begin!=null){
			f.append(" and bean.statisticDate>=:beginDate ").setParam("beginDate", begin);
		}
		if(end!=null){
			f.append(" and bean.statisticDate<=:endDate ").setParam("endDate",  end);
		}
		f.append(" group by bean.statisticColumnValue");
		return find(f);
	}
	
	public List<Object[]> statisticByYear(Integer year,Integer siteId, String statisticType,String statisticValue,boolean groupByMonth,Integer orderBy){
		Finder f = Finder.create("select sum(bean.pv)as pv,sum(bean.ip)as ip,sum(bean.visitors)as visitors,(sum(bean.visitSecondAver * bean.visitors)/sum(bean.visitors)) as second, month(bean.statisticDate)as m from CmsSiteAccessStatistic bean where bean.site.id=:siteId "
				+ " and year(bean.statisticDate)=:year "
				+ " and bean.statisitcType=:statisticType ")
		.setParam("siteId", siteId).setParam("year", year).setParam("statisticType",statisticType);
		if(StringUtils.isNotBlank(statisticValue)){
			f.append(" and bean.statisticColumnValue=:statisticColumnValue").setParam("statisticColumnValue", statisticValue);
		}
		if(groupByMonth){
			f.append(" group by month(bean.statisticDate) order by month(bean.statisticDate) asc");
		}
		if(orderBy!=null){
			if(orderBy==0){
				f.append(" order by bean.pv desc");
			}else if(orderBy==1){
				f.append(" order by bean.ip desc");
			}else if(orderBy==2){
				f.append(" order by bean.visitors desc");
			}else if(orderBy==3){
				f.append(" order by bean.second desc");
			}
		}
		return find(f);
	}
	
	public List<Object[]> statisticByYearByTarget(Integer year,Integer siteId, Integer target,String statisticType,String statisticValue){
		String hql="";
		if(target==STATISTIC_TARGET_PV){
			hql="select sum(bean.pv)as pv,month(bean.statisticDate)as m";
		}else if(target==STATISTIC_TARGET_IP){
			 hql="select count(bean.ip)as ip,month(bean.statisticDate)as m";
		}else if(target==STATISTIC_TARGET_VISITORS){
			 hql="select sum(bean.visitors)as visitors, month(bean.statisticDate)as m";
		}else if(target==STATISTIC_TARGET_VISITSECOND){
			 hql="select sum(bean.visitSecondAver)as second, month(bean.statisticDate)as m";
		}
		hql+=" from CmsSiteAccessStatistic bean where bean.site.id=:siteId  and year(bean.statisticDate)=:year and bean.statisitcType=:statisticType";
		Finder f = Finder.create(hql).setParam("siteId", siteId).setParam("year", year).setParam("statisticType",statisticType);
		if(StringUtils.isNotBlank(statisticValue)){
			f.append(" and bean.statisticColumnValue=:statisticColumnValue").setParam("statisticColumnValue", statisticValue);
		}
		f.append(" group by month(bean.statisticDate) order by month(bean.statisticDate) asc");
		return find(f);
	}

	protected Class<CmsSiteAccessStatistic> getEntityClass() {
		return CmsSiteAccessStatistic.class;
	}
}
