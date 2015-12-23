package com.jeecms.cms.statistic.workload;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticGroup;
import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.web.util.CmsUtils;

/**
 * @author Tom
 */
@Controller
public class CmsWorkLoadStatisticAct {

	@RequiresPermissions("workloadstatistic:v_list")
	@RequestMapping("/workloadstatistic/v_list.do")
	public String contentList(HttpServletRequest request, ModelMap model,
			Integer channelId, Integer reviewerId, Integer authorId,
			Date beginDate, Date endDate, String group) {
		if (StringUtils.isBlank(group)) {
			group = "year";
		}
		if(reviewerId != null && reviewerId.equals(0)){
			reviewerId=null;
		}
		CmsWorkLoadStatisticGroup statisticGroup=initialGrop(group);
		CmsWorkLoadStatisticDateKind kind;
		if (reviewerId != null && !reviewerId.equals(0)) {
			kind = initialDateKind(true);
		} else {
			kind = initialDateKind(false);
		}
		if (authorId != null && authorId.equals(0)) {
			authorId = null;
		}
		Date now = Calendar.getInstance().getTime();
		if (beginDate == null) {
			beginDate = getNextDate(statisticGroup, now, 0);
		}
		if (endDate == null) {
			endDate = now;
		}
		Integer siteId = CmsUtils.getSiteId(request);
		List<Channel> topList = channelMng.getTopList(siteId, true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		List<CmsUser> admins = userMng.getAdminList(siteId, null, false, null);
		List<CmsUser> users = userMng.getList(null, null, null, null, false,null, null);
		List<CmsWorkLoadStatistic> li = workloadStatisticSvc.statistic(channelId, reviewerId,
				authorId, beginDate, endDate, statisticGroup, kind);
		model.addAttribute("channelId", channelId);
		model.addAttribute("reviewerId", reviewerId);
		model.addAttribute("authorId", authorId);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("group", group);
		model.addAttribute("channelList", channelList);
		model.addAttribute("admins", admins);
		model.addAttribute("users", users);
		model.addAttribute("list", li);
		return "statistic/workload/list";
	}
	
	private CmsWorkLoadStatisticGroup initialGrop(String group){
		CmsWorkLoadStatisticGroup statisticGroup = CmsWorkLoadStatisticGroup
				.valueOf(group);
		return statisticGroup;
	}
	private CmsWorkLoadStatisticDateKind initialDateKind(Boolean checkDate){
		CmsWorkLoadStatisticDateKind kind = CmsWorkLoadStatisticDateKind.valueOf("release");
		if(checkDate){
			kind = CmsWorkLoadStatisticDateKind.valueOf("check");
		}
		return kind;
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
	private CmsWorkLoadStatisticSvc workloadStatisticSvc;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsUserMng userMng;
}
