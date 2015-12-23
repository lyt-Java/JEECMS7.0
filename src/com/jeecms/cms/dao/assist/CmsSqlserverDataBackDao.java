package com.jeecms.cms.dao.assist;

import java.sql.SQLException;
import java.util.List;

import com.jeecms.cms.entity.back.CmsField;

public interface CmsSqlserverDataBackDao {

	public  List<String> listTables();

	public  List<CmsField> listFields(String tablename);

	public  List<String> getColumns(String tablename);

	public  List<String> listDataBases();

	public  String createTableDDL(String tablename);

	public  String createConstraintDDL(String sql,String tablename);

	public  String getDefaultCatalog() throws SQLException;

	public  void setDefaultCatalog(String catalog)throws SQLException;

	public  List<Object[]> createTableData(String tablename);

	public  List<String> getNoCheckConstraintSql(String tablename);

	public  List<String> getCheckConstraintSql(String tablename);

	public  List<String> getBeReferForeignKeyFromTable(String tablename);

	public  String getFKConstraintByName(String tablename,String fkConstraintName);

	public  Boolean executeSQL(String sql);

}