package com.jeecms.cms.dao.assist;

import java.util.Date;
import java.util.List;

import com.jeecms.cms.entity.assist.CmsSiteAccess;
import com.jeecms.common.page.Pagination;

/**
 * @author Tom
 */
public interface CmsSiteAccessDao {
	
	public CmsSiteAccess saveOrUpdate(CmsSiteAccess access);
	
	public Pagination findEnterPages(Integer siteId,Integer orderBy,Integer pageNo,Integer pageSize);
	
	public CmsSiteAccess findAccessBySessionId(String sessionId);
	
	/**
	 * 查询date之前最近的访问记录
	 * @param date
	 * @return
	 */
	public CmsSiteAccess findRecentAccess(Date  date,Integer siteId);
	/**
	 * 统计日期站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByDay(Date date,Integer siteId);
	/**
	 * 统计地区站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByArea(Date date,Integer siteId);
	/**
	 * 统计来源站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticBySource(Date date,Integer siteId);
	/**
	 * 统计搜索引擎站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByEngine(Date date,Integer siteId);
	/**
	 * 统计外部链接站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByLink(Date date,Integer siteId);
	/**
	 * 统计关键词站点流量
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByKeyword(Date date,Integer siteId);
	/**
	 * 统计用户访问页数
	 * @param date
	 * @param siteId
	 * @return
	 */
	public List<Object[]> statisticByPageCount(Date date,Integer siteId);
	
	/**
	 * 查询property列值
	 * @param property
	 * @param siteId
	 * @return
	 */
	public List<String> findPropertyValues(String property,Integer siteId);
	
	public List<Object[]> statisticToday(Integer siteId,String area);
	
	public List<Object[]> statisticTodayByTarget(Integer siteId,Integer target,String statisticColumn,String statisticValue);

	public void clearByDate(Date date);

}
