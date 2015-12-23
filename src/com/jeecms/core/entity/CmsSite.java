package com.jeecms.core.entity;

import static com.jeecms.cms.Constants.RES_PATH;
import static com.jeecms.cms.Constants.TPL_BASE;
import static com.jeecms.cms.Constants.TPLDIR_INDEX;
import static com.jeecms.cms.Constants.TPL_SUFFIX;
import static com.jeecms.cms.Constants.UPLOAD_PATH;
import static com.jeecms.cms.Constants.LIBRARY_PATH;
import static com.jeecms.common.web.Constants.INDEX;
import static com.jeecms.common.web.Constants.SPT;

import java.util.Collection;
import org.apache.commons.lang.StringUtils;

import com.jeecms.core.entity.base.BaseCmsSite;

public class CmsSite extends BaseCmsSite {
	private static final long serialVersionUID = 1L;
	public static final String PV_TOTAL="pvTotal";
	public static final String VISITORS="visitors";	
	/**
	 * 返回首页模板
	 * @return
	 */
	public String getTplIndexOrDef() {
		String tpl = getTplIndex();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			return getTplIndexDefault();
		}
	}
	
	/**
	 * 返回首页默认模板(类似/WEB-INF/t/cms/www/default/index/index.html)
	 * @return
	 */
	private String getTplIndexDefault() {
		StringBuilder t = new StringBuilder();
		t.append(getTplIndexPrefix(TPLDIR_INDEX));
		t.append(TPL_SUFFIX);
		return t.toString();
	}
	
	/**
	 * 返回完整前缀(类似/WEB-INF/t/cms/www/default/index/index)
	 * @param prefix
	 * @return
	 */
	public String getTplIndexPrefix(String prefix) {
		StringBuilder t = new StringBuilder();
		t.append(getSolutionPath()).append("/");
		t.append(TPLDIR_INDEX).append("/");
		if (!StringUtils.isBlank(prefix)) {
			t.append(prefix);
		}
		return t.toString();
	}
	

	/**
	 * 获得站点url
	 * 
	 * @return
	 */
	public String getUrl() {
		StringBuilder url = new StringBuilder();
		if (getStaticIndex()) {
			url.append(getUrlStatic());
			if (!getIndexToRoot()) {
				if (!StringUtils.isBlank(getStaticDir())) {
					url.append(getStaticDir());
				}
			}
			url.append(SPT).append(INDEX).append(getStaticSuffix());
		} else {
			url.append(getUrlDynamic());
		}
		return url.toString();
		/*
		if (getStaticIndex()) {
			return getUrlStatic();
		} else {
			return getUrlDynamic();
		}
		*/
	}

	/**
	 * 获得完整路径。在给其他网站提供客户端包含时也可以使用。
	 * 
	 * @return
	 */
	public String getUrlWhole() {
		if (getStaticIndex()) {
			return getUrlBuffer(false, true, false).append("/").toString();
		} else {
			return getUrlBuffer(true, true, false).append("/").toString();
		}
	}

	public String getUrlDynamic() {
		return getUrlBuffer(true, null, false).append("/").toString();
	}

	public String getUrlStatic() {
		return getUrlBuffer(false, null, true).append("/").toString();
	}

	public StringBuilder getUrlBuffer(boolean dynamic, Boolean whole,
			boolean forIndex) {
		boolean relative = whole != null ? !whole : getRelativePath();
		String ctx = getContextPath();
		StringBuilder url = new StringBuilder();
		if (!relative) {
			url.append(getProtocol()).append(getDomain());
			if (getPort() != null) {
				url.append(":").append(getPort());
			}
		}
		if (!StringUtils.isBlank(ctx)) {
			url.append(ctx);
		}
		if (dynamic) {
			String servlet = getServletPoint();
			if (!StringUtils.isBlank(servlet)) {
				url.append(servlet);
			}
		} else {
			if (!forIndex) {
				String staticDir = getStaticDir();
				if (!StringUtils.isBlank(staticDir)) {
					url.append(staticDir);
				}
			}
		}
		return url;
	}

	/**
	 * 获得模板路径。如：/WEB-INF/t/cms/www
	 * 
	 * @return
	 */
	public String getTplPath() {
		return TPL_BASE + "/" + getPath();
	}

	/**
	 * 获得模板方案路径。如：/WEB-INF/t/cms/www/default
	 * 
	 * @return
	 */
	public String getSolutionPath() {
		return TPL_BASE + "/" + getPath() + "/" + getTplSolution();
	}

	/**
	 * 获得模板资源路径。如：/r/cms/www
	 * 
	 * @return
	 */
	public String getResPath() {
		return RES_PATH + "/" + getPath();
	}

	/**
	 * 获得上传路径。如：/u/jeecms/path
	 * 
	 * @return
	 */
	public String getUploadPath() {
		return UPLOAD_PATH + getPath();
	}
	/**
	 * 获得文库路径。如：/wenku/path
	 * 
	 * @return
	 */
	public String getLibraryPath() {
		return LIBRARY_PATH + getPath();
	}

	/**
	 * 获得上传访问前缀。
	 * 
	 * 根据配置识别上传至数据、FTP和本地
	 * 
	 * @return
	 */
	public String getUploadBase() {
		CmsConfig config = getConfig();
		String ctx = config.getContextPath();
		if (config.getUploadToDb()) {
			if (!StringUtils.isBlank(ctx)) {
				return ctx + config.getDbFileUri();
			} else {
				return config.getDbFileUri();
			}
		} else if (getUploadFtp() != null) {
			return getUploadFtp().getUrl();
		} else {
			if (!StringUtils.isBlank(ctx)) {
				return ctx;
			} else {
				return "";
			}
		}
	}

	public String getServletPoint() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getServletPoint();
		} else {
			return null;
		}
	}

	public String getContextPath() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getContextPath();
		} else {
			return null;
		}
	}

	public Integer getPort() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getPort();
		} else {
			return null;
		}
	}

	public String getDefImg() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getDefImg();
		} else {
			return null;
		}
	}

	public String getLoginUrl() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getLoginUrl();
		} else {
			return null;
		}
	}

	public String getProcessUrl() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getProcessUrl();
		} else {
			return null;
		}
	}

	public int getUsernameMinLen() {
		return getConfig().getMemberConfig().getUsernameMinLen();
	}

	public int getPasswordMinLen() {
		return getConfig().getMemberConfig().getPasswordMinLen();
	}
	
	public Boolean getMark(){
		return getConfig().getMarkConfig().getOn();
	}
	
	public String getNewPic(){
		return getConfig().getConfigAttr().getPictureNew();
	}
	
	public Long getPvTotal(){
		String pv=getAttr().get(PV_TOTAL);
		if(StringUtils.isNotBlank(pv)){
			return Long.decode(pv);
		}else{
			return 0l;
		}
	}
	
	public Long getVisitorTotal(){
		String visitorNum=getAttr().get(VISITORS);
		if(StringUtils.isNotBlank(visitorNum)){
			return Long.decode(visitorNum);
		}else{
			return 0l;
		}
	}
	
	public static Integer[] fetchIds(Collection<CmsSite> sites) {
		if (sites == null) {
			return null;
		}
		Integer[] ids = new Integer[sites.size()];
		int i = 0;
		for (CmsSite s : sites) {
			ids[i++] = s.getId();
		}
		return ids;
	}
	
	public String getBaseDomain() {
		String domain = getDomain();
		if (domain.indexOf(".") > -1) {
			return domain.substring(domain.indexOf(".") + 1);
		}
		return domain;
	}

	public void init() {
		if (StringUtils.isBlank(getProtocol())) {
			setProtocol("http://");
		}
		if (StringUtils.isBlank(getTplSolution())) {
			//默认路径名作为方案名
			setTplSolution(getPath());
			//setTplSolution(DEFAULT);
		}
		if (getFinalStep() == null) {
			byte step = 2;
			setFinalStep(step);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsSite () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSite (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSite (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsConfig config,
		java.lang.String domain,
		java.lang.String path,
		java.lang.String name,
		java.lang.String protocol,
		java.lang.String dynamicSuffix,
		java.lang.String staticSuffix,
		java.lang.Boolean indexToRoot,
		java.lang.Boolean staticIndex,
		java.lang.String localeAdmin,
		java.lang.String localeFront,
		java.lang.String tplSolution,
		java.lang.Byte finalStep,
		java.lang.Byte afterCheck,
		java.lang.Boolean relativePath,
		java.lang.Boolean resycleOn) {

		super (
			id,
			config,
			domain,
			path,
			name,
			protocol,
			dynamicSuffix,
			staticSuffix,
			indexToRoot,
			staticIndex,
			localeAdmin,
			localeFront,
			tplSolution,
			finalStep,
			afterCheck,
			relativePath,
			resycleOn);
	}

	/* [CONSTRUCTOR MARKER END] */

}