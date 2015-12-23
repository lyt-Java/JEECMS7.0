package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.util.ChineseCharToEn;
import com.jeecms.cms.dao.assist.CmsSearchWordsDao;
import com.jeecms.cms.entity.assist.CmsSearchWords;

@Repository
public class CmsSearchWordsDaoImpl extends HibernateBaseDao<CmsSearchWords, Integer> implements CmsSearchWordsDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Finder f=Finder.create("from CmsSearchWords words");
		Pagination page = find(f, pageNo, pageSize);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<CmsSearchWords> getList(String name,Integer orderBy,boolean cacheable){
		Finder f=Finder.create("from CmsSearchWords words  ");
		if(StringUtils.isNotBlank(name)){
			String chineseEn =ChineseCharToEn.getAllFirstLetter(name);
			//汉字两边模糊匹配，首字母后面模糊匹配
			f.append(" where  (words.name like :name or words.nameInitial like :nameEn)").setParam("name", "%"+name+"%").setParam("nameEn", chineseEn+"%");;
		}
		if(orderBy!=null){
			if(orderBy.equals(CmsSearchWords.HIT_DESC)){
				f.append(" order by words.hitCount desc");
			}else if(orderBy.equals(CmsSearchWords.HIT_ASC)){
				f.append(" order by words.hitCount asc");
			}else if(orderBy.equals(CmsSearchWords.PRIORITY_DESC)){
				f.append(" order by words.priority desc");
			}else if(orderBy.equals(CmsSearchWords.PRIORITY_ASC)){
				f.append(" order by words.priority asc");
			}
		}else{
			f.append("order by words.hitCount desc");
		}
		f.setCacheable(cacheable);
		return find(f);
	}

	public CmsSearchWords findById(Integer id) {
		CmsSearchWords entity = get(id);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public CmsSearchWords findByName(String name) {
		Finder f=Finder.create("from CmsSearchWords words where words.name=:name ").setParam("name", name);
		List<CmsSearchWords>li=find(f);
		if(li!=null&li.size()>0){
			return li.get(0);
		}else{
			return null;
		}
	}

	public CmsSearchWords save(CmsSearchWords bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsSearchWords deleteById(Integer id) {
		CmsSearchWords entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsSearchWords> getEntityClass() {
		return CmsSearchWords.class;
	}
}