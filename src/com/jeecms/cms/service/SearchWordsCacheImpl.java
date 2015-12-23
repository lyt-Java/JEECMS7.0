package com.jeecms.cms.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jeecms.cms.manager.assist.CmsSearchWordsMng;

@Service
public class SearchWordsCacheImpl implements SearchWordsCache, DisposableBean {
	private Logger log = LoggerFactory.getLogger(SearchWordsCacheImpl.class);

	public void cacheWord(String name) {
		Element e = cache.get(name);
		//搜索次数
		Integer hits;
		if (e != null) {
			hits = (Integer) e.getValue() + 1;
		} else {
			hits = 1;
		}
		cache.put(new Element(name, hits));
		refreshToDB();
	}
	
	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = manager.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh cache hits to DB: {}", count);
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		int count = manager.freshCacheToDB(cache);
		log.info("Bean destroy.refresh cache flows to DB: {}", count);
	}
	
	// 间隔时间
	private int interval = 1 * 30 * 1000; // 30秒
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
	private CmsSearchWordsMng manager;

	@Autowired 
	@Qualifier("cmsSearchWords")
	private Ehcache cache;

}
