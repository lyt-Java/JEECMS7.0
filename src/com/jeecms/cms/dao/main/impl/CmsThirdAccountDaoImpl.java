package com.jeecms.cms.dao.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.main.CmsThirdAccountDao;
import com.jeecms.cms.entity.main.CmsThirdAccount;

@Repository
public class CmsThirdAccountDaoImpl extends HibernateBaseDao<CmsThirdAccount, Long> implements CmsThirdAccountDao {
	public Pagination getPage(String username,String source,int pageNo, int pageSize) {
		String hql="from CmsThirdAccount bean where 1=1 ";
		Finder f=Finder.create(hql);
		if(StringUtils.isNotBlank(username)){
			f.append(" and bean.username like :username").setParam("username", "%"+username+"%");
		}
		if(StringUtils.isNotBlank(source)){
			f.append(" and bean.source=:source").setParam("source", source);
		}
		return find(f, pageNo, pageSize);
	}

	public CmsThirdAccount findById(Long id) {
		CmsThirdAccount entity = get(id);
		return entity;
	}
	
	public CmsThirdAccount findByKey(String key){
		String hql="from CmsThirdAccount bean where bean.accountKey=:accountKey";
		Finder f=Finder.create(hql).setParam("accountKey", key);
		List<CmsThirdAccount>li= find(f);
		if(li.size()>0){
			return li.get(0);
		}else{
			return null;
		}
	}

	public CmsThirdAccount save(CmsThirdAccount bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsThirdAccount deleteById(Long id) {
		CmsThirdAccount entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsThirdAccount> getEntityClass() {
		return CmsThirdAccount.class;
	}
}