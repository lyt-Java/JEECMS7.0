package com.jeecms.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsUserMenuDao;
import com.jeecms.cms.entity.assist.CmsUserMenu;
import com.jeecms.cms.manager.assist.CmsUserMenuMng;

@Service
@Transactional
public class CmsUserMenuMngImpl implements CmsUserMenuMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer userId,int pageNo, int pageSize) {
		Pagination page = dao.getPage(userId,pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsUserMenu> getList(Integer userId,int count){
		return dao.getList(userId,count);
	}

	@Transactional(readOnly = true)
	public CmsUserMenu findById(Integer id) {
		CmsUserMenu entity = dao.findById(id);
		return entity;
	}

	public CmsUserMenu save(CmsUserMenu bean) {
		dao.save(bean);
		return bean;
	}

	public CmsUserMenu update(CmsUserMenu bean) {
		Updater<CmsUserMenu> updater = new Updater<CmsUserMenu>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsUserMenu deleteById(Integer id) {
		CmsUserMenu bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsUserMenu[] deleteByIds(Integer[] ids) {
		CmsUserMenu[] beans = new CmsUserMenu[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsUserMenuDao dao;

	@Autowired
	public void setDao(CmsUserMenuDao dao) {
		this.dao = dao;
	}
}