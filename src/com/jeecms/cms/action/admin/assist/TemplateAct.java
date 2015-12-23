package com.jeecms.cms.action.admin.assist;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.cms.Constants;
import com.jeecms.cms.manager.assist.CmsDirectiveTplMng;
import com.jeecms.cms.manager.assist.CmsResourceMng;
import com.jeecms.common.util.Zipper;
import com.jeecms.common.util.Zipper.FileEntry;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.tpl.Tpl;
import com.jeecms.core.tpl.TplManager;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

/**
 * JEECMS模板的Action
 */
@Controller
public class TemplateAct {
	public static final String TEXT_AREA = "textarea";
	public static final String EDITOR = "editor";
	public static final String VISUAL = "visual";
	private static final String INVALID_PARAM = "template.invalidParams";

	private static final Logger log = LoggerFactory
			.getLogger(TemplateAct.class);

	@RequiresPermissions("template:template_main")
	@RequestMapping("/template/template_main.do")
	public String templateMain(ModelMap model) {
		return "template/template_main";
	}

	@RequiresPermissions("template:v_left")
	@RequestMapping("/template/v_left.do")
	public String left(String path, HttpServletRequest request, ModelMap model) {
		return "template/left";
	}

	@RequiresPermissions("template:v_tree")
	@RequestMapping(value = "/template/v_tree.do", method = RequestMethod.GET)
	public String tree(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		log.debug("tree path={}", root);
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			root = site.getTplPath();
			model.addAttribute("isRoot", true);
		} else {
			model.addAttribute("isRoot", false);
		}
		WebErrors errors = validateTree(root, request);
		if (errors.hasErrors()) {
			log.error(errors.getErrors().get(0));
			ResponseUtils.renderJson(response, "[]");
			return null;
		}
		List<? extends Tpl> tplList = tplManager.getChild(root);
		model.addAttribute("tplList", tplList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "template/tree";
	}

	// 直接调用方法需要把root参数保存至model中
	@RequiresPermissions("template:v_list")
	@RequestMapping(value = "/template/v_list.do", method = RequestMethod.GET)
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = (String) model.get("root");
		if (root == null) {
			root = RequestUtils.getQueryParam(request, "root");
		}
		log.debug("list Template root: {}", root);
		if (StringUtils.isBlank(root)) {
			root = site.getTplPath();
		}
		WebErrors errors = validateList(root, site.getTplPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String rel = root.substring(site.getTplPath().length());
		if (rel.length() == 0) {
			rel = "/";
		}
		model.addAttribute("root", root);
		model.addAttribute("rel", rel);
		model.addAttribute("list", tplManager.getChild(root));
		return "template/list";
	}

	@RequiresPermissions("template:o_create_dir")
	@RequestMapping(value = "/template/o_create_dir.do")
	public String createDir(String root, String dirName,
			HttpServletRequest request, ModelMap model) {
		// TODO 检查dirName是否存在
		tplManager.save(root + "/" + dirName, null, true);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("template:v_add")
	@RequestMapping(value = "/template/v_add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		WebErrors errors = validateAdd(root, site.getTplPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String style = handerStyle(RequestUtils.getQueryParam(request, "style"));
		model.addAttribute("directives", directiveTplMng.getList(Integer.MAX_VALUE));
		model.addAttribute("root", root);
		return "template/add_" + style;
	}

	@RequiresPermissions("template:v_edit")
	@RequestMapping("/template/v_edit.do")
	public String edit(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		String style = handerStyle(RequestUtils.getQueryParam(request, "style"));
		WebErrors errors = validateEdit(root, name,site.getTplPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("directives", directiveTplMng.getList(Integer.MAX_VALUE));
		model.addAttribute("template", tplManager.get(name));
		model.addAttribute("root", root);
		model.addAttribute("name", name);
		return "template/edit_" + style;
	}

	@RequiresPermissions("template:o_save")
	@RequestMapping("/template/o_save.do")
	public String save(String root, String filename, String source,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(filename, source, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String name = root + "/" + filename + Constants.TPL_SUFFIX;
		tplManager.save(name, source, false);
		model.addAttribute("root", root);
		log.info("save Template name={}", filename);
		cmsLogMng.operating(request, "template.log.save", "filename="
				+ filename);
		return "redirect:v_list.do";
	}

	// AJAX请求，不返回页面
	@RequiresPermissions("template:o_ajaxUpdate")
	@RequestMapping("/template/o_ajaxUpdate.do")
	public void ajaxUpdate(String root, String name, String source,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateUpdate(root, name, site.getTplPath(),source, request);
		if (errors.hasErrors()) {
			ResponseUtils.renderJson(response, "{\"success\":false,\"msg\":'"
					+ errors.getErrors().get(0) + "'}");
		}
		tplManager.update(name, source);
		log.info("update Template name={}.", name);
		cmsLogMng.operating(request, "template.log.update", "filename=" + name);
		model.addAttribute("root", root);
		ResponseUtils.renderJson(response, "{\"success\":true}");
	}

	@RequiresPermissions("template:o_update")
	@RequestMapping("/template/o_update.do")
	public String update(String root, String name, String source,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateUpdate(root, name,site.getTplPath(), source, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 此处需要将标签内被替换的特殊符号还原
		source = source.replaceAll("&quot;", "\"");
		source = source.replaceAll("&amp;", "&");
		source = source.replaceAll("&lt;", "<");
		source = source.replaceAll("&gt;", ">");
		tplManager.update(name, source);
		log.info("update Template name={}.", name);
		cmsLogMng.operating(request, "template.log.update", "filename=" + name);
		model.addAttribute("template", tplManager.get(name));
		model.addAttribute("root", root);
		return "template/edit_" + EDITOR;
	}

	@RequiresPermissions("template:o_delete")
	@RequestMapping("/template/o_delete.do")
	public String delete(String root, String[] names,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateDelete(names, site.getTplPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		int count = tplManager.delete(names);
		log.info("delete Template count: {}", count);
		for (String name : names) {
			log.info("delete Template name={}", name);
			cmsLogMng.operating(request, "template.log.delete", "filename="
					+ name);
		}
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("template:o_delete_single")
	@RequestMapping("/template/o_delete_single.do")
	public String deleteSingle(HttpServletRequest request, ModelMap model) {
		// TODO 输入验证
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateDelete(new String[] { name }, site
				.getTplPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		int count = tplManager.delete(new String[] { name });
		log.info("delete Template {}, count {}", name, count);
		cmsLogMng.operating(request, "template.log.delete", "filename=" + name);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("template:v_rename")
	@RequestMapping(value = "/template/v_rename.do", method = RequestMethod.GET)
	public String renameInput(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		String origName = name.substring(site.getTplPath().length());
		model.addAttribute("origName", origName);
		model.addAttribute("root", root);
		return "template/rename";
	}

	@RequiresPermissions("template:o_rename")
	@RequestMapping(value = "/template/o_rename.do", method = RequestMethod.POST)
	public String renameSubmit(String root, String origName, String distName,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String orig = site.getTplPath() + origName;
		String dist = site.getTplPath() + distName;
		tplManager.rename(orig, dist);
		log.info("name Template from {} to {}", orig, dist);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("template:o_swfupload")
	@RequestMapping(value = "/template/o_swfupload.do", method = RequestMethod.POST)
	public void swfUpload(
			String root,
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IllegalStateException, IOException {
		tplManager.save(root, file);
		model.addAttribute("root", root);
		log.info("file upload seccess: {}, size:{}.", file
				.getOriginalFilename(), file.getSize());
		ResponseUtils.renderText(response, "");
	}

	@RequiresPermissions("template:v_setting")
	@RequestMapping(value = "/template/v_setting.do")
	public String setting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String[] solutions = resourceMng.getSolutions(site.getTplPath());
		model.addAttribute("solutions", solutions);
		model.addAttribute("defSolution", site.getTplSolution());
		return "template/setting";
	}

	@RequiresPermissions("template:o_def_template")
	@RequestMapping(value = "/template/o_def_template.do")
	public void defTempate(String solution, HttpServletRequest request,
			HttpServletResponse response) {
		CmsSite site = CmsUtils.getSite(request);
		cmsSiteMng.updateTplSolution(site.getId(), solution);
		ResponseUtils.renderJson(response, "{\"success\":true}");
	}

	@RequiresPermissions("template:o_export")
	@RequestMapping(value = "/template/o_export.do")
	public void exportSubmit(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String solution = RequestUtils.getQueryParam(request, "solution");
		CmsSite site = CmsUtils.getSite(request);
		List<FileEntry> fileEntrys = resourceMng.export(site, solution);
		response.setContentType("application/x-download;charset=UTF-8");
		response.addHeader("Content-disposition", "filename=template-"
				+ solution + ".zip");
		try {
			// 模板一般都在windows下编辑，所以默认编码为GBK
			Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
		} catch (IOException e) {
			log.error("export template error!", e);
		}
	}

	@RequiresPermissions("template:o_import")
	@RequestMapping(value = "/template/o_import.do")
	public String importSubmit(
			@RequestParam(value = "tplZip", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		File tempFile = File.createTempFile("tplZip", "temp");
		file.transferTo(tempFile);
		resourceMng.imoport(tempFile, site);
		tempFile.delete();
		return setting(request, response, model);
	}

	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateList(String name, String tplPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(name, name, tplPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateAdd(String name, String tplPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(name, name, tplPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateSave(String name, String source,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(String path, String name,String tplPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(path, errors)) {
			return errors;
		}
		if(isUnValidName(path, name, tplPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateUpdate(String root, String name, String tplPath,String source,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(root, name, tplPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateDelete(String[] names, String tplPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(names, "names");
		for (String id : names) {
			if (vldExist(id, errors)) {
				return errors;
			}
			if(isUnValidName(id, id, tplPath, errors)){
				errors.addErrorCode(INVALID_PARAM);
				return errors;
			}
		}
		return errors;
	}

	private boolean vldExist(String name, WebErrors errors) {
		if (errors.ifNull(name, "name")) {
			return true;
		}
		Tpl entity = tplManager.get(name);
		if (errors.ifNotExist(entity, Tpl.class, name)) {
			return true;
		}
		return false;
	}
	
	private boolean isUnValidName(String path,String name,String tplPath, WebErrors errors) {
		if (!path.startsWith(tplPath)||path.contains("../")||path.contains("..\\")||name.contains("..\\")||name.contains("../")) {
			return true;
		}else{
			return false;
		}
	}

	private String handerStyle(String style) {
		if (TEXT_AREA.equals(style) || EDITOR.equals(style) || VISUAL.equals(style)) {
			return style;
		}
		return TEXT_AREA;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	private TplManager tplManager;
	private CmsResourceMng resourceMng;
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsDirectiveTplMng directiveTplMng;

	public void setTplManager(TplManager tplManager) {
		this.tplManager = tplManager;
	}

	@Autowired
	public void setResourceMng(CmsResourceMng resourceMng) {
		this.resourceMng = resourceMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}
}