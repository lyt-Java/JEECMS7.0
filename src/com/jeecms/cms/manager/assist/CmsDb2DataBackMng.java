package com.jeecms.cms.manager.assist;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.jeecms.cms.entity.back.CmsField;

public interface CmsDb2DataBackMng {
	
	public List<String> listTables();

	public List<CmsField> listFields(String tablename);

	public String createTableDDL(String tablename,String  encoding)throws FileNotFoundException, SQLException;

	public String createTableDataSQL(String tablename);
	
	/**
	 * 返回启用或者禁用所有外键约束
	 * @param isEnable true启用所有外键约束 false禁用所有外键约束
	 * @return
	 */
	public String disableORenbaleFK(boolean isEnable);
	
	public String getIdentityColumn(String tablename);
	
	public Integer getMaxValueOfIdentityColumn(String tablename);
	
	/**
	 * 返回表关联约束语句
	 * @param tablename
	 * @return
	 */
	public String getTableReferences(String tablename);

	public void executeSQL(String sql);
}