package com.jeecms.cms.dao.assist;

import java.sql.SQLException;
import java.util.List;

import com.jeecms.cms.entity.back.CmsField;

public interface CmsMysqlDataBackDao {

	public List<String> listTables(String catalog);

	public List<CmsField> listFields(String tablename);

	public List<String> listDataBases();

	public String createTableDDL(String tablename);
	
	public String getDefaultCatalog()throws SQLException;
	
	public void setDefaultCatalog(String catalog) throws SQLException;

	public List<Object[]> createTableData(String tablename);
	
	public Boolean executeSQL(String sql);

}