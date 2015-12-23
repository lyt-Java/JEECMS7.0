package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsFileDao;
import com.jeecms.cms.entity.assist.CmsFile;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsFileDaoImpl extends HibernateBaseDao<CmsFile, Integer>
		implements CmsFileDao {
	@SuppressWarnings("unchecked")
	public List<CmsFile> getList(Boolean valid) {
		Finder f = Finder.create("from CmsFile bean where 1=1");
		if(valid!=null){
			if(valid){
				f.append(" and bean.fileIsvalid=true");
			}else{
				f.append(" and bean.fileIsvalid=false");
			}
		}
		f.append(" order by bean.id desc");
		return find(f);
	}
	
	@SuppressWarnings("unchecked")
	public CmsFile findByPath(String path){
		Finder f = Finder.create("from CmsFile bean where bean.filePath  like '%"+path+"%'");
		List<CmsFile> li=find(f);
		if(li!=null&&li.size()>0){
			return (CmsFile) li.get(0);
		}else{
			return null;
		}
	}


	public CmsFile findById(Integer id) {
		CmsFile entity = get(id);
		return entity;
	}

	public CmsFile save(CmsFile bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsFile deleteById(Integer id) {
		CmsFile entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public CmsFile deleteByPath(String path) {
		CmsFile entity = findByPath(path);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public void deleteByContentId(Integer contentId){
		String sql="delete from CmsFile file where file.content.id=:contentId";
		getSession().createQuery(sql).setParameter("contentId", contentId).executeUpdate();
	}


	@Override
	protected Class<CmsFile> getEntityClass() {
		return CmsFile.class;
	}
}