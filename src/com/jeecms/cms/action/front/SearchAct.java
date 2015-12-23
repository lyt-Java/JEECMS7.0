package com.jeecms.cms.action.front;

import static com.jeecms.cms.Constants.TPLDIR_SPECIAL;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.entity.assist.CmsSearchWords;
import com.jeecms.cms.manager.assist.CmsSearchWordsMng;
import com.jeecms.cms.service.SearchWordsCache;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;

@Controller
public class SearchAct {
	public static final String SEARCH_INPUT = "tpl.searchInput";
	public static final String SEARCH_RESULT = "tpl.searchResult";
	public static final String SEARCH_ERROR = "tpl.searchError";
	public static final String SEARCH_JOB = "tpl.searchJob";
	
	@RequestMapping(value = "/search*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		String q = RequestUtils.getQueryParam(request, "q");
		String channelId = RequestUtils.getQueryParam(request, "channelId");
		if (StringUtils.isBlank(q) && StringUtils.isBlank(channelId)) {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, SEARCH_INPUT);
		} else {
			String parseQ=parseKeywords(q);
			model.addAttribute("input",q);
			model.addAttribute("q",parseQ);
			searchWordsCache.cacheWord(q);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, SEARCH_RESULT);
		}
	}
	
	@RequestMapping(value = "/searchJob*.jspx", method = RequestMethod.GET)
	public String searchJob(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String q = RequestUtils.getQueryParam(request, "q");
		String category = RequestUtils.getQueryParam(request, "category");
		String workplace = RequestUtils.getQueryParam(request, "workplace");
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		if (StringUtils.isBlank(q)) {
			model.remove("q");
		}else{
			//处理lucene查询字符串中的关键字
			String parseQ=parseKeywords(q);
			model.addAttribute("q",parseQ);
		}
		model.addAttribute("input",q);
		model.addAttribute("queryCategory",category);
		model.addAttribute("queryWorkplace",workplace);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_JOB);
	}
	
	@RequestMapping("/search/v_ajax_list.jspx")
	public void ajaxList(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		Map<String,String>wordsMap=new LinkedHashMap<String, String>();
		String word=RequestUtils.getQueryParam(request, "term");
		if(StringUtils.isNotBlank(word)){
			List<CmsSearchWords>words=manager.getList(word,CmsSearchWords.HIT_DESC,true);
			for(CmsSearchWords w:words){
				wordsMap.put(w.getName(), w.getName());
			}
		}
		object.put("words", wordsMap);
		ResponseUtils.renderJson(response, object.get("words").toString());
	}
	
	public static String parseKeywords(String q){
		char c='\\';
		int cIndex=q.indexOf(c);
		if(cIndex!=-1&&cIndex==0){
			q=q.substring(1);
		}
		if(cIndex!=-1&&cIndex==q.length()-1){
			q=q.substring(0,q.length()-1);
		}
		try {
			String regular = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
			Pattern p = Pattern.compile(regular);
			Matcher m = p.matcher(q);
			String src = null;
			while (m.find()) {
				src = m.group();
				q = q.replaceAll("\\" + src, ("\\\\" + src));
			}
			q = q.replaceAll("AND", "and").replaceAll("OR", "or").replace("NOT", "not").replace("[", "［").replace("]", "］");
		} catch (Exception e) {
			e.printStackTrace();
			q=q;
		}
		return  q;
	}

	@Autowired
	private CmsSearchWordsMng manager;
	@Autowired
	private SearchWordsCache searchWordsCache;
}
