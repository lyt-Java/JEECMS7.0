package com.jeecms.core.manager;

import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsSiteCompany;

public interface CmsSiteCompanyMng {
	public CmsSiteCompany save(CmsSite site,CmsSiteCompany bean);

	public CmsSiteCompany update(CmsSiteCompany bean);
}