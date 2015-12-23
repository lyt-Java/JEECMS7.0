package com.jeecms.cms.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.cms.action.CommonUpload;

/**
 * @author Tom
 * 批量上传图片和附件
 */
@Controller
public class SwfUploadAct extends CommonUpload {
	/**
	 * 错误信息参数
	 */
	public static final String ERROR = "error";

	@RequiresPermissions("common:o_swfPicsUpload")
	@RequestMapping(value = "/common/o_swfPicsUpload.do", method = RequestMethod.POST)
		public void swfPicsUpload(
			String root,
			Integer uploadNum,
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception{
		super.swfPicsUpload(root, uploadNum, file, request, response, model);
	}
	
	@RequiresPermissions("common:o_swfAttachsUpload")
	@RequestMapping(value = "/common/o_swfAttachsUpload.do", method = RequestMethod.POST)
	public void swfAttachsUpload(
			String root,
			Integer uploadNum,
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception{
		super.swfAttachsUpload(root, uploadNum, file, request, response, model);
	}
	
}
