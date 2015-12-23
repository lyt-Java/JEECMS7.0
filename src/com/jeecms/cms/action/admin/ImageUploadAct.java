package com.jeecms.cms.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.cms.action.CommonUpload;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.Ftp;
import com.jeecms.core.entity.MarkConfig;
import com.jeecms.core.web.WebCoreErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class ImageUploadAct extends CommonUpload {
	private static final Logger log = LoggerFactory
			.getLogger(ImageUploadAct.class);
	/**
	 * 结果页
	 */
	private static final String RESULT_PAGE = "/common/iframe_upload";
	/**
	 * 错误信息参数
	 */
	public static final String ERROR = "error";

	@RequiresPermissions("common:o_upload_image")
	@RequestMapping("/common/o_upload_image.do")
	public String execute(
			String filename,
			Integer uploadNum,
			Boolean mark,
			@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateImage(file, request);
		if (errors.hasErrors()) {
			model.addAttribute(ERROR, errors.getErrors().get(0));
			return RESULT_PAGE;
		}
		CmsSite site = CmsUtils.getSite(request);
		MarkConfig conf = site.getConfig().getMarkConfig();
		if (mark == null) {
			mark = conf.getOn();
		}
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		try {
			String fileUrl;
			if (site.getConfig().getUploadToDb()) {
				String dbFilePath = site.getConfig().getDbFileUri();
				if (!StringUtils.isBlank(filename)) {
					filename = filename.substring(dbFilePath.length());
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = dbFileMng.storeByFilename(filename,
								new FileInputStream(tempFile));
						tempFile.delete();
					} else {
						fileUrl = dbFileMng.storeByFilename(filename, file
								.getInputStream());
					}
				} else {
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = dbFileMng.storeByExt(site.getUploadPath(),
								ext, new FileInputStream(tempFile));
						tempFile.delete();
					} else {
						fileUrl = dbFileMng.storeByExt(site.getUploadPath(),
								ext, file.getInputStream());
					}
					// 加上访问地址
					fileUrl = request.getContextPath() + dbFilePath + fileUrl;
				}
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				if (!StringUtils.isBlank(filename)) {
					filename = filename.substring(ftpUrl.length());
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = ftp.storeByFilename(filename,
								new FileInputStream(tempFile));
						tempFile.delete();
					} else {
						fileUrl = ftp.storeByFilename(filename, file
								.getInputStream());
					}
				} else {
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = ftp.storeByExt(site.getUploadPath(), ext,
								new FileInputStream(tempFile));
						tempFile.delete();
					} else {
						fileUrl = ftp.storeByExt(site.getUploadPath(), ext,
								file.getInputStream());
					}
					// 加上url前缀
					fileUrl = ftpUrl + fileUrl;
				}
			} else {
				String ctx = request.getContextPath();
				if (!StringUtils.isBlank(filename)) {
					filename = filename.substring(ctx.length());
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = fileRepository.storeByFilename(filename,
								tempFile);
						tempFile.delete();
					} else {
						fileUrl = fileRepository
								.storeByFilename(filename, file);
					}
				} else {
					if (mark) {
						File tempFile = mark(file, conf);
						fileUrl = fileRepository.storeByExt(site
								.getUploadPath(), ext, tempFile);
						tempFile.delete();
					} else {
						fileUrl = fileRepository.storeByExt(site
								.getUploadPath(), ext, file);
					}
					// 加上部署路径
					fileUrl = ctx + fileUrl;
				}
			}
			fileMng.saveFileByPath(fileUrl, origName, false);
			model.addAttribute("uploadPath", fileUrl);
			model.addAttribute("uploadNum", uploadNum);
		} catch (IllegalStateException e) {
			model.addAttribute(ERROR, e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute(ERROR, e.getMessage());
			log.error("upload file error!", e);
		} catch (Exception e) {
			model.addAttribute(ERROR, e.getMessage());
			log.error("upload file error!", e);
		}
		cmsUserMng.updateUploadSize(CmsUtils.getUserId(request), Integer.parseInt(String.valueOf(file.getSize()/1024)));
		return RESULT_PAGE;
	}

}