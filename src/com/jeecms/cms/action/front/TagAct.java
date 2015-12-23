package com.jeecms.cms.action.front;

import static com.jeecms.cms.Constants.TPLDIR_SPECIAL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.entity.main.ContentTag;
import com.jeecms.cms.manager.main.ContentTagMng;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;

@Controller
public class TagAct {

	public static final String TAGS_INDEX = "tpl.tagIndex";
	public static final String TAGS_DETAIL = "tpl.tagDetail";

	@RequestMapping(value = "/tag*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, TAGS_INDEX);
	}

	@RequestMapping(value = "/tag/{path}.jspx", method = RequestMethod.GET)
	public String tags(@PathVariable String path, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (StringUtils.isBlank(path)) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		int index = path.indexOf("_");
		int pageNo, id;
		try {
			if (index != -1) {
				id = Integer.valueOf(path.substring(0, index));
				pageNo = Integer.valueOf(path.substring(index + 1, path
						.length()));
			} else {
				id = Integer.valueOf(path);
				pageNo = 1;
			}
		} catch (NumberFormatException e) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		ContentTag tag = contentTagMng.findById(id);
		if (tag == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		model.addAttribute("tag", tag);
		model.addAttribute(FrontUtils.PAGE_NO, pageNo);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, TAGS_DETAIL);
	}

	@Autowired
	private ContentTagMng contentTagMng;
}
