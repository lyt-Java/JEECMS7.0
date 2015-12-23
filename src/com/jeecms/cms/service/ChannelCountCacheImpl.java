package com.jeecms.cms.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jeecms.cms.entity.main.ChannelCount;
import com.jeecms.cms.manager.main.ChannelCountMng;
import com.jeecms.cms.manager.main.ChannelMng;

/**
 * 栏目计数器缓存实现
 */
@Service
public class ChannelCountCacheImpl implements ChannelCountCache, DisposableBean {
	private Logger log = LoggerFactory.getLogger(ChannelCountCacheImpl.class);

	/**
	 * @see ChannelCountCache#viewAndGet(Integer)
	 */
	public int[] viewAndGet(Integer id) {
		ChannelCount count=channelMng.findById(id).getChannelCount();
		if (count == null) {
			return null;
		}
		Element e = cache.get(count.getId());
		Integer view;
		if (e != null) {
			view = (Integer) e.getValue() + 1;
		} else {
			view = 1;
		}
		cache.put(new Element(id, view));
		refreshToDB();
		return new int[] { view + count.getViews() };
	}

	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = channelCountMng.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh cache views to DB: {}", count);
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		int count = channelCountMng.freshCacheToDB(cache);
		log.info("Bean destroy.refresh cache views to DB: {}", count);
	}

	// 间隔时间
	private int interval = 1 * 60 * 1000; // 10分钟
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();
	
	private ChannelCountMng channelCountMng;
	
	private ChannelMng channelMng;
	
	private Ehcache cache;

	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}
	
	@Autowired
	public void setChannelCountMng(ChannelCountMng channelCountMng) {
		this.channelCountMng = channelCountMng;
	}

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setCache(@Qualifier("channelCount")Ehcache cache) {
		this.cache = cache;
	}
	
	

}
