package com.jeecms.core.manager;

import java.util.List;

import com.jeecms.core.entity.CmsGroup;

public interface CmsGroupMng {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);

	public void updateRegDef(Integer regDefId);

	public CmsGroup save(CmsGroup bean);
	
	public CmsGroup save(CmsGroup bean,Integer[] viewChannelIds, Integer[] contriChannelIds);

	public CmsGroup update(CmsGroup bean);
	
	public CmsGroup update(CmsGroup bean,Integer[] viewChannelIds, Integer[] contriChannelIds);

	public CmsGroup deleteById(Integer id);

	public CmsGroup[] deleteByIds(Integer[] ids);

	public CmsGroup[] updatePriority(Integer[] ids, Integer[] priority);
}