package com.jeecms.core.manager;

import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}