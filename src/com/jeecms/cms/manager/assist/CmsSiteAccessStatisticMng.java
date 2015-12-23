package com.jeecms.cms.manager.assist;

import java.util.Date;
import java.util.List;

import com.jeecms.cms.entity.assist.CmsSiteAccessStatistic;

/**
 * @author Tom
 */
public interface CmsSiteAccessStatisticMng {

	public CmsSiteAccessStatistic save(CmsSiteAccessStatistic statistic);

	public List<Object[]> statistic(Date begin, Date end,Integer siteId, String statisticType,String statisticValue);
	
	public List<Object[]> statisticTotal(Date begin, Date end,Integer siteId, String statisticType,String statisticValue,Integer orderBy);
	
	public List<Object[]> statisticByTarget(Date begin, Date end,Integer siteId,Integer target, String statisticType,String statisticValue);
	
	public List<String> findStatisticColumnValues(Date begin, Date end,Integer siteId, String statisticType);
	
	public List<Object[]> statisticByYear(Integer year,Integer siteId, String statisticType,String statisticValue,boolean groupByMonth,Integer orderBy);
	
	public List<Object[]> statisticByYearByTarget(Integer year,Integer siteId, Integer target,String statisticType,String statisticValue);
	
	
}
