package com.jeecms.cms.dao.assist;

import java.util.List;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.common.hibernate3.Updater;

public interface CmsAcquisitionDao {
	public List<CmsAcquisition> getList(Integer siteId);

	public CmsAcquisition findById(Integer id);

	public CmsAcquisition save(CmsAcquisition bean);

	public CmsAcquisition updateByUpdater(Updater<CmsAcquisition> updater);

	public CmsAcquisition deleteById(Integer id);

	public int countByChannelId(Integer channelId);

	public CmsAcquisition getStarted(Integer siteId);

	public Integer getMaxQueue(Integer siteId);

	public List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum);

	public CmsAcquisition popAcquFromQueue(Integer siteId);
}