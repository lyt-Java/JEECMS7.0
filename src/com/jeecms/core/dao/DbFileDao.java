package com.jeecms.core.dao;

import com.jeecms.common.hibernate3.Updater;
import com.jeecms.core.entity.DbFile;

public interface DbFileDao {
	public DbFile findById(String id);

	public DbFile save(DbFile bean);

	public DbFile updateByUpdater(Updater<DbFile> updater);

	public DbFile deleteById(String id);
}