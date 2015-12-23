package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsVoteSubTopicDao;
import com.jeecms.cms.entity.assist.CmsVoteSubTopic;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsVoteSubTopicDaoImpl extends
		HibernateBaseDao<CmsVoteSubTopic, Integer> implements CmsVoteSubTopicDao {
	
	@SuppressWarnings("unchecked")
	public List<CmsVoteSubTopic> findByVoteTopic(Integer voteTopicId){
		String hql="select bean from CmsVoteSubTopic bean";
		Finder finder=Finder.create(hql);
		if(voteTopicId!=null){
			finder.append(" where bean.voteTopic.id=:voteTopicId").setParam("voteTopicId", voteTopicId);
		}
		finder.append(" order by  bean.priority asc,bean.id desc");
		return find(finder);
	}
	
	public CmsVoteSubTopic findById(Integer id) {
		CmsVoteSubTopic entity = get(id);
		return entity;
	}

	public CmsVoteSubTopic save(CmsVoteSubTopic bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsVoteSubTopic deleteById(Integer id) {
		CmsVoteSubTopic entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteSubTopic> getEntityClass() {
		return CmsVoteSubTopic.class;
	}
}