package com.jeecms.cms.action.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeecms.cms.manager.assist.CmsFriendlinkMng;
import com.jeecms.common.web.ResponseUtils;

/**
 * 友情链接点击次数Action
 */
@Controller
public class FriendlinkAct {
	// private static final Logger log = LoggerFactory
	// .getLogger(FriendlinkAct.class);

	@RequestMapping(value = "/friendlink_view.jspx", method = RequestMethod.GET)
	public void view(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsFriendlinkMng.updateViews(id);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	@Autowired
	private CmsFriendlinkMng cmsFriendlinkMng;
}
