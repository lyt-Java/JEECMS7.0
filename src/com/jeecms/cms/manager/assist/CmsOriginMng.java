package com.jeecms.cms.manager.assist;

import java.util.List;

import com.jeecms.common.page.Pagination;
import com.jeecms.cms.entity.assist.CmsOrigin;

public interface CmsOriginMng {
	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsOrigin> getList(String name);
	
	public CmsOrigin findById(Integer id);

	public CmsOrigin save(CmsOrigin bean);

	public CmsOrigin update(CmsOrigin bean);

	public CmsOrigin deleteById(Integer id);
	
	public CmsOrigin[] deleteByIds(Integer[] ids);

}