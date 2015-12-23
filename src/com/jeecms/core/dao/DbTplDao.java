package com.jeecms.core.dao;

import java.util.List;

import com.jeecms.core.entity.DbTpl;

public interface DbTplDao {
	public List<DbTpl> getStartWith(String prefix);

	public List<DbTpl> getChild(String path, boolean isDirectory);

	public DbTpl findById(String id);

	public DbTpl save(DbTpl bean);

	public DbTpl deleteById(String id);
}