package com.jeecms.cms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.jeecms.common.util.ParseURLKeyword.getKeyword;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jeecms.cms.entity.assist.CmsSiteAccess;
import com.jeecms.cms.entity.assist.CmsSiteAccessPages;
import com.jeecms.cms.manager.assist.CmsSiteAccessCountMng;
import com.jeecms.cms.manager.assist.CmsSiteAccessMng;
import com.jeecms.cms.manager.assist.CmsSiteAccessPagesMng;
import com.jeecms.cms.web.CmsThreadVariable;
import com.jeecms.common.ipseek.IpSeekUtils;
import com.jeecms.common.util.DateFormatUtils;
import com.jeecms.common.util.DateUtils;
import com.jeecms.common.util.UserAgentUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.springmvc.MessageResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.web.util.CmsUtils;

import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_BAIDU;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_GOOGLE;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_YAHOO;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_BING;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_SOGOU;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_SOSO;
import static com.jeecms.cms.entity.assist.CmsSiteAccess.ENGINE_SO;

import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_ALL;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_SOURCE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_ENGINE;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_LINK;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_KEYWORD;
import static com.jeecms.cms.entity.assist.CmsSiteAccessStatistic.STATISTIC_AREA;

@Service
public class CmsSiteFlowCacheImpl implements CmsSiteFlowCache, DisposableBean {
	private final String VISIT_COUNT="visitCount";
	private final String LAST_VISIT_TIME="lastVisitTime";
	private Logger log = LoggerFactory.getLogger(CmsSiteFlowCacheImpl.class);

		
	public Long[] flow(HttpServletRequest request,  String page, String referer) {
		String ip = RequestUtils.getIpAddr(request);
		CmsSite site=CmsUtils.getSite(request);
		String brower = UserAgentUtils.getBrowserInfo(request);
		String operatingSystem = UserAgentUtils.getClientOS(request);
		Date nowTime = DateFormatUtils.parseTime(Calendar.getInstance().getTime());
		Date nowDate = DateFormatUtils.parseDate(Calendar.getInstance().getTime());
		HttpSession session=request.getSession();
		String sessionId =session.getId();
		Integer visitCount=(Integer) session.getAttribute(VISIT_COUNT);
		Date lastVisitTime=(Date) session.getAttribute(LAST_VISIT_TIME);
		CmsSiteAccess access = null;
		CmsSiteAccess lastAccess = findLastAccess(site.getId());
		CmsSiteAccessPages accessPage;
		boolean firstVisitToday=false;
		boolean newVisitor=false;
		if(visitCount==null){
			visitCount=0;
			lastVisitTime=Calendar.getInstance().getTime();
			access=visitAccess(request, ip, sessionId, page, referer,brower,operatingSystem);
			//最新访问的时间比当前日期要早
			if(lastAccess==null||lastAccess.getAccessDate().before(nowDate)){
				firstVisitToday=true;
			}
			newVisitor=true;
		}else{
			access=findAccess(sessionId);
			if(access==null){
				access=visitAccess(request, ip, sessionId, page, referer,brower,operatingSystem);
				newVisitor=true;
			}
			access=updateAccess(access, page, visitCount+1, DateUtils.getSecondBetweenDate(access.getAccessTime(), nowTime));
		}
		accessPage=visitPages(site,page, sessionId, visitCount, lastVisitTime);
		visitCount+=1;
		session.setAttribute(VISIT_COUNT, visitCount);
		session.setAttribute(LAST_VISIT_TIME, Calendar.getInstance().getTime());
		accessCache.put(new Element(sessionId, access));
		accessPageCache.put(new Element(sessionId+visitCount, accessPage));
		lastAccessCache.put(new Element(site.getId(),access));
		//当天第一次访问统计昨日数据
		if(firstVisitToday){
			Thread thread = new StatisticThread(site.getId());
			thread.start();
		}
		return totalCache(site, newVisitor);
	}
	
	/**
	 * 统计当前流量信息入统计表
	 */
	private class StatisticThread extends Thread{
		private Integer siteId;
		
		public StatisticThread(Integer siteId) {
			this.siteId = siteId;
		}

		public void run() {
			Date today=Calendar.getInstance().getTime();
			//统计最近
			CmsSiteAccess latestBefore=cmsSiteAccessMng.findRecentAccess(today, this.siteId);
			if(latestBefore!=null){
				Date recent = DateFormatUtils.parseDate(latestBefore.getAccessDate());
				//每日总流量统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_ALL, recent, this.siteId);
				//地区统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_AREA, recent, this.siteId);
				//来源统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_SOURCE, recent, this.siteId);
				//搜索引擎统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_ENGINE, recent, this.siteId);
				//外部链接统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_LINK, recent, this.siteId);
				//关键词统计
				cmsSiteAccessMng.statisticByProperty(STATISTIC_KEYWORD, recent, this.siteId);
				//访问页数情况统计
				cmsSiteAccessCountMng.statisticCount(recent, this.siteId);
				//清除以往数据
				Date d=DateFormatUtils.parseDate(today);
				cmsSiteAccessMng.clearByDate(d);
				cmsSiteAccessPagesMng.clearByDate(d);
			}
		}
	}
	
	public Long[] totalCache(CmsSite site,boolean newVisitor) {
		Long pvTotal=site.getPvTotal();
		Long visitorTotal=site.getVisitorTotal();
		Element pvCache = pvTotalCache.get(site.getId());
		Long pv;
		if (pvCache != null) {
			pv = (Long) pvCache.getValue() + 1;
		} else {
			pv = 1l;
		}
		Long visitor;
		Element visitorCache = visitorTotalCache.get(site.getId());
		if (visitorCache != null) {
			if(newVisitor){
				visitor = (Long) visitorCache.getValue() + 1;
			}else{
				visitor = (Long) visitorCache.getValue();
			}
		}else{
			if(newVisitor){
				visitor=1l;
			}else{
				visitor = 0l;
			}
		}
		pvTotalCache.put(new Element(site.getId(), pv));
		visitorTotalCache.put(new Element(site.getId(), visitor));
		refreshToDB();
		return new Long[] { pv +pvTotal, visitor+visitorTotal};
	}
	
	private CmsSiteAccess visitAccess(HttpServletRequest request,String ip, String sessionId, String page, String referer,String browser,String operatingSystem){
		CmsSite site =CmsUtils.getSite(request);
		String accessSource=getSource(request, referer);
		CmsSiteAccess bean=new CmsSiteAccess();
		Date now=Calendar.getInstance().getTime();
		bean.setAccessDate(now);
		bean.setAccessSource(accessSource);
		if(accessSource.equals(getMessage(request,"cmsAccess.externallink"))){
			bean.setExternalLink(getRefererWebSite(referer));
		}
		if(enterFromEngine(request, referer)){
			bean.setEngine(getEngine(request, referer));
		}
		bean.setAccessTime(DateFormatUtils.parseTime(now));
		bean.setIp(ip);
		bean.setArea(IpSeekUtils.getIpProvinceByTaobao(ip));
		bean.setBrowser(browser);
		bean.setEntryPage(page);
		bean.setKeyword(getKeyword(referer));
		bean.setLastStopPage(page);
		bean.setOperatingSystem(operatingSystem);
		bean.setSessionId(sessionId);
		bean.setSite(site);
		bean.setVisitPageCount(1);
		bean.setVisitSecond(0);
		return bean;
	}
	
	private CmsSiteAccess updateAccess(CmsSiteAccess bean,String lastStopPage,int visitPageCount,Integer visitSecond){
		bean.setLastStopPage(lastStopPage);
		bean.setVisitPageCount(visitPageCount);
		bean.setVisitSecond(visitSecond);
		return bean;
	}
	
	private CmsSiteAccess findAccess(String sessionId){
		Element accessElement=accessCache.get(sessionId);
		if(accessElement!=null){
			return (CmsSiteAccess) accessElement.getObjectValue();
		}else{
			CmsSiteAccess access=cmsSiteAccessMng.findAccessBySessionId(sessionId);
			return access;
		}
	}
	

	
	private CmsSiteAccess findLastAccess(Integer siteId){
		Element accessElement=lastAccessCache.get(siteId);
		CmsSiteAccess lastAccess =null;
		if(accessElement!=null){
			lastAccess=(CmsSiteAccess) accessElement.getObjectValue();
		}
		return lastAccess;
		 
	}
	
	private CmsSiteAccessPages visitPages(CmsSite site,String page,String sessionId,Integer hasVisitCount,Date lastVisitTime) {
		CmsSiteAccessPages bean = new CmsSiteAccessPages();
		Date time = DateFormatUtils.parseTime(Calendar.getInstance().getTime());
		Date date = DateFormatUtils.parseDate(Calendar.getInstance().getTime());
		bean.setAccessPage(page);
		bean.setAccessTime(time);
		bean.setAccessDate(date);
		bean.setSite(site);
		bean.setSessionId(sessionId);
		//设置当前访问时间0，设置上次时间
		bean.setVisitSecond(0);
		bean.setPageIndex(hasVisitCount+1);
		//accessPageCache key为sessionid+访问页面顺序
		String prePageKey=sessionId+hasVisitCount;
		Element pageElement=accessPageCache.get(prePageKey);
		//修改上个页面的访问时间(更新缓存)
		CmsSiteAccessPages prePage=null;
		String prePageCacheKey;
		if(pageElement==null){
			prePage=cmsSiteAccessPagesMng.findAccessPage(sessionId, hasVisitCount);
			prePageCacheKey=sessionId+hasVisitCount;
		}else{
			prePage=(CmsSiteAccessPages) pageElement.getObjectValue();
			prePageCacheKey=(String) pageElement.getKey();
		}
		if(prePage!=null){
			prePage.setVisitSecond(DateUtils.getSecondBetweenDate(prePage.getAccessTime(), time));
			accessPageCache.put(new Element(prePageCacheKey,prePage));
		}
		return bean;
	}
	
	
	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			freshPvTotalCacheToDB(pvTotalCache);
			freshVisitorTotalCacheToDB(visitorTotalCache);
			int accessCount = freshAccessCacheToDB(accessCache);
			int pagesCount = freshAccessPagesCacheToDB(accessPageCache);
			// 清除缓存
			pvTotalCache.removeAll();
			visitorTotalCache.removeAll();
			accessCache.removeAll();
			accessPageCache.removeAll();
			log.info("refresh cache access to DB: {}", accessCount);
			log.info("refresh cache pages to DB: {}", pagesCount);
		}
	}
	
	private int freshAccessCacheToDB(Ehcache cache) {
		int count = 0;
		List<String> list = cache.getKeys();
		for (String key : list) {
			Element element = cache.get(key);
			if (element == null) {
				return count;
			}
			CmsSiteAccess access = (CmsSiteAccess) element.getValue();
			cmsSiteAccessMng.saveOrUpdate(access);
		}
		return count;
	}
	
	private int freshAccessPagesCacheToDB(Ehcache cache){
		int count = 0;
		List<String> list = cache.getKeys();
		for (String key : list) {
			Element element = cache.get(key);
			if (element == null) {
				return count;
			}
			CmsSiteAccessPages page = (CmsSiteAccessPages) element.getValue();
			if (page.getId() == null&& page.getSessionId() != null) {
				cmsSiteAccessPagesMng.save(page);
			}else{
				cmsSiteAccessPagesMng.update(page);
			}
		}
		return count;
	}
	
	private void freshPvTotalCacheToDB(Ehcache cache) {
		List<Integer> list = cache.getKeys();
		Map<String,String>attr=new HashMap<String, String>();
		CmsSite site=CmsThreadVariable.getSite();
		for (Integer key : list) {
			Element element = cache.get(key);
			Long pv = (Long) element.getValue()+site.getPvTotal();
			attr.put(CmsSite.PV_TOTAL, pv.toString());
			cmsSiteMng.updateAttr(key, attr);
		}
	}
	
	private void freshVisitorTotalCacheToDB(Ehcache cache) {
		List<Integer> list = cache.getKeys();
		Map<String,String>attr=new HashMap<String, String>();
		CmsSite site=CmsThreadVariable.getSite();
		for (Integer key : list) {
			Element element = cache.get(key);
			Long visitor = (Long) element.getValue()+site.getVisitorTotal();
			attr.put(CmsSite.VISITORS, visitor.toString());
			cmsSiteMng.updateAttr(key, attr);
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		int accessCount = freshAccessCacheToDB(accessCache);
		int pagesCount = freshAccessPagesCacheToDB(accessPageCache);
		freshPvTotalCacheToDB(pvTotalCache);
		freshVisitorTotalCacheToDB(visitorTotalCache);
		log.info("Bean destroy.refresh cache access to DB: {}", accessCount);
		log.info("Bean destroy.refresh cache pages to DB: {}", pagesCount);
	}

	
	private  String getRefererWebSite(String referer){
		if(StringUtils.isBlank(referer)){
			return "";
		}
		int start = 0, i = 0, count = 3;
		while (i < count && start != -1) {
			start = referer.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"referer website uri not like 'http://.../...' pattern: "
							+ referer);
		}
		return referer.substring(0, start);
	}
	
	private  String getSource(HttpServletRequest request,String referer){
		CmsSite site=CmsUtils.getSite(request);
		if(StringUtils.isBlank(referer)){
			return "";
		}
		if(enterFromEngine(request, referer)){
			return getMessage(request,"cmsAccess.engine");
		}else{
			String refererWebSite=getRefererWebSite(referer);
			String refererWebDomain=refererWebSite.substring(refererWebSite.indexOf('/')+2);
			if(refererWebDomain.indexOf(':')!=-1){
				refererWebDomain=refererWebDomain.substring(0, refererWebDomain.indexOf(':'));
			}
			//本站域名直接访问
			if(site.getDomain().equals(refererWebDomain)||site.getDomainAlias().contains(refererWebDomain)){
				return getMessage(request,"cmsAccess.directaccess");
			}else{
				return getMessage(request,"cmsAccess.externallink");
			}
		}
	}
	
	/**
	 * 只支持常用的搜索引擎
	 * @param request
	 * @param referer
	 * @return
	 */
	private boolean enterFromEngine(HttpServletRequest request,String referer){
		if(StringUtils.isBlank(referer)){
			return false;
		}
		if(referer.indexOf(ENGINE_BAIDU)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_GOOGLE)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_YAHOO)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_BING)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_SOGOU)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_SOSO)!=-1){
			return true;
		}else if(referer.indexOf(ENGINE_SO)!=-1){
			return true;
		}
		return false;
	}
	
	private  String getEngine(HttpServletRequest request,String referer){
		if(StringUtils.isBlank(referer)){
			return "";
		}
		if(referer.indexOf(ENGINE_BAIDU)!=-1){
			return getMessage(request,"cmsSearch.engine.baidu");
		}else if(referer.indexOf(ENGINE_GOOGLE)!=-1){
			return getMessage(request,"cmsSearch.engine.google");
		}else if(referer.indexOf(ENGINE_YAHOO)!=-1){
			return getMessage(request,"cmsSearch.engine.yahoo");
		}else if(referer.indexOf(ENGINE_BING)!=-1){
			return getMessage(request,"cmsSearch.engine.bing");
		}else if(referer.indexOf(ENGINE_SOGOU)!=-1){
			return getMessage(request,"cmsSearch.engine.sogou");
		}else if(referer.indexOf(ENGINE_SOSO)!=-1){
			return getMessage(request,"cmsSearch.engine.soso");
		}else if(referer.indexOf(ENGINE_SO)!=-1){
			return getMessage(request,"cmsSearch.engine.so");
		}
		return "";
	}
	
	
	private  String getMessage(HttpServletRequest request, String key,
			Object... args) {
		return MessageResolver.getMessage(request, key, args);
	}
	
	// 间隔时间
	private int interval = 1 * 60 * 1000; // 60秒
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();
	
	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位秒
	 */
	public void setInterval(int interval) {
		this.interval = interval * 1000;
	}
	
	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsSiteAccessMng cmsSiteAccessMng;
	@Autowired
	private CmsSiteAccessPagesMng cmsSiteAccessPagesMng;
	@Autowired
	private CmsSiteAccessCountMng cmsSiteAccessCountMng;

	@Autowired 
	@Qualifier("cmsAccessCache")
	private Ehcache accessCache;
	@Autowired 
	@Qualifier("cmsLastAccessCache")
	private Ehcache lastAccessCache;
	@Autowired 
	@Qualifier("cmsAccessPageCache")
	private Ehcache accessPageCache;
	@Autowired 
	@Qualifier("cmsPvTotalCache")
	private Ehcache pvTotalCache;
	@Autowired 
	@Qualifier("cmsVisitorTotalCache")
	private Ehcache visitorTotalCache;

}
