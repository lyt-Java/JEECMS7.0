package com.jeecms.core.manager.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.dao.CmsGroupDao;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.manager.CmsGroupMng;

@Service
@Transactional
public class CmsGroupMngImpl implements CmsGroupMng {
	@Transactional(readOnly = true)
	public List<CmsGroup> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsGroup findById(Integer id) {
		CmsGroup entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsGroup getRegDef() {
		return dao.getRegDef();
	}

	public void updateRegDef(Integer regDefId) {
		if (regDefId != null) {
			for (CmsGroup g : getList()) {
				if (g.getId().equals(regDefId)) {
					g.setRegDef(true);
				} else {
					g.setRegDef(false);
				}
			}
		}
	}

	public CmsGroup save(CmsGroup bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}
	
	public CmsGroup save(CmsGroup bean,Integer[] viewChannelIdss, Integer[] contriChannelIds){
		bean.init();
		dao.save(bean);
		Channel c;
		if (viewChannelIdss != null && viewChannelIdss.length > 0) {
			for (Integer cid : viewChannelIdss) {
				c = channelMng.findById(cid);
				bean.addToViewChannels(c);
			}
		}
		if (contriChannelIds != null && contriChannelIds.length > 0) {
			for (Integer cid : contriChannelIds) {
				c = channelMng.findById(cid);
				bean.addToContriChannels(c);
			}
		}
		return bean;
	}

	public CmsGroup update(CmsGroup bean) {
		Updater<CmsGroup> updater = new Updater<CmsGroup>(bean);
		CmsGroup entity = dao.updateByUpdater(updater);
		return entity;
	}
	
	public CmsGroup update(CmsGroup bean,Integer[] viewChannelIds, Integer[] contriChannelIds){
		Updater<CmsGroup> updater = new Updater<CmsGroup>(bean);
		bean = dao.updateByUpdater(updater);
		// 更新浏览栏目权限
		Set<Channel> viewChannels = bean.getViewChannels();
		// 清除
		for (Channel channel : viewChannels) {
			channel.getViewGroups().remove(bean);
		}
		bean.getViewChannels().clear();
		Set<Channel>contriChannels=bean.getContriChannels();
		//清除
		for(Channel channel:contriChannels){
			channel.getContriGroups().remove(bean);
		}
		bean.getContriChannels().clear();
		Channel c;
		if (viewChannelIds != null && viewChannelIds.length > 0) {
			for (Integer cid : viewChannelIds) {
				c = channelMng.findById(cid);
				bean.addToViewChannels(c);
			}
		}
		if (contriChannelIds != null && contriChannelIds.length > 0) {
			for (Integer cid : contriChannelIds) {
				c = channelMng.findById(cid);
				bean.addToContriChannels(c);
			}
		}
		return bean;
	}

	public CmsGroup deleteById(Integer id) {
		CmsGroup bean =dao.findById(id);
		//清除组权限
		for(Channel c:bean.getViewChannels()){
			c.removeViewGroup(bean);
		}
		for(Channel c:bean.getContriChannels()){
			c.removeContriGroup(bean);
		}
		dao.deleteById(id);
		return bean;
	}

	public CmsGroup[] deleteByIds(Integer[] ids) {
		CmsGroup[] beans = new CmsGroup[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public CmsGroup[] updatePriority(Integer[] ids, Integer[] priority) {
		int len = ids.length;
		CmsGroup[] beans = new CmsGroup[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
		}
		return beans;
	}

	private CmsGroupDao dao;
	@Autowired
	private ChannelMng channelMng;

	@Autowired
	public void setDao(CmsGroupDao dao) {
		this.dao = dao;
	}
}