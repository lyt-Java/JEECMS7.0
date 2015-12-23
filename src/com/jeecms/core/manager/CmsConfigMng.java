package com.jeecms.core.manager;

import java.util.Date;

import com.jeecms.core.entity.CmsConfig;
import com.jeecms.core.entity.CmsConfigAttr;
import com.jeecms.core.entity.MarkConfig;
import com.jeecms.core.entity.MemberConfig;

public interface CmsConfigMng {
	public CmsConfig get();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);

	public CmsConfig update(CmsConfig bean);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
	
	public void updateConfigAttr(CmsConfigAttr configAttr);
}