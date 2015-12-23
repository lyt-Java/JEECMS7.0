package com.jeecms.core.action.front;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jeecms.common.ueditor.LocalizedMessages;
import com.jeecms.common.upload.UploadUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import static com.jeecms.cms.Constants.SNAP_PATH;

@SuppressWarnings("serial")
public class SnapScreenServlet extends HttpServlet {
	// 状态
	private static final String STATE = "state";
	// URL
	private static final String URL = "url";
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		responseInit(response);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		JSONObject result=new JSONObject();
		try {
			if (!isMultipart) {
				result.put(STATE, LocalizedMessages.getInvalidUploadMultipartSpecified(request));
			}
			InputStream inputStream=request.getInputStream();
			if (inputStream == null) {
				result.put(STATE, LocalizedMessages.getInvalidUploadInputStreamSpecified(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String snapImageName=getSnapImageName(request, "jpg");
			FileOutputStream fos = new FileOutputStream(getSnapImagePath(snapImageName));
			BufferedInputStream bis = new BufferedInputStream(getInputStreamFromRequest(request));
			IOUtils.copy(bis, fos);
			result.put(STATE, "SUCCESS");
			result.put(URL, request.getContextPath()+snapImageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseUtils.renderJson(response, result.toString());
	}

	public void init() throws ServletException {
		WebApplicationContext appCtx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		realPathResolver = BeanFactoryUtils.beanOfTypeIncludingAncestors(appCtx,RealPathResolver.class);
	}
	
	//获取截图输入流
	private InputStream getInputStreamFromRequest(HttpServletRequest request) {
		InputStream inputStream=null;
		DiskFileItemFactory dff = new DiskFileItemFactory();
		try {
			ServletFileUpload sfu = new ServletFileUpload(dff);
			FileItemIterator fii = sfu.getItemIterator(request);
			while (fii.hasNext()) {
				FileItemStream item = fii.next();
				// 普通参数存储
				if (!item.isFormField()) {
					// 只保留一个
					if (inputStream == null) {
						inputStream = item.openStream();
						return inputStream;
					}
				} 
			}
		} catch (Exception e) {
		}
		return inputStream;
	}
	
	private String getSnapImageName(HttpServletRequest request,String ext){
		String filename = UploadUtils.generateFilename(SNAP_PATH, ext);
		return filename;
	}
	
	private String getSnapImagePath(String filename) {
		File dest = new File(realPathResolver.get(filename));
		UploadUtils.checkDirAndCreate(dest.getParentFile());
		dest = UploadUtils.getUniqueFile(dest);
		return dest.getAbsolutePath();
	}

	private void responseInit(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
	}
	private RealPathResolver realPathResolver;
}
