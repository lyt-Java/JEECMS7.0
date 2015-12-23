package com.jeecms.cms.statistic.workload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticGroup;
import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsUserMng;

@Service
@Transactional(readOnly = true)
public class CmsWorkLoadStatisticSvcImpl implements CmsWorkLoadStatisticSvc {
	public List<CmsWorkLoadStatistic> statistic(Integer channelId,
			Integer reviewerId, Integer authorId, Date beginDate, Date endDate,
			CmsWorkLoadStatisticGroup group, CmsWorkLoadStatisticDateKind kind) {
		Long count;
		CmsWorkLoadStatistic bean;
		Channel channel = null;
		CmsUser author = null;
		CmsUser reviewer = null;
		Date begin = beginDate;
		Date end = null;
		int add = 1;
		if (channelId != null) {
			channel = channelMng.findById(channelId);
		}
		if (reviewerId != null) {
			reviewer = userMng.findById(reviewerId);
		}
		if (authorId != null) {
			author = userMng.findById(authorId);
		}
		begin = getNextDate(group, beginDate, 0);
		end = getNextDate(group, beginDate, add);
		List<CmsWorkLoadStatistic> list = new ArrayList<CmsWorkLoadStatistic>();
		while (end.before(endDate)) {
			count = dao.statistic(channelId, reviewerId, authorId, begin, end,
					kind);
			bean = new CmsWorkLoadStatistic(channel, author, reviewer, begin,
					count);
			list.add(bean);
			begin = end;
			end = getNextDate(group, beginDate, ++add);
		}
		count = dao.statistic(channelId, reviewerId, authorId, begin, end, kind);
		bean = new CmsWorkLoadStatistic(channel, author, reviewer, begin, count);
		list.add(bean);
		return list;
	}

	private Date getNextDate(CmsWorkLoadStatisticGroup group, Date date,
			int amount) {
		Date result = null;
		if (group == CmsWorkLoadStatisticGroup.year) {
			result = DateUtils.getSpecficYearStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.month) {
			result = DateUtils.getSpecficMonthStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.week) {
			result = DateUtils.getSpecficWeekStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.day) {
			result = DateUtils.getSpecficDateStart(date, amount);
		}
		return result;
	}

	@Autowired
	private CmsWorkLoadStatisticDao dao;
	@Autowired
	private CmsUserMng userMng;
	@Autowired
	private ChannelMng channelMng;
}
