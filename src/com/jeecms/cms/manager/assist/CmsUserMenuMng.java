package com.jeecms.cms.manager.assist;

import java.util.List;

import com.jeecms.common.page.Pagination;
import com.jeecms.cms.entity.assist.CmsUserMenu;

public interface CmsUserMenuMng {
	public Pagination getPage(Integer userId,int pageNo, int pageSize);
	
	public List<CmsUserMenu> getList(Integer userId,int cout);

	public CmsUserMenu findById(Integer id);

	public CmsUserMenu save(CmsUserMenu bean);

	public CmsUserMenu update(CmsUserMenu bean);

	public CmsUserMenu deleteById(Integer id);
	
	public CmsUserMenu[] deleteByIds(Integer[] ids);
}