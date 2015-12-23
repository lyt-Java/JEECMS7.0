package com.jeecms.cms.manager.assist.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.assist.CmsVoteTopicDao;
import com.jeecms.cms.entity.assist.CmsVoteItem;
import com.jeecms.cms.entity.assist.CmsVoteReply;
import com.jeecms.cms.entity.assist.CmsVoteSubTopic;
import com.jeecms.cms.entity.assist.CmsVoteTopic;
import com.jeecms.cms.manager.assist.CmsVoteRecordMng;
import com.jeecms.cms.manager.assist.CmsVoteReplyMng;
import com.jeecms.cms.manager.assist.CmsVoteSubTopicMng;
import com.jeecms.cms.manager.assist.CmsVoteTopicMng;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.entity.CmsUser;

@Service
@Transactional
public class CmsVoteTopicMngImpl implements CmsVoteTopicMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public  List<CmsVoteTopic> getList(Boolean def,Integer siteId, int count){
		return dao.getList(def,siteId,count);
	}

	@Transactional(readOnly = true)
	public CmsVoteTopic findById(Integer id) {
		CmsVoteTopic entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsVoteTopic getDefTopic(Integer siteId) {
		return dao.getDefTopic(siteId);
	}

	public CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics) {
		int totalCount = 0;
		bean.setTotalCount(totalCount);
		bean.init();
		dao.save(bean);
		cmsVoteSubtopicMng.save(bean,subTopics);
		return bean;
	}

	public CmsVoteTopic update(CmsVoteTopic bean) {
		Updater<CmsVoteTopic> updater = new Updater<CmsVoteTopic>(bean);
		updater.include(CmsVoteTopic.PROP_START_TIME);
		updater.include(CmsVoteTopic.PROP_END_TIME);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsVoteTopic vote(Integer topicId,Integer[]subIds,
			List<Integer[]> itemIds,String[]replys, CmsUser user,
			String ip, String cookie) {
		CmsVoteTopic topic = findById(topicId);
		Set<CmsVoteItem> items = topic.getItems();
		List<Integer>itemIdsList=new ArrayList<Integer>();
		for(Integer[]itemId:itemIds){
			if(itemId!=null&&itemId.length>0){
				for(Integer id:itemId){
					itemIdsList.add(id);
				}
			}
		}
		Integer[]ids=new Integer[itemIdsList.size()];
		ids=(Integer[]) itemIdsList.toArray(ids);
		for (CmsVoteItem item : items) {
			if (ArrayUtils.contains(ids, item.getId())) {
				item.setVoteCount(item.getVoteCount() + 1);
			}
		}
		String reply;
		if(replys!=null&&replys.length>0){
			for(int i=0;i<replys.length;i++){
				reply=replys[i];
				if(StringUtils.isNotBlank(reply)){
					CmsVoteReply voteReply=new CmsVoteReply();
					voteReply.setReply(reply);
					voteReply.setSubTopic(cmsVoteSubtopicMng.findById(subIds[i]));
					cmsVoteReplyMng.save(voteReply);
				}
			}
		}
		//每个选项计一票改成每次提及整个调查计一票
		topic.setTotalCount(topic.getTotalCount()+1);
		// 如果需要限制投票，则需保存投票记录。
		if ((topic.getRepeateHour() == null || topic.getRepeateHour() > 0)
				&& (topic.getRestrictMember() || topic.getRestrictIp() || topic
						.getRestrictCookie())) {
			cmsVoteRecordMng.save(topic, user, ip, cookie);
		}
		return topic;
	}

	public CmsVoteTopic deleteById(Integer id) {
		CmsVoteTopic bean = dao.deleteById(id);
		return bean;
	}

	public CmsVoteTopic[] deleteByIds(Integer[] ids) {
		CmsVoteTopic[] beans = new CmsVoteTopic[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsVoteSubTopicMng cmsVoteSubtopicMng;
	private CmsVoteReplyMng cmsVoteReplyMng;
	private CmsVoteRecordMng cmsVoteRecordMng;
	private CmsVoteTopicDao dao;

	@Autowired
	public void setCmsVoteSubTopicMng(CmsVoteSubTopicMng cmsVoteSubTopicMng) {
		this.cmsVoteSubtopicMng = cmsVoteSubTopicMng;
	}
	
	@Autowired
	public void setCmsVoteReplyMng(CmsVoteReplyMng cmsVoteReplyMng) {
		this.cmsVoteReplyMng = cmsVoteReplyMng;
	}

	@Autowired
	public void setCmsVoteRecordMng(CmsVoteRecordMng cmsVoteRecordMng) {
		this.cmsVoteRecordMng = cmsVoteRecordMng;
	}

	@Autowired
	public void setDao(CmsVoteTopicDao dao) {
		this.dao = dao;
	}

}