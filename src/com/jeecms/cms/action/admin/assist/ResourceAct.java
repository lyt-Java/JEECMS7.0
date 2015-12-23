package com.jeecms.cms.action.admin.assist;

import java.io.IOException;
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

import com.jeecms.cms.manager.assist.CmsResourceMng;
import com.jeecms.common.file.FileWrap;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

/**
 * JEECMS资源的Action
 * 
 */
// TODO 验证path必须以TPL_BASE开头，不能有..后退关键字
@Controller
public class ResourceAct {
	private static final Logger log = LoggerFactory
			.getLogger(ResourceAct.class);
	private static final String INVALID_PARAM = "template.invalidParams";


	@RequiresPermissions("resource:resource_main")
	@RequestMapping("/resource/resource_main.do")
	public String resourceMain(ModelMap model) {
		return "resource/resource_main";
	}
	
	@RequiresPermissions("resource:v_left")
	@RequestMapping("/resource/v_left.do")
	public String left(String path, HttpServletRequest request, ModelMap model) {
		return "resource/left";
	}

	@RequiresPermissions("resource:v_tree")
	@RequestMapping(value = "/resource/v_tree.do")
	public String tree(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		log.debug("tree path={}", root);
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			root = site.getResPath();
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
		List<? extends FileWrap> resList = resourceMng.listFile(root, true);
		model.addAttribute("resList", resList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "resource/tree";
	}

	// 直接调用方法需要把root参数保存至model中
	@RequiresPermissions("resource:v_list")
	@RequestMapping(value = "/resource/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = (String) model.get("root");
		if (root == null) {
			root = RequestUtils.getQueryParam(request, "root");
		}
		log.debug("list Resource root: {}", root);
		if (StringUtils.isBlank(root)) {
			root = site.getResPath();
		}
		WebErrors errors = validateList(root, site.getResPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String rel = root.substring(site.getResPath().length());
		if (rel.length() == 0) {
			rel = "/";
		}
		model.addAttribute("root", root);
		model.addAttribute("rel", rel);
		model.addAttribute("list", resourceMng.listFile(root, false));
		return "resource/list";
	}

	@RequiresPermissions("resource:o_create_dir")
	@RequestMapping(value = "/resource/o_create_dir.do")
	public String createDir(String root, String dirName,
			HttpServletRequest request, ModelMap model) {
		// TODO 检查dirName是否存在
		resourceMng.createDir(root, dirName);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("resource:v_add")
	@RequestMapping(value = "/resource/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		String root = RequestUtils.getQueryParam(request, "root");
		model.addAttribute("root", root);
		return "resource/add";
	}

	@RequiresPermissions("resource:v_edit")
	@RequestMapping("/resource/v_edit.do")
	public String edit(HttpServletRequest request, ModelMap model)
			throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		WebErrors errors = validateEdit(root, site.getResPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("source", resourceMng.readFile(name));
		model.addAttribute("root", root);
		model.addAttribute("name", name);
		model.addAttribute("filename", name
				.substring(name.lastIndexOf('/') + 1));
		return "resource/edit";
	}

	@RequiresPermissions("resource:o_save")
	@RequestMapping("/resource/o_save.do")
	public String save(String root, String filename, String source,
			HttpServletRequest request, ModelMap model) throws IOException {
		WebErrors errors = validateSave(filename, source, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		resourceMng.createFile(root, filename, source);
		model.addAttribute("root", root);
		log.info("save Resource name={}", filename);
		cmsLogMng.operating(request, "resource.log.save", "filename="
				+ filename);
		return "redirect:v_list.do";
	}

	// AJAX请求，不返回页面
	@RequiresPermissions("resource:o_update")
	@RequestMapping("/resource/o_update.do")
	public void update(String root, String name, String source,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateUpdate(root, name,site.getResPath(), source, request);
		if (errors.hasErrors()) {
			ResponseUtils.renderJson(response, "{\"success\":false,\"msg\":'"
					+ errors.getErrors().get(0) + "'}");
		}
		resourceMng.updateFile(name, source);
		log.info("update Resource name={}.", name);
		cmsLogMng.operating(request, "resource.log.update", "filename=" + name);
		model.addAttribute("root", root);
		ResponseUtils.renderJson(response, "{\"success\":true}");
	}

	@RequiresPermissions("resource:o_delete")
	@RequestMapping("/resource/o_delete.do")
	public String delete(String root, String[] names,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateDelete(root, names,site.getResPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		int count = resourceMng.delete(names);
		log.info("delete Resource count: {}", count);
		for (String name : names) {
			log.info("delete Resource name={}", name);
			cmsLogMng.operating(request, "resource.log.delete", "filename="
					+ name);
		}
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("resource:o_delete_single")
	@RequestMapping("/resource/o_delete_single.do")
	public String deleteSingle(HttpServletRequest request, ModelMap model) {
		// TODO 输入验证
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		int count = resourceMng.delete(new String[] { name });
		log.info("delete Resource {}, count {}", name, count);
		cmsLogMng.operating(request, "resource.log.delete", "filename=" + name);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("resource:v_rename")
	@RequestMapping(value = "/resource/v_rename.do")
	public String renameInput(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		String origName = name.substring(site.getResPath().length());
		model.addAttribute("origName", origName);
		model.addAttribute("root", root);
		return "resource/rename";
	}

	@RequiresPermissions("resource:o_rename")
	@RequestMapping(value = "/resource/o_rename.do", method = RequestMethod.POST)
	public String renameSubmit(String root, String origName, String distName,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String orig = site.getResPath() + origName;
		String dist = site.getResPath() + distName;
		resourceMng.rename(orig, dist);
		log.info("name Resource from {} to {}", orig, dist);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("resource:v_upload")
	@RequestMapping(value = "/resource/v_upload.do")
	public String uploadInput(HttpServletRequest request, ModelMap model) {
		String root = RequestUtils.getQueryParam(request, "root");
		model.addAttribute("root", root);
		return "resource/upload";
	}

	@RequiresPermissions("resource:o_upload")
	@RequestMapping(value = "/resource/o_upload.do", method = RequestMethod.POST)
	public String uploadSubmit(String root, HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("resource:o_swfupload")
	@RequestMapping(value = "/resource/o_swfupload.do", method = RequestMethod.POST)
	public void swfUpload(
			String root,
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IllegalStateException, IOException {
		resourceMng.saveFile(root, file);
		model.addAttribute("root", root);
		log.info("file upload seccess: {}, size:{}.", file
				.getOriginalFilename(), file.getSize());
		ResponseUtils.renderText(response, "");
	}

	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateList(String name, String resPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(name, name, resPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}
	
	private WebErrors validateSave(String name, String source,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(String id, String resPath,HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		if(isUnValidName(id, id, resPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateUpdate(String root, String name,String  resPath,String source,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(root, name, resPath, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateDelete(String root, String[] names, String resPath,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(names, "names");
		for (String id : names) {
			vldExist(id, errors);
			if(isUnValidName(id, id, resPath, errors)){
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
		// Tpl entity = tplManager.get(name);
		// if (errors.ifNotExist(entity, Tpl.class, name)) {
		// return true;
		// }
		return false;
	}
	
	private boolean isUnValidName(String path,String name,String resPath, WebErrors errors) {
		if (!path.startsWith(resPath)||path.contains("../")||path.contains("..\\")||name.contains("..\\")||name.contains("../")) {
			return true;
		}else{
			return false;
		}
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	private CmsResourceMng resourceMng;

	@Autowired
	public void setResourceMng(CmsResourceMng resourceMng) {
		this.resourceMng = resourceMng;
	}
}