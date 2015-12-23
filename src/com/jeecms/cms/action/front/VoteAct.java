package com.jeecms.cms.action.front;

import static com.jeecms.cms.Constants.TPLDIR_SPECIAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.entity.assist.CmsVoteItem;
import com.jeecms.cms.entity.assist.CmsVoteSubTopic;
import com.jeecms.cms.entity.assist.CmsVoteTopic;
import com.jeecms.cms.manager.assist.CmsVoteRecordMng;
import com.jeecms.cms.manager.assist.CmsVoteReplyMng;
import com.jeecms.cms.manager.assist.CmsVoteSubTopicMng;
import com.jeecms.cms.manager.assist.CmsVoteTopicMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;

import static com.jeecms.common.page.SimplePage.cpn;

@Controller
public class VoteAct {
	private static final Logger log = LoggerFactory.getLogger(VoteAct.class);
	/**
	 * 投票cookie前缀
	 */
	public static final String VOTE_COOKIE_PREFIX = "_vote_cookie_";

	public static final String VOTE_INPUT = "tpl.voteInput";
	public static final String VOTE_RESULT = "tpl.voteResult";
	public static final String VOTE_REPLY_RESULT = "tpl.voteReplyResult";

	@RequestMapping(value = "/vote_result.jspx", method = RequestMethod.GET)
	public String result(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote=null;
		if(voteId!=null){
			vote = cmsVoteTopicMng.findById(voteId);
		}
		if(vote!=null){
			model.addAttribute("vote", vote);
			FrontUtils.frontData(request, model, site);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_RESULT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotefind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}
	
	@RequestMapping(value = "/vote_reply_view.jspx", method = RequestMethod.GET)
	public String reply_view(Integer subId,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteSubTopic subTopic=null;
		if(subId!=null){
			subTopic=cmsVoteSubTopicMng.findById(subId);
		}
		if(subTopic!=null){
			Pagination pagination=cmsVoteReplyMng.getPage(subId, cpn(pageNo), CookieUtils.getPageSize(request));
			model.addAttribute("subTopic", subTopic);
			model.addAttribute("pagination", pagination);
			FrontUtils.frontData(request, model, site);
			FrontUtils.frontPageData(request, model);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_REPLY_RESULT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotesubfind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.GET)
	public String input(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote =null;
		if(voteId!=null){
			vote= cmsVoteTopicMng.findById(voteId);
		}
		if(vote!=null){
			model.addAttribute("vote", vote);
			FrontUtils.frontData(request, model, site);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, VOTE_INPUT);
		}else{
			WebErrors errors=WebErrors.create(request);
			errors.addError("error.vote.novotefind");
			return FrontUtils.showError(request, response, model, errors);
		}
	}

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.POST)
	public String submit(Integer voteId,Integer[]subIds,String[]reply, 
			HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String ip = RequestUtils.getIpAddr(request);
		String cookieName = VOTE_COOKIE_PREFIX + voteId;
		Cookie cookie = CookieUtils.getCookie(request, cookieName);
		String cookieValue;
		if (cookie != null && !StringUtils.isBlank(cookie.getValue())) {
			cookieValue = cookie.getValue();
		} else {
			cookieValue = null;
		}
		List<Integer[]>itemIds=getItemIdsParam(request, subIds);
		Integer[]subTxtIds = null;
		if(reply!=null&&reply.length>0){
			subTxtIds=new Integer[reply.length];
			List<Integer>subTxtIdList=new ArrayList<Integer>();
       		for(int i=0;i<itemIds.size();i++){
       			if(itemIds.get(i)==null){
       				subTxtIdList.add(subIds[i]);
       			}
       		}
			//投票文本选项和题目id数组对应相同大小
       		subTxtIds=(Integer[]) subTxtIdList.toArray(subTxtIds);
		}
		if (!validateSubmit(voteId,subIds,itemIds, user, ip, cookieValue, model)) {
			if (cookieValue == null) {
				// 随机cookie
				cookieValue = StringUtils.remove(UUID.randomUUID().toString(),
						"-");
				// 写cookie
				CookieUtils.addCookie(request, response, cookieName,
						cookieValue, Integer.MAX_VALUE, null);
			}
			CmsVoteTopic vote = cmsVoteTopicMng.vote(voteId,subTxtIds,itemIds,reply, user, ip,cookieValue);
			model.addAttribute("status", 0);
			model.addAttribute("vote", vote);

			log.info("vote CmsVote id={}, name={}", vote.getId(), vote
					.getTitle());
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, VOTE_RESULT);
	}
	
	private List<Integer[]> getItemIdsParam(HttpServletRequest request,Integer[] subIds){
		List<Integer[]>itemIds=new ArrayList<Integer[]>();
		for(Integer subId:subIds){
			itemIds.add(getSubItemIdsParam(request, subId));
		}
		return itemIds;
	}
	
	private Integer[] getSubItemIdsParam(HttpServletRequest request,Integer subId){
		String[] paramValues=request.getParameterValues("itemIds"+subId);
		return com.jeecms.common.util.ArrayUtils.convertStrArrayToInt(paramValues);
	}
	

	private boolean validateSubmit(Integer topicId,Integer[]subIds, List<Integer[]>itemIds,
			CmsUser user, String ip, String cookie, ModelMap model) {
		// 投票ID不能为空
		if (topicId == null) {
			model.addAttribute("status", 1);
			return true;
		}
		// 投票项不能为空
		if (itemIds == null || itemIds.size() <= 0) {
			model.addAttribute("status", 2);
			return true;
		}
		// 非文本选项 投票项不能为空
		for(int i=0;i<subIds.length;i++){
			if(!cmsVoteSubTopicMng.findById(subIds[i]).getIsText()){
				if(itemIds.get(i)==null){
					model.addAttribute("status", 2);
					return true;
				}
			}
		}
		CmsVoteTopic topic = cmsVoteTopicMng.findById(topicId);
		// 投票主题不存在
		if (topic == null) {
			model.addAttribute("status", 100);
			return true;
		}
		// 投票项不合法
		List<Integer>itemTotalIds=new ArrayList<Integer>();
		for(Integer[]ids:itemIds){
			if(ids!=null&&ids.length>0){
				for(Integer id:ids){
					itemTotalIds.add(id);
				}
			}
		}
		boolean contains;
		for (Integer itemId : itemTotalIds) {
			contains = false;
			for (CmsVoteItem item : topic.getItems()) {
				if (item.getId().equals(itemId)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				model.addAttribute("status", 101);
				return true;
			}
		}

		// 需要登录才能投票
		if (topic.getRestrictMember() && user == null) {
			model.addAttribute("status", 501);
			return true;
		}

		// 投票主题已经关闭
		if (topic.getDisabled()) {
			model.addAttribute("status", 200);
			return true;
		}
		/*多题目取消下面限制
		// 投票的选项个数大于允许的个数
		if (itemIds.length > topic.getMultiSelect()) {
			model.addAttribute("status", 201);
			return true;
		}
		*/
		long now = System.currentTimeMillis();
		// 投票还没有开始
		Date start = topic.getStartTime();
		if (start != null && now < start.getTime()) {
			model.addAttribute("status", 202);
			model.addAttribute("startTime", start);
			return true;
		}
		// 投票已经结束
		Date end = topic.getEndTime();
		if (end != null && now > end.getTime()) {
			model.addAttribute("status", 203);
			model.addAttribute("endTime", end);
			return true;
		}
		Integer hour = topic.getRepeateHour();
		if (hour == null || hour > 0) {
			Date vtime;
			// 规定时间内，同一会员不能重复投票
			if (topic.getRestrictMember()) {
				vtime = cmsVoteRecordMng.lastVoteTimeByUserId(user.getId(),
						topicId);
				if ((hour == null&&vtime!=null)||(hour != null&&vtime.getTime() + hour * 60 * 60 * 1000 > now)){
					model.addAttribute("status", 204);
					return true;
				}
			}
			// 规定时间内，同一IP不能重复投票
			if (topic.getRestrictIp()) {
				vtime = cmsVoteRecordMng.lastVoteTimeByIp(ip, topicId);
				if ((hour == null&&vtime!=null)||(hour != null&&vtime.getTime() + hour * 60 * 60 * 1000 > now)){
					model.addAttribute("status", 205);
					return true;
				}
			}
			// 规定时间内，同一COOKIE不能重复投票
			if (topic.getRestrictCookie() && cookie != null) {
				vtime = cmsVoteRecordMng.lastVoteTimeByCookie(cookie, topicId);
				if ((hour == null&&vtime!=null)||(hour != null&&vtime.getTime() + hour * 60 * 60 * 1000 > now)){
					model.addAttribute("status", 206);
					return true;
				}
			}
		}
		return false;
	}

	@Autowired
	private CmsVoteTopicMng cmsVoteTopicMng;
	@Autowired
	private CmsVoteSubTopicMng cmsVoteSubTopicMng;
	@Autowired
	private CmsVoteReplyMng cmsVoteReplyMng;
	@Autowired
	private CmsVoteRecordMng cmsVoteRecordMng;
}
