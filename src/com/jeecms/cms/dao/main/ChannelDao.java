package com.jeecms.cms.dao.main;

import java.util.List;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;

public interface ChannelDao {
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly,boolean displayOnly, boolean cacheable);

	public Pagination getTopPage(Integer siteId, boolean hasContentOnly,boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId,boolean hasContentOnly);
	
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly,boolean displayOnly, boolean cacheable);
	
	public List<Channel> getBottomList(Integer siteId,boolean hasContentOnly);

	public Pagination getChildPage(Integer parentId, boolean hasContentOnly,boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

	public List<Channel> getChildListByRight(Integer userId, Integer parentId,boolean hasContentOnly);

	public List<Channel> getTopListForDepartId(Integer departId,Integer siteId,boolean hasContentOnly);
	
	public List<Channel> getChildListByDepartId(Integer departId,Integer  siteId,Integer parentId, boolean hasContentOnly);

	public Channel findByPath(String path, Integer siteId, boolean cacheable);

	public Channel findById(Integer id);

	public Channel save(Channel bean);

	public Channel updateByUpdater(Updater<Channel> updater);

	public Channel deleteById(Integer id);
	
	public void initWorkFlow(Integer workflowId);
}