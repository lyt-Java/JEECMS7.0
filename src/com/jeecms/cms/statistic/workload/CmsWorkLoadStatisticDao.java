package com.jeecms.cms.statistic.workload;

import java.util.Date;

import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;

public interface CmsWorkLoadStatisticDao {
	public Long  statistic(Integer channelId,
			Integer reviewerId, Integer authorId, Date beginDate, Date endDate,CmsWorkLoadStatisticDateKind dateKind);
}
