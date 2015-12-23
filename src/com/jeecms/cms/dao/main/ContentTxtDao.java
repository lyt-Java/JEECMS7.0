package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.common.hibernate3.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}