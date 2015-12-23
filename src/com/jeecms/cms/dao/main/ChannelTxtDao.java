package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.ChannelTxt;
import com.jeecms.common.hibernate3.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}