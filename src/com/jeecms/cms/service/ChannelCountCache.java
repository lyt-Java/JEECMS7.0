package com.jeecms.cms.service;

/**
 * 栏目计数器缓存接口
 */
public interface ChannelCountCache {

	/**
	 * 浏览一次
	 * 
	 * @param id
	 *            栏目ID
	 * @return 返回浏览次数。
	 */
	public int[] viewAndGet(Integer id);
}
