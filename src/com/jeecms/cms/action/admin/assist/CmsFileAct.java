package com.jeecms.cms.action.admin.assist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import com.jeecms.cms.entity.assist.CmsFile;
import com.jeecms.cms.manager.assist.CmsFileMng;
import com.jeecms.cms.manager.assist.CmsResourceMng;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

/**
 * JEECMS附件（包括文章图片多媒体）的Action
 * 
 * @author TOM
 * 
 */
//
@Controller
public class CmsFileAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsFileAct.class);
	private static final String INVALID_PARAM = "template.invalidParams";


	// 直接调用方法需要把root参数保存至model中
	@RequiresPermissions("file:v_list")
	@RequestMapping(value = "/file/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		String root = (String) model.get("root");
		if (root == null) {
			root = RequestUtils.getQueryParam(request, "root");
		}
		String valid=RequestUtils.getQueryParam(request, "valid");
		Boolean validB=null;
		if(StringUtils.isNotBlank(valid)){
			if(valid.equals("1")){
				validB=true;
			}else{
				validB=false;
			}
		}
		log.debug("list Resource root: {}", root);
		if (StringUtils.isBlank(root)) {
			root = site.getUploadPath();
		}
		String uploadPath = root.substring(site.getUploadPath().length());
		if (uploadPath.length() == 0) {
			uploadPath = "/";
		}
		WebErrors errors = validateList(root, site.getUploadPath(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("root", root);
		model.addAttribute("rel", uploadPath);
		model.addAttribute("valid",validB);
		model.addAttribute("list", resourceMng.queryFiles(root, validB));
		return "file/list";
	}
	
	@RequiresPermissions("file:o_delfreefiles")
	@RequestMapping("/file/o_delfreefiles.do")
	public String deleteUnValid(String root,
			HttpServletRequest request, ModelMap model) {
		List<CmsFile>fileList=fileMng.getList(false);
		CmsSite site=CmsUtils.getSite(request);
		String contextPath=site.getContextPath();
		String[]names=new String[fileList.size()];
		String filePath;
		for(int i=0;i<names.length;i++){
			filePath=fileList.get(i).getFilePath();
			//只有文件才删除
			if(filePath.indexOf(".")!=-1){
				if(StringUtils.isNotBlank(contextPath)){
					if(filePath.contains(contextPath)){
						names[i]=filePath.substring(filePath.indexOf(contextPath)+contextPath.length());
					}
				}else{
					names[i]=filePath;
				}
			}
		}
		//去掉空
		List<String>nameList=new ArrayList<String>();
		for(String name:names){
			if(StringUtils.isNotBlank(name)){
				nameList.add(name);
			}
		}
		names=(String[]) nameList.toArray(new String[nameList.size()]);
		WebErrors errors = validateDeleteFreeFile(root, site.getUploadPath(),  names, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if(names!=null&&names.length>0){
			int count = resourceMng.delete(names);
			log.info("delete Resource count: {}", count);
			for (String name : names) {
				fileMng.deleteByPath(name);
				log.info("delete Resource name={}", name);
				cmsLogMng.operating(request, "resource.log.delete", "filename="
						+ name);
			}
		}
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("file:o_delete")
	@RequestMapping("/file/o_delete.do")
	public String delete(String root, String[] names,
			HttpServletRequest request, ModelMap model) {
		CmsSite site=CmsUtils.getSite(request);
		WebErrors errors = validateDelete(root, site.getUploadPath(), names, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		int count = resourceMng.delete(names);
		log.info("delete Resource count: {}", count);
		for (String name : names) {
			fileMng.deleteByPath(name);
			log.info("delete Resource name={}", name);
			cmsLogMng.operating(request, "resource.log.delete", "filename="
					+ name);
		}
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("file:o_delete_single")
	@RequestMapping("/file/o_delete_single.do")
	public String deleteSingle(HttpServletRequest request, ModelMap model) {
		// TODO 输入验证
		String root = RequestUtils.getQueryParam(request, "root");
		String name = RequestUtils.getQueryParam(request, "name");
		int count = resourceMng.delete(new String[] { name });
		fileMng.deleteByPath(name);
		log.info("delete Resource {}, count {}", name, count);
		cmsLogMng.operating(request, "resource.log.delete", "filename=" + name);
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("file:v_upload")
	@RequestMapping(value = "/file/v_upload.do")
	public String uploadInput(HttpServletRequest request, ModelMap model) {
		String root = RequestUtils.getQueryParam(request, "root");
		model.addAttribute("root", root);
		return "file/upload";
	}

	@RequiresPermissions("file:o_upload")
	@RequestMapping(value = "/file/o_upload.do", method = RequestMethod.POST)
	public String uploadSubmit(String root, HttpServletRequest request,
			ModelMap model) {
		model.addAttribute("root", root);
		return list(request, model);
	}

	@RequiresPermissions("file:o_swfupload")
	@RequestMapping(value = "/file/o_swfupload.do", method = RequestMethod.POST)
	public void swfUpload(
			String root,
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IllegalStateException, IOException {
		resourceMng.saveFile(root, file);
		fileMng.saveFileByPath(root+"//"+file.getOriginalFilename(), file.getOriginalFilename(), false);
		model.addAttribute("root", root);
		log.info("file upload seccess: {}, size:{}.", file
				.getOriginalFilename(), file.getSize());
		ResponseUtils.renderText(response, "");
	}
	
	@RequiresPermissions("file:o_flag_files")
	@RequestMapping(value = "/file/o_flag_files.do")
	public String flagOldFilesValid(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
	    String root = site.getUploadPath();
		String uploadPath = root.substring(site.getUploadPath().length());
		if (uploadPath.length() == 0) {
			uploadPath = "/";
		}
		model.addAttribute("root", root);
		model.addAttribute("rel", uploadPath);
		model.addAttribute("list", resourceMng.queryFiles(root, false));
		saveFileFlags(realPathResolver.get(root),root);
		return list(request, model);
	}

	private  void saveFileFlags(String realpath,String path){   
        File file = new File(realpath);   
        File[] array = file.listFiles();   
        String filePath;
        for(int i=0;i<array.length;i++){   
        	filePath=path+"/"+array[i].getName();
            if(array[i].isFile()){ 
            	if(fileMng.findByPath(filePath)==null){
            		fileMng.saveFileByPath(filePath,array[i].getName(), true);
            	}
            }else if(array[i].isDirectory()){ 
            	if(fileMng.findByPath(filePath)==null){
            		fileMng.saveFileByPath(filePath,array[i].getName(), true);
            	}
            	saveFileFlags(array[i].getPath(),path);   
            }   
        }   
    }

	private WebErrors validateList(String name, String path,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(name, errors)) {
			return errors;
		}
		if(isUnValidName(name, name, path, errors)){
			errors.addErrorCode(INVALID_PARAM);
		}
		return errors;
	}

	private WebErrors validateDelete(String root, String path,String[] names,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(names, "names");
		for (String id : names) {
			vldExist(id, errors);
		}
		for(String name:names){
			if(isUnValidName(root, name, path, errors)){
				errors.addErrorCode(INVALID_PARAM);
				return errors;
			}
		}
		return errors;
	}
	
	private WebErrors validateDeleteFreeFile(String root, String path,String[] names,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(names==null||names.length<=0){
			errors.addErrorCode("error.findnofreefile");
		}
		for(String name:names){
			if(isUnValidName(root, name, path, errors)){
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
	@Autowired
	private CmsFileMng fileMng;
	private CmsResourceMng resourceMng;
	@Autowired
	private RealPathResolver realPathResolver;

	@Autowired
	public void setResourceMng(CmsResourceMng resourceMng) {
		this.resourceMng = resourceMng;
	}
}