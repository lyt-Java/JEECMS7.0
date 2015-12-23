package com.jeecms.cms.service;


import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * 微信Token缓存接口
 */
@Service
public class WeixinTokenCacheImpl implements WeixinTokenCache {

	public String getToken() {
		String token="";
		Element e = cache.get("token");
		if (e != null) {
			token = (String) e.getValue();
			String tokenFresh=refreshCache();
			if(StringUtils.isNotBlank(tokenFresh)){
				token=tokenFresh;
			}
		} else {
			token = weiXinSvc.getToken();
			cache.put(new Element("token",token));
		}
		return token;
	}
	

	private String refreshCache() {
		long time = System.currentTimeMillis();
		String token="";
		if (time > refreshTime + interval) {
			refreshTime = time;
			// 更新缓存
			token= weiXinSvc.getToken();
			cache.put(new Element("token",token));
		}
		return token;
	}

	

	// 间隔时间
	private int interval = 60 * 60 * 1000; // 1小时
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();

	@Autowired
	private WeiXinSvc weiXinSvc;

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
	public void setCache(@Qualifier("tokenCache") Ehcache cache) {
		this.cache = cache;
	}

	

}
