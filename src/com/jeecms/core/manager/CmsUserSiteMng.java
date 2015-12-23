package com.jeecms.core.manager;

import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserSite;

public interface CmsUserSiteMng {
	public CmsUserSite findById(Integer id);

	public CmsUserSite save(CmsSite site, CmsUser user, Byte step,
			Boolean allChannel);

	public CmsUserSite update(CmsUserSite bean);

	public void updateByUser(CmsUser user, Integer siteId, Byte step,
			Boolean allChannel);

	public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
			Boolean[] allChannels);

	public int deleteBySiteId(Integer siteId);

	public CmsUserSite deleteById(Integer id);

	public CmsUserSite[] deleteByIds(Integer[] ids);
}