package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.ChannelExt;
import com.jeecms.common.hibernate3.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}