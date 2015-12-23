package com.jeecms.cms.dao.assist;

import java.sql.SQLException;
import java.util.List;

import com.jeecms.cms.entity.back.CmsField;

public interface CmsDb2DataBackDao {

	public String getDefaultSchema();
	
	public String getDefaultCatalog()throws SQLException;
	
	public List<String> listTables();

	public List<CmsField> listFields(String tablename);
	
	/**
	 * 返回表的被关联外键
	 * object[]封装关联表名，外键名称，字段，参照表中字段，
	 * @param tablename
	 * @return
	 */
	public List<Object[]> getTableReferences(String tablename);
	
	public List<Object[]> createTableData(String tablename);
	
	public List<String> getFkConstraints(String tablename);
	
	public boolean constraintExist(String constraint);
	
	public String getIdentityColumn(String tablename);
	
	public Integer getMaxValueOfIdentityColumn(String tablename);
	
	public void executeSQL(String sql);

}