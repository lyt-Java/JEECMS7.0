package com.jeecms.core.dao;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.entity.CmsSiteCompany;

public interface CmsSiteCompanyDao {

	public CmsSiteCompany save(CmsSiteCompany bean);

	public CmsSiteCompany updateByUpdater(Updater<CmsSiteCompany> updater);
}