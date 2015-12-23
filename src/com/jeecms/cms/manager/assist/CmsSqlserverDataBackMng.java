package com.jeecms.cms.manager.assist;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jeecms.cms.entity.back.CmsField;

public interface CmsSqlserverDataBackMng {
	public  List<String> listTabels();

	public  List<CmsField> listFields(String tablename);

	public  List<String> getColumns(String tablename);

	public  List<String> listDataBases();

	public  String createTableDDL(String tablename);

	public  String createConstraintDDL(String sql, String tablename);

	public  List<Object[]> createTableData(String tablename);

	public  String getDefaultCatalog() throws SQLException;

	public  void setDefaultCatalog(String catalog)throws SQLException;

	public  String getNoCheckConstraintSql(String tablename);

	public  String getCheckConstraintSql(String tablename);

	public  Map<String, String> getBeReferForeignKeyFromTable(String tablename);

	public  String getFKConstraintByName(String tablename,String fkConstraintName);

	public  Boolean executeSQL(String sql);
}