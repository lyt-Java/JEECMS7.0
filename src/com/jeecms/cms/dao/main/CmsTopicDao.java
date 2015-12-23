package com.jeecms.cms.dao.main;

import java.util.List;

import com.jeecms.cms.entity.main.CmsTopic;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;

public interface CmsTopicDao {
	public List<CmsTopic> getList(Integer channelId, boolean recommend,
			Integer count, boolean cacheable);

	public Pagination getPage(Integer channelId, boolean recommend, int pageNo,
			int pageSize, boolean cacheable);

	public List<CmsTopic> getGlobalTopicList();

	public List<CmsTopic> getListByChannelId(Integer channelId);

	public List<CmsTopic> getListByChannelIds(Integer[] channelIds);

	public CmsTopic findById(Integer id);

	public CmsTopic save(CmsTopic bean);

	public CmsTopic updateByUpdater(Updater<CmsTopic> updater);

	public CmsTopic deleteById(Integer id);

	public int deleteContentRef(Integer id);

	public int countByChannelId(Integer channelId);
}