package com.jeecms.core.dao;

import java.util.List;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.entity.Ftp;

public interface FtpDao {
	public List<Ftp> getList();

	public Ftp findById(Integer id);

	public Ftp save(Ftp bean);

	public Ftp updateByUpdater(Updater<Ftp> updater);

	public Ftp deleteById(Integer id);
}