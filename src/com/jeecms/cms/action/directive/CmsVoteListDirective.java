package com.jeecms.cms.action.directive;

import static com.jeecms.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.entity.assist.CmsVoteTopic;
import com.jeecms.cms.manager.assist.CmsVoteTopicMng;
import com.jeecms.common.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 网上调查列表标签
 * 
 * @author tom
 * 
 */
public class CmsVoteListDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数,调查数量。可以为空，为空则获取站点的所有调查。
	 */
	public static final String COUNT = "count";
	/**
	 * 输入参数，是否默认，可以为空，为空则包含默认调查
	 */
	public static final String DEF = "def";
	/**
	 * 输入参数，站点ID。默认为当前站点。
	 */
	public static final String PARAM_SITE_ID = "siteId";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		if (count == null) {
			count=Integer.MAX_VALUE;
		} 
		Boolean def=getDef(params);
		List<CmsVoteTopic>voteTopicList=cmsVoteTopicMng.getList(def,getSiteId(params), count);
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(voteTopicList));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getCount(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(COUNT, params);
	}
	
	private Boolean getDef(Map<String, TemplateModel> params)
	throws TemplateException {
		String def = DirectiveUtils.getString(DEF, params);
		if ("1".equals(def)) {
			return true;
		} else if ("2".equals(def)) {
			return false;
		} else {
			return null;
		}
	}

	private Integer getSiteId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}
	

	@Autowired
	private CmsVoteTopicMng cmsVoteTopicMng;
}
