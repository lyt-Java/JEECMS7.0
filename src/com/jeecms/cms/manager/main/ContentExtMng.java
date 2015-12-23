package com.jeecms.cms.manager.main;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}