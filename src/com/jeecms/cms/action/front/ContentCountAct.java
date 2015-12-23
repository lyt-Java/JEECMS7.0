package com.jeecms.cms.action.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ContentCount;
import com.jeecms.cms.entity.main.ContentCount.ContentViewCount;
import com.jeecms.cms.manager.main.ContentCountMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.service.ChannelCountCache;
import com.jeecms.cms.service.ContentCountCache;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class ContentCountAct {
	@RequestMapping(value = "/content_view.jspx", method = RequestMethod.GET)
	public void contentView(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "[]");
			return;
		}
		int[] counts = contentCountCache.viewAndGet(contentId);
		//栏目访问量计数
		Channel channel=contentMng.findById(contentId).getChannel();
		channelCountCache.viewAndGet(channel.getId());
		String json;
		if (counts != null) {
			json = new JSONArray(counts).toString();
			ResponseUtils.renderJson(response, json);
		} else {
			ResponseUtils.renderJson(response, "[]");
		}
	}
	
	@RequestMapping(value = "/content_view_get.jspx")
	public void getContentView(Integer contentIds[], String view, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		if (contentIds == null) {
			ResponseUtils.renderJson(response, "[]");
			return;
		}
		ContentViewCount viewCountType;
		JSONObject json=new JSONObject();
		Map<Integer, Integer>contentViewsMap=new HashMap<Integer, Integer>();
		if (!StringUtils.isBlank(view)) {
			viewCountType = ContentViewCount.valueOf(view);
		} else {
			viewCountType = ContentViewCount.viewTotal;
		}
		for(Integer contentId:contentIds){
			Integer counts=getViewCount(contentId, viewCountType);
			if (counts != null) {
				contentViewsMap.put(contentId, counts);
			} else{
				contentViewsMap.put(contentId, 0);
			}
			json.put("contentViewsMap", contentViewsMap);
		}
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping(value = "/content_up.jspx", method = RequestMethod.GET)
	public void contentUp(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "false");
		} else {
			contentCountMng.contentUp(contentId);
			ResponseUtils.renderJson(response, "true");
		}
	}

	@RequestMapping(value = "/content_down.jspx", method = RequestMethod.GET)
	public void contentDown(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		if (contentId == null) {
			ResponseUtils.renderJson(response, "false");
		} else {
			contentCountMng.contentDown(contentId);
			ResponseUtils.renderJson(response, "true");
		}
	}
	
	private Integer getViewCount(Integer contentId,ContentViewCount viewCountType){
		Integer counts=0;
		ContentCount contentCount=contentCountMng.findById(contentId);
		if(viewCountType.equals(ContentViewCount.viewTotal)){
			counts= contentCount.getViews();
		}else if(viewCountType.equals(ContentViewCount.viewMonth)){
			counts=contentCount.getViewsMonth();
		}else if(viewCountType.equals(ContentViewCount.viewWeek)){
			counts=contentCount.getViewsWeek();
		}else if(viewCountType.equals(ContentViewCount.viewDay)){
			counts=contentCount.getViewsDay();
		}else{
			
		}
		return counts;
	}

	@Autowired
	private ContentCountCache contentCountCache;
	@Autowired
	private ChannelCountCache channelCountCache;
	@Autowired
	private ContentCountMng contentCountMng;
	@Autowired
	private ContentMng contentMng;
}
