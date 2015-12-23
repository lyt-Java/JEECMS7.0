package com.jeecms.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.main.ChannelDao;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class ChannelDaoImpl extends HibernateBaseDao<Channel, Integer>
		implements ChannelDao {
	@SuppressWarnings("unchecked")
	public List<Channel> getTopList(Integer siteId,boolean hasContentOnly,
			boolean displayOnly, boolean cacheable) {
		Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
		return find(f);
	}

	public Pagination getTopPage(Integer siteId, boolean hasContentOnly,
			boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
		Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
		return find(f, pageNo, pageSize);
	}

	private Finder getTopFinder(Integer siteId, boolean hasContentOnly,
			boolean displayOnly, boolean cacheable) {
		Finder f = Finder.create("from Channel bean");
		f.append(" where bean.site.id=:siteId and bean.parent.id is null");
		f.setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		if (displayOnly) {
			f.append(" and bean.display=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		f.setCacheable(cacheable);
		return f;
	}

	@SuppressWarnings("unchecked")
	public List<Channel> getTopListByRigth(Integer userId, Integer siteId,
			boolean hasContentOnly) {
		Finder f = Finder.create("select bean from Channel bean");
		f.append(" join bean.users user");
		f.append(" where user.id=:userId and bean.site.id=:siteId");
		f.setParam("userId", userId).setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		f.append(" and bean.parent.id is null");
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getTopListForDepartId(Integer departId,Integer siteId,boolean hasContentOnly){
		Finder f = Finder.create("select distinct bean from Channel bean");
		f.append(" left join bean.departments depart");
		f.append(" where 1=1 ");
		if(departId!=null){
			f.append(" and depart.id =:departId");
			f.setParam("departId", departId);
		}
		f.append(" and bean.site.id=:siteId").setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		f.append(" and bean.parent.id is null");
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly,
			boolean displayOnly, boolean cacheable) {
		Finder f = getChildFinder(parentId, hasContentOnly, displayOnly,
				cacheable);
		return find(f);
	}
	
	public List<Channel> getBottomList(Integer siteId,boolean hasContentOnly){
		Finder f = Finder.create("select  bean from Channel bean");
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		f.append(" and size(bean.child) <= 0");
	//	f.append(" and bean.id not in(select channel.parent.id from Channel channel where channel.parent.id is not null)");
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}

	public Pagination getChildPage(Integer parentId, boolean hasContentOnly,
			boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
		Finder f = getChildFinder(parentId, hasContentOnly, displayOnly,
				cacheable);
		return find(f, pageNo, pageSize);
	}

	private Finder getChildFinder(Integer parentId, boolean hasContentOnly,
			boolean displayOnly, boolean cacheable) {
		Finder f = Finder.create("from Channel bean");
		f.append(" where bean.parent.id=:parentId");
		f.setParam("parentId", parentId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		if (displayOnly) {
			f.append(" and bean.display=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		return f;
	}

	@SuppressWarnings("unchecked")
	public List<Channel> getChildListByRight(Integer userId, Integer parentId,
			boolean hasContentOnly) {
		Finder f = Finder.create("select bean from Channel bean");
		f.append(" join bean.users user");
		f.append(" where user.id=:userId and bean.parent.id=:parentId");
		f.setParam("userId", userId).setParam("parentId", parentId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getChildListByDepartId(Integer departId,Integer siteId,
			Integer parentId, boolean hasContentOnly){
		Finder f = Finder.create("select distinct bean from Channel bean");
		f.append(" left join bean.departments depart");
		f.append(" where  bean.parent.id=:parentId");
		f.setParam("parentId", parentId);
		if(departId!=null){
			f.append(" and depart.id =:departId ");
			f.setParam("departId", departId);
		}
		f.append(" and bean.site.id=:siteId").setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.hasContent=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}

	public Channel findById(Integer id) {
		Channel entity = get(id);
		return entity;
	}

	public Channel findByPath(String path, Integer siteId, boolean cacheable) {
		String hql = "from Channel bean where bean.path=? and bean.site.id=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, path).setParameter(1, siteId);
		// 做一些容错处理，因为毕竟没有在数据库中限定path是唯一的。
		query.setMaxResults(1);
		return (Channel) query.setCacheable(cacheable).uniqueResult();
	}

	public Channel save(Channel bean) {
		getSession().save(bean);
		return bean;
	}

	public Channel deleteById(Integer id) {
		Channel entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public void initWorkFlow(Integer workflowId){
		String hql = "update Channel bean set bean.workflow.id=null  where bean.workflow.id=:workflowId";
		Query query = getSession().createQuery(hql).setParameter("workflowId", workflowId);
		query.executeUpdate();
	}

	@Override
	protected Class<Channel> getEntityClass() {
		return Channel.class;
	}
}