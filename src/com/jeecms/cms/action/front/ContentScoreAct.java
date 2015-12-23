package com.jeecms.cms.action.front;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.manager.assist.CmsScoreRecordMng;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class ContentScoreAct {
	
	@RequestMapping(value = "/content_score_items.jspx", method = RequestMethod.GET)
	public void contentView(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		JSONObject json=new JSONObject();
		if (contentId == null) {
			json.put("result", "[]");
			return;
		}
		Map<String,String>itemCountMap = scoreRecordMng.viewContent(contentId);
		if (itemCountMap != null) {
			json.put("result", itemCountMap);
		} else {
			json.put("result", "[]");
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequestMapping(value = "/content_score.jspx", method = RequestMethod.GET)
	public void contentScore(Integer contentId, Integer itemId,HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		JSONObject json=new JSONObject();
		if (contentId == null) {
			json.put("succ", false);
		} else {
			Integer count=scoreRecordMng.contentScore(contentId,itemId);
			json.put("succ", true);
			json.put("count", count);
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@Autowired
	private CmsScoreRecordMng scoreRecordMng;
}
