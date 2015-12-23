package com.jeecms.cms.statistic;

import static com.jeecms.cms.statistic.CmsStatistic.SITEID;
import static com.jeecms.cms.statistic.CmsStatistic.ISREPLYED;
import static com.jeecms.cms.statistic.CmsStatistic.USERID;
import static com.jeecms.cms.statistic.CmsStatistic.CHANNELID;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.statistic.CmsStatistic.TimeRange;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateSimpleDao;

@Repository
public class CmsStatisticDaoImpl extends HibernateSimpleDao implements
		CmsStatisticDao {
	public long memberStatistic(TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from CmsUser bean where 1=1");
		if (timeRange != null) {
			f.append(" and bean.registerTime >= :begin");
			f.append(" and bean.registerTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return (Long) find(f).iterator().next();
	}

	public long contentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from Content bean");
		Integer userId = (Integer) restrictions.get(USERID);
		Integer channelId = (Integer) restrictions.get(CHANNELID);
		if (channelId != null) {
			f.append(" join bean.channel channel,Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else {
			f.append(" where bean.site.id=:siteId").setParam("siteId",
					restrictions.get(SITEID));
		}
		if (timeRange != null) {
			f.append(" and bean.contentExt.releaseDate >= :begin");
			f.append(" and bean.contentExt.releaseDate < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		if (userId != null) {
			f.append(" and bean.user.id=:userId").setParam("userId", userId);
		}
		return (Long) find(f).iterator().next();
	}

	public long commentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from CmsComment bean where bean.site.id=:siteId");
		f.setParam("siteId", restrictions.get(SITEID));
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		Boolean isReplyed = (Boolean) restrictions.get(ISREPLYED);
		if (isReplyed != null) {
			if (isReplyed) {
				f.append(" and bean.replayTime is not null");
			} else {
				f.append(" and bean.replayTime is null");
			}
		}
		return (Long) find(f).iterator().next();
	}

	public long guestbookStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from CmsGuestbook bean where bean.site.id=:siteId");
		f.setParam("siteId", restrictions.get(SITEID));
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		Boolean isReplyed = (Boolean) restrictions.get(ISREPLYED);
		if (isReplyed != null) {
			if (isReplyed) {
				f.append(" and bean.replayTime is not null");
			} else {
				f.append(" and bean.replayTime is null");
			}
		}
		return (Long) find(f).iterator().next();
	}

	private Finder createCacheableFinder(String hql) {
		Finder finder = Finder.create(hql);
		finder.setCacheable(true);
		return finder;
	}

}
