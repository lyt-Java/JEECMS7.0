package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.Constants;
import com.jeecms.cms.entity.assist.CmsAdvertising;
import com.jeecms.cms.entity.assist.CmsAdvertisingSpace;
import com.jeecms.cms.entity.assist.CmsDirectiveTpl;
import com.jeecms.cms.entity.assist.CmsFriendlinkCtg;
import com.jeecms.cms.entity.assist.CmsGuestbookCtg;
import com.jeecms.cms.entity.assist.CmsVoteTopic;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.manager.assist.CmsAdvertisingMng;
import com.jeecms.cms.manager.assist.CmsAdvertisingSpaceMng;
import com.jeecms.cms.manager.assist.CmsDirectiveTplMng;
import com.jeecms.cms.manager.assist.CmsFriendlinkCtgMng;
import com.jeecms.cms.manager.assist.CmsGuestbookCtgMng;
import com.jeecms.cms.manager.assist.CmsVoteTopicMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.util.CmsUtils;

/**
 * @author Tom
 * JEECMS标签向导类
 */
@Controller
public class DirectiveAct {
	public static final String ENCODING = "UTF-8";
	private final static String CHANNEL="channel";
	private final static String COMMENT="comment";
	private final static String TOPIC="topic";
	private final static String VOTE="vote";
	private final static String GUESTBOOK="guestbook";
	private final static String ADVERTISE="advertise";
	private final static String LINK="link";
	private final static String TAG="tag";
	private final static String CONTENT="content";
	public static final String LIST_PREFIX = "l_";
	public static final String PAGE_PREFIX = "p_";
	public static final String SINGLE_PREFIX = "s_";
	public static final String CHANNEL_PREFIX = "c_";
	public static final String SYSTEM_TPL_PREFIX = "s_";
	public static final String CUSTOM_TPL_PREFIX = "c_";
	public static final String TPL_SUFFIX = ".txt";
	public static final String HasContent="hasContent";
	public static final String COUNT="count";
	public static final String TextLen="textLen";
	public static final String DescLen="descLen";
	public static final String TitleLen="titleLen";
	public static final String ContentLen="contentLen";
	public static final String CHECKED="checked";
	public static final String RECOMMEND="recommend";
	public static final String ORDERBY="orderBy";
	public static final String CHANNEL_ID="channelId";
	public static final String CHANNEL_PATH="channelPath";
	public static final String NULL="null";
	public static final Integer LIST_COUNT=100;
	
	
	public static final String ID="id";
	public static final String CTG_ID="ctgId";
	
	/**
	 * module模块名称
	 * @param module
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("directive:module")
	@RequestMapping("/directive/module.do")
	public String select(String module, HttpServletRequest request, ModelMap model) {
		if(StringUtils.isNotBlank(module)){
			Integer siteId=CmsUtils.getSiteId(request);
			if(module.equals(CHANNEL)||module.equals(TOPIC)){
				List<Channel> channelList;
				List<Channel> topList = channelMng.getTopList(siteId,false);
				channelList = Channel.getListForSelect(topList, null, true);
				model.addAttribute("channelList", channelList);
			}else if(module.equals(VOTE)){
				List<CmsVoteTopic>voteList=voteTopicMng.getList(null, siteId, LIST_COUNT);
				model.addAttribute("voteList", voteList);
			}else if(module.equals(GUESTBOOK)){
				List<CmsGuestbookCtg>ctgList=guestbookCtgMng.getList(siteId);
				model.addAttribute("ctgList", ctgList);
			}else if(module.equals(ADVERTISE)){
				List<CmsAdvertisingSpace>adspaceList=advertisingSpaceMng.getList(siteId);
				List<CmsAdvertising>adList=advertisingMng.getList(null, true);
				model.addAttribute("adList", adList);
				model.addAttribute("spList", adspaceList);
			}else if(module.equals(LINK)){
				List<CmsFriendlinkCtg>linkCtgList=friendlinkCtgMng.getList(siteId);
				model.addAttribute("list", linkCtgList);
			}else if(module.equals(CONTENT)){
				List<ContentType>contentTypes=contentTypeMng.getList(true);
				model.addAttribute("types", contentTypes);
			}
		}
		return "directive/"+module;
	}
	
	@RequiresPermissions("directive:v_list")
	@RequestMapping("/directive/v_list.do")
	public String list(Integer pageNo,HttpServletRequest request, ModelMap model) {
		Pagination pagination=manager.getPage(cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "directive/list";
	}
	
	@RequiresPermissions("directive:v_add")
	@RequestMapping("/directive/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		return "directive/add";
	}
	
	@RequiresPermissions("directive:o_save")
	@RequestMapping("/directive/o_save.do")
	public String save(String name,String description,String module,Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsDirectiveTpl bean=new CmsDirectiveTpl();
		bean.setCode(getDirectiveTpl(module, request));
		bean.setName(name);
		bean.setDescription(description);
		bean.setUser(CmsUtils.getUser(request));
		manager.save(bean);
		cmsLogMng.operating(request, "CmsDirectiveTpl.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}
	
	@RequiresPermissions("directive:v_getcode")
	@RequestMapping("/directive/v_getcode.do")
	public void getCode(String module,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		String code=getDirectiveTpl(module, request);
		json.put("code", code);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("directive:v_ajax_edit")
	@RequestMapping("/directive/v_ajax_edit.do")
	public void ajaxEdit(Integer id, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		CmsDirectiveTpl directive=manager.findById(id);
		if(directive!=null){
			object.put("id", directive.getId());
			object.put("name", directive.getName());
			object.put("description", directive.getDescription());
			object.put("code", directive.getCode());
		}
		ResponseUtils.renderJson(response, object.toString());
	}
	
	@RequiresPermissions("directive:o_update")
	@RequestMapping("/directive/o_update.do")
	public String update(CmsDirectiveTpl bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		bean = manager.update(bean);
		cmsLogMng.operating(request, "CmsDirectiveTpl.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}
	
	@RequiresPermissions("directive:o_delete")
	@RequestMapping("/directive/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsDirectiveTpl[] beans = manager.deleteByIds(ids);
		for (CmsDirectiveTpl bean : beans) {
			cmsLogMng.operating(request, "CmsDirectiveTpl.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}
	private String getDirectiveTpl(String module,HttpServletRequest request){
		Map<String,Object>params=RequestUtils.getQueryParams(request);
		String filename="";
		Map<String,String>value=new HashMap<String, String>();
		if(StringUtils.isNotBlank(module)){
			if(module.equals(CHANNEL)){
				filename=getChannelTpl(params);
				value=getChannelValue(params);
			}else if(module.equals(COMMENT)){
				filename=getCommonTpl(params, COMMENT);
				value=getCommentValue(params);
			}else if(module.equals(TOPIC)){
				filename=getCommonTpl(params, TOPIC);
				value=getTopicValue(params);
			}else if(module.equals(VOTE)){
				filename=getVoteTpl(params);
				value=getVoteValue(params);
			}else if(module.equals(GUESTBOOK)){
				filename=getCommonTpl(params, GUESTBOOK);
				value=getGuestbookValue(params);
			}else if(module.equals(ADVERTISE)){
				filename=getCommonTpl(params, ADVERTISE);
				value=getAdvertiseValue(params);
			}else if(module.equals(LINK)){
				filename=getLinkTpl(params);
				value=getLinkValue(params);
			}else if(module.equals(TAG)){
				filename=getCommonTpl(params,TAG);
				value=getTagValue(params);
			}else if(module.equals(CONTENT)){
				filename=getContentTpl(params);
				value=getContentValue(params);
			}
		}
		String directive=readTpl(new File(realPathResolver.get(filename)), value);
		return directive;
	}
	
	private String getChannelTpl(Map<String,Object>params){
		String filename="";
		String listType=(String) params.get("listType");
		String singleType=(String) params.get("singleType");
		Boolean list=getBooleanParam(params, "list");
		Boolean channel=getBooleanParam(params, "channel");
		filename+=Constants.DIRECTIVE_TPL_PATH+CHANNEL+"/";
		if(list){
			filename+=LIST_PREFIX;
			if(channel){
				filename+=CHANNEL_PREFIX;
			}else{
			}
			filename+=listType;
		}else{
			filename+=SINGLE_PREFIX;
			if(channel){
				filename+=CHANNEL_PREFIX;
			}else{
			}
			filename+=singleType;
		}
		filename+=TPL_SUFFIX;
		return filename;
	}
	
	private Map<String,String> getChannelValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String cid=(String)params.get("channelId");
		Boolean list=getBooleanParam(params, "list");
		Boolean channel=getBooleanParam(params, "channel");
		Boolean hasContent=getBooleanParam(params, "hasContent");
		Integer channelId=1;
		if(StringUtils.isNotBlank(cid)){
			channelId=Integer.parseInt(cid);
		}
		if(list){
			if(!channel){
				value.put(ID, channelId.toString());
			}
			value.put(HasContent, hasContent.toString());
		}else{
			if(!channel){
				value.put(ID, channelId.toString());
			}
		}
		return value;
	}
	
	private String getCommonTpl(Map<String,Object>params,String module){
		String filename="";
		Boolean page=getBooleanParam(params, "page");
		filename+=Constants.DIRECTIVE_TPL_PATH+module+"/";
		if(page){
			filename+=PAGE_PREFIX;
		}else{
			filename+=LIST_PREFIX;
		}
		filename+=TPL_SUFFIX;
		return filename;
	}
	
	private Map<String,String> getCommentValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String textLen=(String)params.get("textLen");
		String c=(String)params.get("count");
		Boolean recommend=getBooleanParam(params, "recommend");
		Boolean checked=getBooleanParam(params, "checked");
		Boolean orderBy=getBooleanParam(params, "orderBy");
		value.put(TextLen, textLen);
		value.put(COUNT, c);
		if(recommend==null){
			value.put(RECOMMEND, NULL);
		}else{
			value.put(RECOMMEND, recommend.toString());
		}
		if(checked==null){
			value.put(CHECKED, NULL);
		}else{
			value.put(CHECKED, checked.toString());
		}
		if(orderBy){
			value.put(ORDERBY, "1");
		}else{
			value.put(ORDERBY, "0");
		}
		return value;
	}
	
	private Map<String,String> getTopicValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String descLen=(String)params.get("descLen");
		String c=(String)params.get("count");
		Boolean recommend=getBooleanParam(params, "recommend");
		value.put(DescLen, descLen);
		value.put(COUNT, c);
		if(recommend==null){
			value.put(RECOMMEND, NULL);
		}else{
			value.put(RECOMMEND, recommend.toString());
		}
		return value;
	}
	
	private String getVoteTpl(Map<String,Object>params){
		String filename="";
		Boolean list=getBooleanParam(params, "list");
		filename+=Constants.DIRECTIVE_TPL_PATH+VOTE+"/";
		if(list){
			filename+=LIST_PREFIX;
		}else{
			filename+=SINGLE_PREFIX;
		}
		filename+=TPL_SUFFIX;
		return filename;
	}
	
	private Map<String,String> getVoteValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String c=(String)params.get("count");
		String v=(String)params.get("voteId");
		value.put(COUNT, c);
		value.put(ID, v);
		return value;
	}
	
	private Map<String,String> getGuestbookValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String c=(String)params.get("count");
		String ctgId=(String)params.get("ctgId");
		Boolean recommend=getBooleanParam(params, "recommend");
		Boolean checked=getBooleanParam(params, "checked");
		String titleLen=(String)params.get("titleLen");
		String contentLen=(String)params.get("contentLen");
		
		if(recommend==null){
			value.put(RECOMMEND, NULL);
		}else{
			value.put(RECOMMEND, recommend.toString());
		}
		if(checked==null){
			value.put(CHECKED, NULL);
		}else{
			value.put(CHECKED, checked.toString());
		}
		if(ctgId.equals("0")){
			value.put(CTG_ID, NULL);
		}else{
			value.put(CTG_ID, ctgId);
		}
		value.put(COUNT, c);
		
		value.put(TitleLen, titleLen);
		value.put(ContentLen, contentLen);
		return value;
	}
	
	private Map<String,String> getAdvertiseValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		Boolean page=getBooleanParam(params, "page");
		String aid=(String)params.get("aid");
		String sid=(String)params.get("sid");
		if(page!=null){
			if(page){
				value.put(ID, sid);
			}else{
				value.put(ID,aid);
			}
		}else{
			value.put(ID, sid);
		}
		return value;
	}
	
	private String getLinkTpl(Map<String,Object>params){
		return Constants.DIRECTIVE_TPL_PATH+LINK+"/"+LIST_PREFIX+TPL_SUFFIX;
	}
	
	private Map<String,String> getLinkValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String id=(String)params.get("id");
		value.put(ID, id);
		return value;
	}
	
	private Map<String,String> getTagValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String c=(String)params.get("count");
		value.put(COUNT, c);
		return value;
	}
	
	private String getContentTpl(Map<String,Object>params){
		String filename="";
		String type=(String) params.get("type");
		String singleType=(String) params.get("singleType");
		Boolean sysTpl=getBooleanParam(params, "sys");
		String tpl=(String) params.get("tpl");
		filename+=Constants.DIRECTIVE_TPL_PATH+CONTENT+"/";
		if(type.equals("single")){
			filename+=SINGLE_PREFIX+singleType;
		}else if(type.equals("page")){
			filename+=PAGE_PREFIX;
		}else if(type.equals("ids")){
			//ids或者list
			filename+=LIST_PREFIX+"i_";
		}else {
			filename+=LIST_PREFIX;
		}
		if(!(type.equals("single")||type.equals("ids"))){
			if(sysTpl){
				filename+=SYSTEM_TPL_PREFIX;
				filename+=tpl;
			}else{
				filename+=CUSTOM_TPL_PREFIX;
			}
		}
		filename+=TPL_SUFFIX;
		return filename;
	}
	
	
	private Map<String,String> getContentValue(Map<String,Object>params){
		Map<String,String>value=new HashMap<String, String>();
		String type=(String) params.get("type");
		Boolean sysTpl=getBooleanParam(params, "sys");
		String tpl=(String) params.get("tpl");
		if(type.equals("single")){
			String id=(String)params.get(ID);
			value.put(ID, id);
		}else if(type.equals("ids")){
			//ids
			String ids=(String)params.get("ids");
			String titleLen=(String)params.get("idsTitLen");
			String idsDateFormat=(String)params.get("idsDateFormat");
			value.put("ids", ids);
			value.put(TitleLen, titleLen);
			value.put("dateFormat", idsDateFormat);
		}else {
			String tagId=getStringParam(params, TAG);
			String topicID=getStringParam(params, TOPIC);
			String channelId=getStringParam(params, CHANNEL_ID);
			String channelPath=getStringParam(params, CHANNEL_PATH);
			String channelOption=getStringParam(params, "channelOption");
			String typeId=getStringsParam(params, "typeId");
			String recommend=getStringParam(params, "recommend");
			String image=getStringParam(params, "image");
			String shownew=getStringParam(params, "new");
			String title=getStringParam(params, "title");
			String orderBy=getStringParam(params, "orderBy");
			String titLen=getStringParam(params, "titLen");
			String showDesc=getStringParam(params, "showDesc");
			String descLen=getStringParam(params, "descLen");
			String target=getStringParam(params, "target");
			String dateFormat=getStringParam(params, "dateFormat");
			String count=getStringParam(params, COUNT);
			value.put("tagId", tagId);
			value.put("topicId", topicID);
			value.put("channelId", channelId);
			value.put("channelPath", channelPath);
			if(tagId!=null){
				value.put("topicId", NULL);
				value.put("channelId", NULL);
				value.put("channelPath", NULL);
			}
			if(topicID!=null){
				value.put("channelId", NULL);
				value.put("channelPath", NULL);
			}
			if(channelId!=null){
				value.put("channelPath", NULL);
			}
			value.put("channelOption", channelOption);
			value.put("typeId", typeId);
			value.put("title", title);
			value.put("orderBy", orderBy);
			value.put("titLen", titLen);
			value.put("descLen", descLen);
			value.put("dateFormat", dateFormat);
			value.put(RECOMMEND, recommend);
			value.put("image", image);
			value.put("target", target);
			value.put("count", count);
			value.put("new", shownew);
			value.put("showDesc", showDesc);
		}
		if(!(type.equals("single")||type.equals("ids"))){
			String styleList=(String) params.get("tpl"+tpl);
			String showTitleStyle=getStringParam(params,"showTitleStyle");
			String useShortTitle=getStringParam(params,"useShortTitle");
			if(sysTpl){
				value.put("styleList", styleList);
				value.put("showTitleStyle", showTitleStyle);
				value.put("useShortTitle", useShortTitle);
				if(tpl.equals("1")||tpl.equals("2")){
					//普通列表
					String lineHeight=getStringParam(params, "lineHeight");
					String headMarkImg=getStringParam(params, "headMarkImg");
					String headMark=getStringParam(params, "headMark");
					String bottomLine=getStringParam(params, "bottomLine");
					String datePosition=getStringParam(params, "datePosition");
					String ctgForm=getStringParam(params, "ctgForm");
					String picWidth=getStringParam(params, "picWidth");
					String picHeight=getStringParam(params, "picHeight");
					String rightPadding=getStringParam(params, "rightPadding");
					String picFloat=getStringParam(params, "picFloat");
					String view=getStringParam(params, "view");
					String viewTitle=getStringParam(params, "viewTitle");
					if(styleList.equals("1")||styleList.equals("3")){
						//文字 列表
						value.put("lineHeight", lineHeight);
						value.put("headMarkImg", headMarkImg);
						value.put("headMark", headMark);
						value.put("bottomLine", bottomLine);
						value.put("datePosition", datePosition);
						value.put("ctgForm", ctgForm);
						value.put("picWidth", NULL);
						value.put("picHeight", NULL);
						value.put("rightPadding", NULL);
						value.put("picFloat", NULL);
					}else if(styleList.equals("2")||styleList.equals("4")){
						//图文列表
						value.put("picWidth", picWidth);
						value.put("picHeight", picHeight);
						value.put("rightPadding", rightPadding);
						value.put("picFloat", picFloat);
						value.put("lineHeight", NULL);
						value.put("headMarkImg", NULL);
						value.put("headMark", NULL);
						value.put("bottomLine", NULL);
						value.put("datePosition", NULL);
						value.put("ctgForm", NULL);
					}
					if(styleList.equals("3")){
						//带点击率的文字列表
						value.put("view", view);
						value.put("viewTitle", viewTitle);
					}else{
						value.put("view", NULL);
						value.put("viewTitle", NULL);
					}
					//滚动列表
					if(tpl.equals("2")){
						String rollDisplayHeight=getStringParam(params, "rollDisplayHeight");
						String rollLineHeight=getStringParam(params, "rollLineHeight");
						String rollCols=getStringParam(params, "rollCols");
						String rollSpeed=getStringParam(params, "rollSpeed");
						String rollSleepTime=getStringParam(params, "rollSleepTime");
						String rollRows=getStringParam(params, "rollRows");
						String rollSpan=getStringParam(params, "rollSpan");
						String isSleep=getStringParam(params, "isSleep");
						value.put("rollDisplayHeight", rollDisplayHeight);
						value.put("rollLineHeight", rollLineHeight);
						value.put("rollCols", rollCols);
						value.put("rollSpeed", rollSpeed);
						value.put("rollSleepTime", rollSleepTime);
						value.put("rollRows", rollRows);
						value.put("rollSpan", rollSpan);
						value.put("isSleep", isSleep);
					}
				}else if(tpl.equals("3")){
					//flash焦点
					String flashWidth=getStringParam(params, "flashWidth");
					String flashHeight=getStringParam(params, "flashHeight");
					String textHeight=getStringParam(params, "textHeight");
					value.put("flashWidth", flashWidth);
					value.put("flashHeight", flashHeight);
					value.put("textHeight", textHeight);
				}
			}
		}
		return value;
	}
	
	private Boolean getBooleanParam(Map<String,Object>params,String name){
		Boolean booValue;
		String value=(String) params.get(name);
		if(StringUtils.isNotBlank(value)){
			if(value.equals("true")){
				booValue=true;
			}else if(value.equals("all")){
				booValue=null;
			}else {
				booValue=false;
			}
		}else{
			booValue=null;
		}
		return booValue;
	}
	
	private String getStringParam(Map<String,Object>params,String name){
		String value=(String) params.get(name);
		if(StringUtils.isBlank(value)||value.equals("all")){
			return null;
		}
		return value;
	}
	
	private String getStringsParam(Map<String,Object>params,String name){
		Object valueObject= params.get(name);
		if(valueObject!=null){
			if(valueObject instanceof String){
				return (String) valueObject;
			}else{
				String[] values=(String[]) valueObject;
				String value="";
				for(String v:values){
					value+=v+",";
				}
				return value;
			}
		}else{
			return null;
		}
	}
	
	private String readTpl(File tpl,Map<String,String>prop) {
		String content = null;
		try {
			content = FileUtils.readFileToString(tpl, ENCODING);
			Set<String> ps = prop.keySet();
			for (Object o : ps) {
				String key = (String) o;
				String value = prop.get(key);
				if(value==null||StringUtils.isNotBlank(value)&&value.equals(NULL)){
					content = content.replaceAll(key+"='\\#\\{" + key + "\\}'", "");
				}else{
					content = content.replaceAll("\\#\\{" + key + "\\}", value);
				}
				
			}
		} catch (IOException e) {
		}
		return content;

	}
	@Autowired
	private RealPathResolver realPathResolver;
	@Autowired
	private CmsDirectiveTplMng manager;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private CmsVoteTopicMng voteTopicMng;
	@Autowired
	private CmsAdvertisingSpaceMng advertisingSpaceMng;
	@Autowired
	private CmsAdvertisingMng advertisingMng;
	@Autowired
	private CmsFriendlinkCtgMng friendlinkCtgMng;
	@Autowired
	private CmsGuestbookCtgMng guestbookCtgMng;
}
