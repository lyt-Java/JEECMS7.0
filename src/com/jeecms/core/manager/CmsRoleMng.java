package com.jeecms.core.manager;

import java.util.List;
import java.util.Set;

import com.jeecms.core.entity.CmsRole;

public interface CmsRoleMng {
	
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean, Set<String> perms);

	public CmsRole update(CmsRole bean, Set<String> perms);

	public CmsRole deleteById(Integer id);

	public CmsRole[] deleteByIds(Integer[] ids);

	public void deleteMembers(CmsRole role, Integer[] userIds);
}