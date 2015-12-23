package com.jeecms.core.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.dao.CmsDictionaryDao;
import com.jeecms.core.entity.CmsDictionary;
import com.jeecms.core.manager.CmsDictionaryMng;

@Service
@Transactional
public class CmsDictionaryMngImpl implements CmsDictionaryMng {
	
	public void init(){
	}
	@Transactional(readOnly = true)
	public Pagination getPage(String queryType,int pageNo, int pageSize) {
		Pagination page = dao.getPage(queryType,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsDictionary> getList(String queryType){
		return dao.getList(queryType);
	}
	
	@Transactional(readOnly = true)
	public List<String> getTypeList(){
		return dao.getTypeList();
	}

	@Transactional(readOnly = true)
	public CmsDictionary findById(Integer id) {
		CmsDictionary entity = dao.findById(id);
		return entity;
	}
	
	@Transactional(readOnly = true)
	public CmsDictionary findByValue(String type,String value){
		CmsDictionary entity = dao.findByValue(type,value);
		return entity;
	}

	public CmsDictionary save(CmsDictionary bean) {
		dao.save(bean);
		return bean;
	}

	public CmsDictionary update(CmsDictionary bean) {
		Updater<CmsDictionary> updater = new Updater<CmsDictionary>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsDictionary deleteById(Integer id) {
		CmsDictionary bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsDictionary[] deleteByIds(Integer[] ids) {
		CmsDictionary[] beans = new CmsDictionary[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public boolean dicDeplicateValue(String value, String type) {
		return dao.countByValue(value,type) > 0;
	}

	private CmsDictionaryDao dao;

	@Autowired
	public void setDao(CmsDictionaryDao dao) {
		this.dao = dao;
	}
}