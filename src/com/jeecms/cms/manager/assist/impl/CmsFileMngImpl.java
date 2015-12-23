package com.jeecms.cms.manager.assist.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.assist.CmsFileDao;
import com.jeecms.cms.entity.assist.CmsFile;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.manager.assist.CmsFileMng;
import com.jeecms.common.hibernate3.Updater;

@Service
@Transactional
public class CmsFileMngImpl implements CmsFileMng {
	
	public CmsFile deleteById(Integer id) {
		return dao.deleteById(id);
	}
	
	public CmsFile deleteByPath(String path){
		return dao.deleteByPath(path);
	}
	
	public void deleteByContentId(Integer contentId){
		 dao.deleteByContentId(contentId);
	}


	public CmsFile findById(Integer id) {
		return dao.findById(id);
	}

	public CmsFile findByPath(String path) {
		return dao.findByPath(path);
	}

	public List<CmsFile> getList(Boolean valid) {
		return dao.getList(valid);
	}

	public CmsFile save(CmsFile bean) {
		return dao.save(bean);
	}

	public void saveFileByPath(String filepath, String name, Boolean valid) {
		CmsFile attFile=new CmsFile();
		attFile.setFilePath(filepath);
		attFile.setFileName(name);
		attFile.setFileIsvalid(valid);
		save(attFile);
	}

	public void updateFileByPath(String path, Boolean valid, Content c) {
		CmsFile file;
		file=findByPath(path);
		if(file!=null){
			file.setContent(c);
			file.setFileIsvalid(valid);
			update(file);
		}
	}
	public void updateFileByPaths(String[] attachmentPaths,String[]picPaths,String mediaPath,
			String titleImg, String typeImg,String contentImg,Boolean valid,Content c){
		//处理附件有效性
		if(attachmentPaths!=null){
			for(String att:attachmentPaths){
				updateFileByPath(att, valid, c);
			}
		}
		//处理图片集
		if(picPaths!=null){
			for(String pic:picPaths){
				updateFileByPath(pic, valid, c);
			}
		}
		//处理多媒体
		if(StringUtils.isNotBlank(mediaPath)){
			updateFileByPath(mediaPath, valid, c);
		}
		//标题图
		if(StringUtils.isNotBlank(titleImg)){
			updateFileByPath(titleImg, valid, c);
		}
		//类型图
		if(StringUtils.isNotBlank(typeImg)){
			updateFileByPath(typeImg, valid, c);
		}
		//内容图
		if(StringUtils.isNotBlank(contentImg)){
			updateFileByPath(contentImg, valid, c);
		}
	}

	public CmsFile update(CmsFile bean) {
		Updater<CmsFile> updater = new Updater<CmsFile>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	private CmsFileDao dao;

	@Autowired
	public void setDao(CmsFileDao dao) {
		this.dao = dao;
	}

}