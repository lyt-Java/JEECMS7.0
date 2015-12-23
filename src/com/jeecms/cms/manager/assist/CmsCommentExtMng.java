package com.jeecms.cms.manager.assist;

import com.jeecms.cms.entity.assist.CmsComment;
import com.jeecms.cms.entity.assist.CmsCommentExt;

public interface CmsCommentExtMng {
	public CmsCommentExt save(String ip, String text, CmsComment comment);

	public CmsCommentExt update(CmsCommentExt bean);

	public int deleteByContentId(Integer contentId);
}