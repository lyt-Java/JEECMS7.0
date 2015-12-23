package com.jeecms.cms.action.admin;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeecms.cms.service.ImageSvc;
import com.jeecms.common.image.ImageScale;
import com.jeecms.common.image.ImageUtils;
import com.jeecms.common.ueditor.LocalizedMessages;
import com.jeecms.common.ueditor.ResourceType;
import com.jeecms.common.ueditor.Utils;
import com.jeecms.common.upload.FileRepository;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.Ftp;
import com.jeecms.core.entity.MarkConfig;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.manager.DbFileMng;
import com.jeecms.core.web.util.CmsUtils;

/**
 * ueditor服务器端接口
 * 
 * 为了更好、更灵活的处理ueditor上传，重新实现ueditor服务器端接口。
 */
@Controller
public class UeditorAct {

	private static final Logger log = LoggerFactory.getLogger(UeditorAct.class);

	// 状态
	private static final String STATE = "state";
	// 上传成功
	private static final String SUCCESS = "SUCCESS";
	// URL
	private static final String URL = "url";
	// 原URL
	private static final String SRC_URL = "srcUrl";
	// 文件原名
	private static final String ORIGINAL = "original";
	// TITLE
	private static final String TITLE = "title";
	// 文件类型
	private static final String FILETYPE = "fileType";
	// 在线图片管理图片分隔符
	private static final String UE_SEPARATE_UE="ue_separate_ue";
	//提示信息
	private static final String TIP = "tip";
	@RequiresPermissions("ueditor:upload")
	@RequestMapping(value = "/ueditor/upload.do",method = RequestMethod.POST)
	public void upload(
			@RequestParam(value = "Type", required = false) String typeStr,
			Boolean mark,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		responseInit(response);
		if (Utils.isEmpty(typeStr)) {
			typeStr = "File";
		}
		if(mark==null){
			mark=false;
		}
		JSONObject json = new JSONObject();
		JSONObject ob = validateUpload(request, typeStr);
		if (ob == null) {
			json = doUpload(request, typeStr, mark);
		} else {
			json = ob;
		}
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("ueditor:getRemoteImage")
	@RequestMapping(value = "/ueditor/getRemoteImage.do")
	public void getRemoteImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = request.getParameter("upfile");
		JSONObject json = new JSONObject();
		CmsSite site=CmsUtils.getSite(request);
		String[] arr = url.split(UE_SEPARATE_UE);
		String[] outSrc = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			outSrc[i]=imgSvc.crawlImg(arr[i], site);
		}
		String outstr = "";
		for (int i = 0; i < outSrc.length; i++) {
			outstr += outSrc[i] + UE_SEPARATE_UE;
		}
		outstr = outstr.substring(0, outstr.lastIndexOf(UE_SEPARATE_UE));
		json.put(URL, outstr);
		json.put(SRC_URL, url);
		json.put(TIP, LocalizedMessages.getRemoteImageSuccessSpecified(request));
		ResponseUtils.renderJson(response, json.toString());
	}
	
	/**
	 * 在线图片管理（选择最近或站点图片）
	 * @param picNum 图片显示数量（数量不宜太大）
	 * @param insite 站点内图片（默认选择最近月内图片）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("ueditor:imageManager")
	@RequestMapping(value = "/ueditor/imageManager.do")
	public void imageManager(Integer picNum,Boolean insite,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CmsSite site=CmsUtils.getSite(request);
		String uploadPath=site.getUploadPath();
		if(insite==null){
			insite=false;
		}
		String path;
		if(insite){
			path = uploadPath;
		}else{
			path = uploadPath+"/"+getSubFolderNameDirectory(realPathResolver.get(uploadPath));
		}
		String realpath = realPathResolver.get(path);
		String imgStr ="";
		StringBuffer imgStrBuff=new StringBuffer();
		List<File> files = getFiles(realpath,new ArrayList<File>());
		if(picNum==null){
			picNum=10;
		}
		if(picNum>files.size()){
			picNum=files.size();
		}
		for(int i=0;i<picNum;i++){
			File file=files.get(i);
			imgStrBuff.append(file.getPath().replace(realPathResolver.get(""),site.getContextPath())+UE_SEPARATE_UE);
		}
		imgStr=imgStrBuff.toString();
		if(StringUtils.isNotBlank(imgStr)){
			 imgStr = imgStr.substring(0,imgStr.lastIndexOf(UE_SEPARATE_UE)).replace(File.separator, "/").trim();
		}
		response.getWriter().print(imgStr);
	}

	//getmovie方法完全参考ueditor提供
	@RequiresPermissions("ueditor:getmovie")
	@RequestMapping(value = "/ueditor/getmovie.do", method = RequestMethod.POST)
	public void getMovie(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer readOneLineBuff = new StringBuffer();
		String content = "";
		String searchkey = request.getParameter("searchKey");
		String videotype = request.getParameter("videoType");
		try {
			searchkey = URLEncoder.encode(searchkey, "utf-8");
			URL url = new URL(
					"http://api.tudou.com/v3/gw?method=item.search&appKey=myKey&format=json&kw="
							+ searchkey + "&pageNo=1&pageSize=20&channelId="
							+ videotype + "&inDays=7&media=v&sort=s");
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				readOneLineBuff.append(line);
			}
			content = readOneLineBuff.toString();
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		ResponseUtils.renderText(response, content);
	}

	private JSONObject doUpload(HttpServletRequest request, String typeStr,Boolean mark) throws Exception {
		ResourceType type = ResourceType.getDefaultResourceType(typeStr);
		JSONObject result = new JSONObject();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// We upload just one file at the same time
			MultipartFile uplFile = multipartRequest.getFileMap().entrySet()
					.iterator().next().getValue();
			// Some browsers transfer the entire source path not just the
			// filename
			String filename = FilenameUtils.getName(uplFile
					.getOriginalFilename());
			log.debug("Parameter NewFile: {}", filename);
			String ext = FilenameUtils.getExtension(filename);
			if (type.isDeniedExtension(ext)) {
				result.put(STATE, LocalizedMessages
						.getInvalidFileTypeSpecified(request));
				return result;
			}
			if (type.equals(ResourceType.IMAGE)
					&& !ImageUtils.isImage(uplFile.getInputStream())) {
				result.put(STATE, LocalizedMessages
						.getInvalidFileTypeSpecified(request));
				return result;
			}
			String fileUrl;
			CmsSite site = CmsUtils.getSite(request);
			CmsUser user = CmsUtils.getUser(request);
			MarkConfig conf = site.getConfig().getMarkConfig();
			if (mark == null) {
				mark = conf.getOn();
			}
			boolean isImg = type.equals(ResourceType.IMAGE);
			if (site.getConfig().getUploadToDb()) {
				if (mark && isImg) {
					File tempFile = mark(uplFile, conf);
					fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext,
							new FileInputStream(tempFile));
					tempFile.delete();
				} else {
					fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext,
							uplFile.getInputStream());
				}
				// 加上访问地址
				String dbFilePath = site.getConfig().getDbFileUri();
				fileUrl = request.getContextPath() + dbFilePath + fileUrl;
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				if (mark && isImg) {
					File tempFile = mark(uplFile, conf);
					fileUrl = ftp.storeByExt(site.getUploadPath(), ext,
							new FileInputStream(tempFile));
					tempFile.delete();
				} else {
					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, uplFile
							.getInputStream());
				}
				// 加上url前缀
				fileUrl = ftp.getUrl() + fileUrl;
			} else {
				if (mark && isImg) {
					File tempFile = mark(uplFile, conf);
					fileUrl = fileRepository.storeByExt(site.getUploadPath(),
							ext, tempFile);
					tempFile.delete();
				} else {
					fileUrl = fileRepository.storeByExt(site.getUploadPath(),
							ext, uplFile);
				}
				// 加上部署路径
				fileUrl = request.getContextPath() + fileUrl;
			}
			cmsUserMng.updateUploadSize(user.getId(), Integer.parseInt(String
					.valueOf(uplFile.getSize() / 1024)));
			//需要给页面参数(参考ueditor的/jsp/imageUp.jsp)
			result.put(STATE, SUCCESS);
			result.put(URL, fileUrl);
			result.put(ORIGINAL, filename);
			result.put(TITLE, filename);
			result.put(FILETYPE, "." + ext);
			return result;
		} catch (IOException e) {
			result.put(STATE, LocalizedMessages
					.getFileUploadWriteError(request));
			return result;
		}
	}


	private void responseInit(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
	}

	private JSONObject validateUpload(HttpServletRequest request, String typeStr)
			throws JSONException {
		// TODO 是否允许上传
		JSONObject result = new JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uplFile = multipartRequest.getFileMap().entrySet()
				.iterator().next().getValue();
		String filename = FilenameUtils.getName(uplFile.getOriginalFilename());
		int fileSize = (int) (uplFile.getSize() / 1024);
		String ext = FilenameUtils.getExtension(filename).toLowerCase(
				Locale.ENGLISH);
		CmsUser user = CmsUtils.getUser(request);
		// 非允许的后缀
		if (!user.isAllowSuffix(ext)) {
			result.put(STATE, LocalizedMessages
					.getInvalidFileSuffixSpecified(request));
			return result;
		}
		// 超过附件大小限制
		if (!user.isAllowMaxFile((int) (uplFile.getSize() / 1024))) {
			result.put(STATE, LocalizedMessages.getInvalidFileToLargeSpecified(
					request, filename, user.getGroup().getAllowMaxFile()));
			return result;
		}
		// 超过每日上传限制
		if (!user.isAllowPerDay(fileSize)) {
			long laveSize = user.getGroup().getAllowPerDay()
					- user.getUploadSize();
			if (laveSize < 0) {
				laveSize = 0;
			}
			result.put(STATE, LocalizedMessages
					.getInvalidUploadDailyLimitSpecified(request, String
							.valueOf(laveSize)));
			return result;
		}
		if (!ResourceType.isValidType(typeStr)) {
			result.put(STATE, LocalizedMessages
					.getInvalidResouceTypeSpecified(request));
			return result;
		}
		return null;
	}
	
	private  String getSubFolderNameDirectory(String directory) {
		File realFile = new File(directory);
		if(realFile.isDirectory()){
			File[] subfiles = realFile.listFiles();
			if(subfiles!=null&&subfiles.length>0){
				return subfiles[subfiles.length-1].getName();
			}else{
				return directory;
			}
		}else{
			return directory;
		}
	}
	
	private List<File> getFiles(String realpath, List<File> files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for(File file :subfiles ){
				if(file.isDirectory()){
					getFiles(file.getAbsolutePath(),files);
				}else{
					if(ImageUtils.isValidImageExt( FilenameUtils.getExtension(file.getName()))){
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	private File mark(MultipartFile file, MarkConfig conf) throws Exception {
		String path = System.getProperty("java.io.tmpdir");
		File tempFile = new File(path, String.valueOf(System
				.currentTimeMillis()));
		file.transferTo(tempFile);
		boolean imgMark = !StringUtils.isBlank(conf.getImagePath());
		if (imgMark) {
			File markImg = new File(realPathResolver.get(conf.getImagePath()));
			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
					.getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
					.getOffsetY(), markImg);
		} else {
			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf
					.getMinHeight(), conf.getPos(), conf.getOffsetX(), conf
					.getOffsetY(), conf.getContent(), Color.decode(conf
					.getColor()), conf.getSize(), conf.getAlpha());
		}
		return tempFile;
	}

	private FileRepository fileRepository;
	private DbFileMng dbFileMng;
	private ImageScale imageScale;
	private RealPathResolver realPathResolver;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private ImageSvc imgSvc;

	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Autowired
	public void setDbFileMng(DbFileMng dbFileMng) {
		this.dbFileMng = dbFileMng;
	}

	@Autowired
	public void setImageScale(ImageScale imageScale) {
		this.imageScale = imageScale;
	}

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}

}
