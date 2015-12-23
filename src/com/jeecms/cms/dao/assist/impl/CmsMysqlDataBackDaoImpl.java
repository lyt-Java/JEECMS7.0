package com.jeecms.cms.dao.assist.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsMysqlDataBackDao;
import com.jeecms.cms.entity.back.CmsField;
import com.jeecms.cms.Constants;

@Repository
public class CmsMysqlDataBackDaoImpl extends JdbcDaoSupport implements
		CmsMysqlDataBackDao {

	public String createTableDDL(String tablename) {
		String sql = " show create table " + tablename;
		String ddl = getJdbcTemplate().queryForObject(sql,
				new RowMapper<String>() {
					public String mapRow(ResultSet set, int arg1)
							throws SQLException {
						return set.getString(2);
					}
				});
		return ddl;
	}

	public List<Object[]> createTableData(String tablename) {
		int filedNum = getTableFieldNums(tablename);
		List<Object[]> results = new ArrayList<Object[]>();
		String sql = " select * from   " + tablename;
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			Object[] oneResult = new Object[filedNum];
			for (int i = 1; i <= filedNum; i++) {
				oneResult[i - 1] = set.getObject(i);
			}
			results.add(oneResult);
		}
		return results;
	}

	public List<CmsField> listFields(String tablename) {
		String sql = " desc  " + tablename;
		List<CmsField> fields = new ArrayList<CmsField>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			CmsField field = new CmsField();
			field.setName(set.getString(1));
			field.setFieldType(set.getString(2));
			field.setNullable(set.getString(3));
			field.setFieldProperty(set.getString(4));
			field.setFieldDefault(set.getString(5));
			field.setExtra(set.getString(6));
			fields.add(field);
		}
		return fields;
	}

	public List<String> listTables(String catalog) {
	//	String sql = " show tables ";
		String sql = " SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA='" + catalog + "' "; 
		List<String> tables = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			tables.add(set.getString(1));
		}
		return tables;
	}
	
	public List<String> listDataBases() {
		String sql = " show  databases ";
		List<String> tables = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			tables.add(set.getString(1));
		}
		return tables;
	}
	public String getDefaultCatalog() throws SQLException{
		  return getJdbcTemplate().getDataSource().getConnection().getCatalog();
	}
	
	public void setDefaultCatalog(String catalog) throws SQLException{
		   getJdbcTemplate().getDataSource().getConnection().setCatalog(catalog);
	}

	private int getTableFieldNums(String tablename) {
		String sql = " desc  " + tablename;
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		int rownum = 0;
		while (set.next()) {
			rownum++;
		}
		return rownum;
	}

	public Boolean executeSQL(String sql) {
		try {
			String[]s=sql.split(Constants.ONESQL_PREFIX);
			for(String sqls:s){
				if(StringUtils.isNotBlank(sqls)){
					getJdbcTemplate().execute(sqls.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}