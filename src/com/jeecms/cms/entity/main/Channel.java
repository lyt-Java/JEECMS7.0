package com.jeecms.cms.entity.main;

import static com.jeecms.common.web.Constants.INDEX;
import static com.jeecms.common.web.Constants.SPT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.main.base.BaseChannel;
import com.jeecms.cms.staticpage.StaticPageUtils;
import com.jeecms.common.hibernate3.HibernateTree;
import com.jeecms.common.hibernate3.PriorityComparator;
import com.jeecms.common.hibernate3.PriorityInterface;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;

/**
 * 栏目实体类
 */
public class Channel extends BaseChannel implements HibernateTree<Integer>,
		PriorityInterface {
	private static final long serialVersionUID = 1L;

	/**
	 * 审核后内容修改方式
	 */
	public static enum AfterCheckEnum {
		/**
		 * 不能修改，不能删除。
		 */
		CANNOT_UPDATE,
		/**
		 * 可以修改，可以删除。 修改后文章的审核级别将退回到修改人级别的状态。如果修改人的级别高于当前文章的审核级别，那么文章审核级别将保持不变。
		 */
		BACK_UPDATE,
		/**
		 * 可以修改，可以删除。 修改后文章保持原状态。
		 */
		KEEP_UPDATE
	}

	/**
	 * 获得URL地址
	 * 
	 * @return
	 */
	public String getUrl() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(false, 1);
		} else {
//			return getUrlDynamic(null);
			//此处共享了别站信息需要绝句路径，做了更改 于2012-7-26修改
			return getUrlDynamic(true);
		}
	}

	public String getUrlWhole() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(true, 1);
		} else {
			return getUrlDynamic(true);
		}
	}

	/**
	 * 获得静态URL地址
	 * 
	 * @return
	 */
	public String getUrlStatic() {
		return getUrlStatic(null, 1);
	}

	public String getUrlStatic(int pageNo) {
		return getUrlStatic(null, pageNo);
	}

	public String getUrlStatic(Boolean whole, int pageNo) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		CmsSite site = getSite();
		StringBuilder url = site.getUrlBuffer(false, whole, false);
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			if (pageNo > 1) {
				int index = filename.indexOf(".", filename.lastIndexOf("/"));
				if (index != -1) {
					url.append(filename.substring(0, index));
					url.append("_").append(pageNo);
					url.append(filename.substring(index));
				} else {
					url.append("_").append(pageNo);
				}
			} else {
				if (getAccessByDir()) {
					url.append(filename.substring(0,
							filename.lastIndexOf("/") + 1));
				} else {
					url.append(filename);
				}
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			if (pageNo > 1) {
				url.append("_").append(pageNo);
				url.append(site.getStaticSuffix());
			} else {
				if (getHasContent()) {
					url.append(SPT);
				} else {
					url.append(site.getStaticSuffix());
				}
			}
		}
		return url.toString();
	}

	public String getStaticFilename(int pageNo) {
		CmsSite site = getSite();
		StringBuilder url = new StringBuilder();
		String staticDir = site.getStaticDir();
		if (!StringUtils.isBlank(staticDir)) {
			url.append(staticDir);
		}
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			int index = filename.indexOf(".", filename.lastIndexOf("/"));
			if (pageNo > 1) {
				if (index != -1) {
					url.append(filename.substring(0, index)).append("_")
							.append(pageNo).append(filename.substring(index));
				} else {
					url.append(filename).append("_").append(pageNo);
				}
			} else {
				url.append(filename);
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			String suffix = site.getStaticSuffix();
			if (getHasContent()) {
				url.append(SPT).append(INDEX);
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			} else {
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			}
		}
		return url.toString();
	}

	public String getStaticFilenameByRule() {
		String rule = getChannelRule();
		if (StringUtils.isBlank(rule)) {
			return null;
		}
		CmsModel model = getModel();
		String url = StaticPageUtils.staticUrlRule(rule, model.getId(), model
				.getPath(), getId(), getPath(), null, null);
		return url;
	}

	/**
	 * 获得动态URL地址
	 * 
	 * @return
	 */
	public String getUrlDynamic() {
		return getUrlDynamic(null);
	}

	public String getUrlDynamic(Boolean whole) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		CmsSite site = getSite();
		StringBuilder url = site.getUrlBuffer(true, whole, false);
		url.append(SPT).append(getPath());
		if (getHasContent()) {
			url.append(SPT).append(INDEX);
		}
		url.append(site.getDynamicSuffix());
		return url.toString();
	}

	/**
	 * 获得节点列表。从父节点到自身。
	 * 
	 * @return
	 */
	public List<Channel> getNodeList() {
		LinkedList<Channel> list = new LinkedList<Channel>();
		Channel node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}

	/**
	 * 获得节点列表ID。从父节点到自身。
	 * 
	 * @return
	 */
	public Integer[] getNodeIds() {
		List<Channel> channels = getNodeList();
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}

	/**
	 * 获得深度
	 * 
	 * @return 第一层为0，第二层为1，以此类推。
	 */
	public int getDeep() {
		int deep = 0;
		Channel parent = getParent();
		while (parent != null) {
			deep++;
			parent = parent.getParent();
		}
		return deep;
	}
	/**
	 * 获取栏目下总浏览量
	 * @return
	 */
	public int getViewTotal() {
		Integer totalView=0;
		List<Channel> list = new ArrayList<Channel>();
		addChildToList(list, this, true);
		for(Channel c:list){
			totalView+=c.getChannelCount().getViews();
		}
		return totalView;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getViewsDayTotal() {
		Integer totalView=0;
		List<Channel> list = new ArrayList<Channel>();
		addChildToList(list, this, true);
		for(Channel c:list){
			totalView+=c.getChannelCount().getViewsDay();
		}
		return totalView;
	}
	
	public int getViewsMonthTotal() {
		Integer totalView=0;
		List<Channel> list = new ArrayList<Channel>();
		addChildToList(list, this, true);
		for(Channel c:list){
			totalView+=c.getChannelCount().getViewsMonth();
		}
		return totalView;
	}
	
	public int getViewsWeekTotal() {
		Integer totalView=0;
		List<Channel> list = new ArrayList<Channel>();
		addChildToList(list, this, true);
		for(Channel c:list){
			totalView+=c.getChannelCount().getViewsWeek();
		}
		return totalView;
	}
	
	private static void addChildToList(List<Channel> list, Channel channel, boolean hasContentOnly) {
		list.add(channel);
		Set<Channel> child = channel.getChild();
		for (Channel c : child) {
			if (hasContentOnly) {
				if (c.getHasContent()) {
					addChildToList(list, c,  hasContentOnly);
				}
			} else {
				addChildToList(list, c, hasContentOnly);
			}
		}
	}
	
	/**
	 * 获得栏目终审级别
	 * 
	 * @return
	 */
	public Byte getFinalStepExtends() {
		Byte step = getFinalStep();
		if (step == null) {
			Channel parent = getParent();
			if (parent == null) {
				return getSite().getFinalStep();
			} else {
				return parent.getFinalStepExtends();
			}
		} else {
			return step;
		}
	}

	public Byte getAfterCheck() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAfterCheck();
		} else {
			return null;
		}
	}

	/**
	 * 获得审核后修改方式的枚举值。如果该值为null则取父级栏目，父栏目为null则取站点相关设置。
	 * 
	 * @return
	 */
	public AfterCheckEnum getAfterCheckEnum() {
		Byte after = getChannelExt().getAfterCheck();
		Channel channel = getParent();
		// 如果为null，则查找父栏目。
		while (after == null && channel != null) {
			after = channel.getAfterCheck();
			channel = channel.getParent();
		}
		// 如果依然为null，则查找站点设置
		if (after == null) {
			after = getSite().getAfterCheck();
		}
		if (after == 1) {
			return AfterCheckEnum.CANNOT_UPDATE;
		} else if (after == 2) {
			return AfterCheckEnum.BACK_UPDATE;
		} else if (after == 3) {
			return AfterCheckEnum.KEEP_UPDATE;
		} else {
			// 默认为不可改、不可删
			return AfterCheckEnum.CANNOT_UPDATE;
		}
	}

	/**
	 * 获得列表用于下拉选择。条件：有内容的栏目。
	 * 
	 * @return
	 */
	public List<Channel> getListForSelect(Set<Channel> rights,
			boolean hasContentOnly) {
		return getListForSelect(rights, null, hasContentOnly);
	}

	public List<Channel> getListForSelect(Set<Channel> rights, Channel exclude,
			boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>((getRgt() - getLft()) / 2);
		addChildToList(list, this, rights, exclude, hasContentOnly);
		return list;
	}

	/**
	 * 获得列表用于下拉选择。条件：有内容的栏目。
	 * 
	 * @param topList
	 *            顶级栏目
	 * @return
	 */
	public static List<Channel> getListForSelect(List<Channel> topList,
			Set<Channel> rights, boolean hasContentOnly) {
		return getListForSelect(topList, rights, null, hasContentOnly);
	}

	public static List<Channel> getListForSelect(List<Channel> topList,
			Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>();
		for (Channel c : topList) {
			addChildToList(list, c, rights, exclude, hasContentOnly);
		}
		return list;
	}

	/**
	 * 递归将子栏目加入列表。条件：有内容的栏目。
	 * 
	 * @param list
	 *            栏目容器
	 * @param channel
	 *            待添加的栏目，且递归添加子栏目
	 * @param rights
	 *            有权限的栏目，为null不控制权限。
	 */
	private static void addChildToList(List<Channel> list, Channel channel,
			Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		if ((rights != null && !rights.contains(channel))
				|| (exclude != null && exclude.equals(channel))) {
			return;
		}
		list.add(channel);
		Set<Channel> child = channel.getChild();
		for (Channel c : child) {
			if (hasContentOnly) {
				if (c.getHasContent()) {
					addChildToList(list, c, rights, exclude, hasContentOnly);
				}
			} else {
				addChildToList(list, c, rights, exclude, hasContentOnly);
			}
		}
	}

	public String getTplChannelOrDef() {
		String tpl = getTplChannel();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getSolutionPath();
			return getModel().getTplChannel(sol, true);
		}
	}

	public String getTplContentOrDef(CmsModel contentModel) {
		/*
		String tpl = getTplContent();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getSolutionPath();
			return getModel().getTplContent(sol, true);
		}
		*/
		String tpl = getModelTpl(contentModel);
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getSolutionPath();
			return contentModel.getTplContent(sol, true);
		}
	}

	public Integer[] getUserIds() {
		Set<CmsUser> users = getUsers();
		return CmsUser.fetchIds(users);
	}

	public void addToViewGroups(CmsGroup group) {
		Set<CmsGroup> groups = getViewGroups();
		if (groups == null) {
			groups = new TreeSet<CmsGroup>(new PriorityComparator());
			setViewGroups(groups);
		}
		groups.add(group);
		group.getViewChannels().add(this);
	}

	public void addToContriGroups(CmsGroup group) {
		Set<CmsGroup> groups = getContriGroups();
		if (groups == null) {
			groups = new TreeSet<CmsGroup>(new PriorityComparator());
			setContriGroups(groups);
		}
		groups.add(group);
		group.getContriChannels().add(this);
	}

	public void addToUsers(CmsUser user) {
		Set<CmsUser> set = getUsers();
		if (set == null) {
			set = new TreeSet<CmsUser>(new PriorityComparator());
			setUsers(set);
		}
		set.add(user);
		user.addToChannels(this);
	}
	
	public void addToChannelModels(CmsModel model,String tpl) {
		List<ChannelModel> list = getChannelModels();
		if (list == null) {
			list = new ArrayList<ChannelModel>();
			setChannelModels(list);
		}
		ChannelModel cm = new ChannelModel();
		cm.setTplContent(tpl);
		cm.setModel(model);
		list.add(cm);
	}
	
	public List<ChannelModel> getChannelModelsExtend() {
		List<ChannelModel>list=getChannelModels();
		//没有配置栏目模型默认父栏目配置
		if (list == null||list.size()<=0) {
			Channel parent = getParent();
			if (parent == null) {
				return null;
			} else {
				return parent.getChannelModelsExtend();
			}
		} else {
			return list;
		}
	}
	
	public List<CmsModel>getModels(){
		List<ChannelModel>list=getChannelModelsExtend();
		if (list == null) {
			return null;
		} 
		List<CmsModel>models=new ArrayList<CmsModel>();
		for(ChannelModel cm:list){
			models.add(cm.getModel());
		}
		return models;
	}
	
	public List<CmsModel>getModels(List<CmsModel>allModels){
		List<ChannelModel>list=getChannelModelsExtend();
		//顶层栏目没有配置默认所有可用模型
		if (list == null) {
			return allModels;
		} 
		List<CmsModel>models=new ArrayList<CmsModel>();
		for(ChannelModel cm:list){
			models.add(cm.getModel());
		}
		return models;
	}
	
	public List<String>getModelIds(){
		List<String>ids=new ArrayList<String>();
		List<CmsModel>models=getModels();
		if(models!=null){
			for(CmsModel model:models){
				ids.add(model.getId().toString());
			}
		}
		return ids;
	}
	
	public List<String>getModelTpls(){
		List<ChannelModel>list=getChannelModelsExtend();
		List<String>tpls=new ArrayList<String>();
		// 当前模板，去除基本路径
		int tplPathLength = getSite().getTplPath().length();
		if(list!=null){
			for(ChannelModel cm:list){
				String tpl=cm.getTplContent();
				if(StringUtils.isNotBlank(tpl)){
					tpls.add(tpl.substring(tplPathLength));
				}
			}
		}
		return tpls;
	}
	
	public String getModelTpl(CmsModel model){
		List<ChannelModel>list=getChannelModelsExtend();
		if(list!=null){
			for(ChannelModel cm:list){
				if(cm.getModel().equals(model)){
					return cm.getTplContent();
				}
			}
		}
		return null;
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getDisplay() == null) {
			setDisplay(true);
		}
	}

	public String getName() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getName();
		} else {
			return null;
		}
	}

	public Boolean getStaticChannel() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getStaticChannel();
		} else {
			return null;
		}
	}

	public Boolean getStaticContent() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getStaticContent();
		} else {
			return null;
		}
	}

	public Boolean getAccessByDir() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAccessByDir();
		} else {
			return null;
		}
	}

	public Boolean getListChild() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getListChild();
		} else {
			return null;
		}
	}

	public Integer getPageSize() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getPageSize();
		} else {
			return null;
		}
	}

	public String getChannelRule() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getChannelRule();
		} else {
			return null;
		}
	}

	public String getContentRule() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentRule();
		} else {
			return null;
		}
	}

	public Byte getFinalStep() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getFinalStep();
		} else {
			return null;
		}
	}

	public String getLink() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getLink();
		} else {
			return null;
		}
	}

	public String getTplChannel() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTplChannel();
		} else {
			return null;
		}
	}

	public String getTplContent() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTplContent();
		} else {
			return null;
		}
	}

	public Boolean getHasTitleImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getHasTitleImg();
		} else {
			return null;
		}
	}

	public Boolean getHasContentImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getHasContentImg();
		} else {
			return null;
		}
	}

	public Integer getTitleImgWidth() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImgWidth();
		} else {
			return null;
		}
	}

	public Integer getTitleImgHeight() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImgHeight();
		} else {
			return null;
		}
	}

	public Integer getContentImgWidth() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImgWidth();
		} else {
			return null;
		}
	}

	public Integer getContentImgHeight() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImgHeight();
		} else {
			return null;
		}
	}

	public String getTitleImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImg();
		} else {
			return null;
		}
	}

	public String getContentImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImg();
		} else {
			return null;
		}
	}

	public String getTitle() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getKeywords() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getKeywords();
		} else {
			return null;
		}
	}

	public String getDescription() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getDescription();
		} else {
			return null;
		}
	}

	public Integer getCommentControl() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getCommentControl();
		} else {
			return null;
		}
	}

	public Boolean getAllowUpdown() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAllowUpdown();
		} else {
			return null;
		}
	}
	
	public Boolean getAllowShare() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAllowShare();
		} else {
			return null;
		}
	}
	
	public Boolean getAllowScore() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAllowScore();
		} else {
			return null;
		}
	}

	public Boolean getBlank() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getBlank();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容
	 * 
	 * @return
	 */
	public String getTxt() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容1
	 * 
	 * @return
	 */
	public String getTxt1() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt1();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容2
	 * 
	 * @return
	 */
	public String getTxt2() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt2();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容3
	 * 
	 * @return
	 */
	public String getTxt3() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt3();
		} else {
			return null;
		}
	}

	public ChannelTxt getChannelTxt() {
		Set<ChannelTxt> set = getChannelTxtSet();
		if (set != null && set.size() > 0) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	/**
	 * 每个站点各自维护独立的树结构
	 * 
	 * @see HibernateTree#getTreeCondition()
	 */
	public String getTreeCondition() {
		return "bean.site.id=" + getSite().getId();
	}

	/**
	 * @see HibernateTree#getParentId()
	 */
	public Integer getParentId() {
		Channel parent = getParent();
		if (parent != null) {
			return parent.getId();
		} else {
			return null;
		}
	}

	/**
	 * @see HibernateTree#getLftName()
	 */
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	/**
	 * @see HibernateTree#getParentName()
	 */
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	/**
	 * @see HibernateTree#getRgtName()
	 */
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	public static Integer[] fetchIds(Collection<Channel> channels) {
		if (channels == null) {
			return null;
		}
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}
	
	public  void removeViewGroup(CmsGroup group) {
		Set<CmsGroup>viewGroups=getViewGroups();
		viewGroups.remove(group);
	}
	
	public  void removeContriGroup(CmsGroup group) {
		Set<CmsGroup>contriGroups=getContriGroups();
		contriGroups.remove(group);
	}
	

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Channel() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Channel(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Channel(java.lang.Integer id,
			com.jeecms.core.entity.CmsSite site,
			com.jeecms.cms.entity.main.CmsModel model, java.lang.Integer lft,
			java.lang.Integer rgt, java.lang.Integer priority,
			java.lang.Boolean hasContent, java.lang.Boolean display) {

		super(id, site, model, lft, rgt, priority, hasContent, display);
	}

	/* [CONSTRUCTOR MARKER END] */

}