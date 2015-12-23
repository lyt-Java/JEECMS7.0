package com.jeecms.cms.service;

import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.Ftp;

/**
 * @author Tom
 */
public interface ImageSvc {
	/**
	 * 抓取远程图片返回本地地址
	 * @param imgUrl 远程图片URL
	 * @return
	 */
	public String crawlImg(String imgUrl,CmsSite site);
	
	public String crawlImg(String imgUrl,String ctx,boolean uploadToDb,String dbFileUri,Ftp ftp,String uploadPath);
}
