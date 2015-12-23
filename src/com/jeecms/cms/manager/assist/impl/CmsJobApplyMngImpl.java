package com.jeecms.cms.manager.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsJobApplyDao;
import com.jeecms.cms.entity.assist.CmsJobApply;
import com.jeecms.cms.manager.assist.CmsJobApplyMng;

@Service
@Transactional
public class CmsJobApplyMngImpl implements CmsJobApplyMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer userId,Integer contentId,Integer siteId,boolean cacheable,int pageNo, int pageSize) {
		Pagination page = dao.getPage(userId,contentId,siteId,cacheable,pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public CmsJobApply findById(Integer id) {
		CmsJobApply entity = dao.findById(id);
		return entity;
	}

	public CmsJobApply save(CmsJobApply bean) {
		dao.save(bean);
		return bean;
	}

	public CmsJobApply update(CmsJobApply bean) {
		Updater<CmsJobApply> updater = new Updater<CmsJobApply>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsJobApply deleteById(Integer id) {
		CmsJobApply bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsJobApply[] deleteByIds(Integer[] ids) {
		CmsJobApply[] beans = new CmsJobApply[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsJobApplyDao dao;

	@Autowired
	public void setDao(CmsJobApplyDao dao) {
		this.dao = dao;
	}
}