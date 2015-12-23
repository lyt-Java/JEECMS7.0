package com.jeecms.cms.statistic.workload;


import java.util.Date;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.jeecms.common.hibernate3.HibernateSimpleDao;

@Repository
public class CmsWorkLoadStatisticDaoImpl extends HibernateSimpleDao implements CmsWorkLoadStatisticDao {
	public Long statistic(Integer channelId,
			Integer reviewerId, Integer authorId, Date beginDate, Date endDate,CmsWorkLoadStatisticDateKind dateKind) {
		String hql="select count(*) from Content bean";
		if (reviewerId!=null) {
			hql+=" join bean.contentCheckSet check";
		}
		if (channelId != null) {
			hql+=" join bean.channel channel,Channel parent";
			hql+=" where channel.lft between parent.lft and parent.rgt";
			hql+=" and channel.site.id=parent.site.id";
			hql+=" and parent.id=:parentId";
		}else {
			hql+=" where 1=1";
		}
		if (reviewerId!=null) {
			hql+=" and check.reviewer.id=:reviewerId";
		}
		if(authorId!=null){
			hql+=" and bean.user.id=:authorId";
		}
		if(dateKind==CmsWorkLoadStatisticDateKind.release){
			if(beginDate!=null){
				hql+=" and  bean.contentExt.releaseDate>=:beginDate";
			}
			if(endDate!=null){
				hql+=" and  bean.contentExt.releaseDate<=:endDate";
			}
		}else{
			if(beginDate!=null){
				hql+=" and  check.checkDate>=:beginDate";
			}
			if(endDate!=null){
				hql+=" and  check.checkDate<=:endDate";
			}
		}
		Query query = getSession().createQuery(hql);
		if(channelId!=null){
			query.setParameter("parentId", channelId);
		}
		if(reviewerId!=null){
			query.setParameter("reviewerId", reviewerId);
		}
		if(authorId!=null){
			query.setParameter("authorId", authorId);
		}
		if(beginDate!=null){
			query.setParameter("beginDate", beginDate);
		}
		if(endDate!=null){
			query.setParameter("endDate", endDate);
		}
		return (Long) query.uniqueResult();
	}

}
