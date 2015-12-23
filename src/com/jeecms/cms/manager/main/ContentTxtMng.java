package com.jeecms.cms.manager.main;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}