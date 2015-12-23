package com.jeecms.core.manager.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.dao.CmsRoleDao;
import com.jeecms.core.entity.CmsRole;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsRoleMng;
import com.jeecms.core.manager.CmsUserMng;

@Service
@Transactional
public class CmsRoleMngImpl implements CmsRoleMng {
	@Transactional(readOnly = true)
	public List<CmsRole> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsRole findById(Integer id) {
		CmsRole entity = dao.findById(id);
		return entity;
	}

	public CmsRole save(CmsRole bean, Set<String> perms) {
		bean.setPerms(perms);
		dao.save(bean);
		return bean;
	}

	public CmsRole update(CmsRole bean, Set<String> perms) {
		Updater<CmsRole> updater = new Updater<CmsRole>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setPerms(perms);
		return bean;
	}

	public CmsRole deleteById(Integer id) {
		CmsRole bean = dao.deleteById(id);
		return bean;
	}

	public CmsRole[] deleteByIds(Integer[] ids) {
		CmsRole[] beans = new CmsRole[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	public void deleteMembers(CmsRole role, Integer[] userIds) {
		Updater<CmsRole> updater = new Updater<CmsRole>(role);
		role = dao.updateByUpdater(updater);
		if (userIds != null) {
			CmsUser user;
			for (Integer uid : userIds) {
				user = userMng.findById(uid);
				role.delFromUsers(user);
			}
		}
	}

	private CmsRoleDao dao;
	@Autowired
	private CmsUserMng userMng;

	@Autowired
	public void setDao(CmsRoleDao dao) {
		this.dao = dao;
	}
}