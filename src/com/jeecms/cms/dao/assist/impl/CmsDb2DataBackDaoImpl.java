package com.jeecms.cms.dao.assist.impl;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsDb2DataBackDao;
import com.jeecms.cms.entity.back.CmsField;

@Repository
public class CmsDb2DataBackDaoImpl extends JdbcDaoSupport implements
		CmsDb2DataBackDao {

	public String getDefaultSchema(){
		String sql = "select CURRENT schema from sysibm.sysdummy1";
		String schema=getJdbcTemplate().queryForObject(sql, String.class);
		return schema;
	}
	
	public String getDefaultCatalog() throws SQLException {
		String sql = " select db_name from table(SNAPSHOT_DATABASE('',-1)) as t";
		String ddl = getJdbcTemplate().queryForObject(sql,
				new RowMapper<String>() {
					public String mapRow(ResultSet set, int arg1)
							throws SQLException {
						return set.getString(1);
					}
				});
		return ddl;
	}
	
	public List<String> listTables() {
		String sql = " select tabname from syscat.tables where tabschema = CURRENT SCHEMA "; 
		List<String> tables = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			tables.add(set.getString(1));
		}
		return tables;
	}


	public List<CmsField> listFields(String tablename) {
		String sql = "select NAME,COLTYPE,NULLS,REMARKS,DEFAULT from sysibm.syscolumns where TBNAME ='"+tablename+"' order by COLNO ASC";
		List<CmsField> fields = new ArrayList<CmsField>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			CmsField field = new CmsField();
			field.setName(set.getString(1));
			field.setFieldType(set.getString(2));
			field.setNullable(set.getString(3));
			field.setComment(set.getString(4));
			field.setFieldDefault(set.getString(5));
			fields.add(field);
		}
		return fields;
	}

	public List<Object[]> getTableReferences(String tablename){
		List<Object[]> results = new ArrayList<Object[]>();
		String sql = " SELECT TABNAME,CONSTNAME,FK_COLNAMES  ,PK_COLNAMES   FROM SYSCAT.REFERENCES   where  REFTABNAME ='"+tablename+"' " ;
		results=getJdbcTemplate().query(sql, new ResultSetExtractor<List<Object[]>>(){
			public List<Object[]> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Object[]>result=new ArrayList<Object[]>();
				while(rs.next()){
					Object[] oneResult = new Object[4];
					for (int i = 1; i <= 4; i++) {
						oneResult[i - 1] = rs.getObject(i);
					}
					result.add(oneResult);
				}
				return result;
			}
		});
		return results;
	}
	
	public List<Object[]> createTableData(String tablename) {
		final int filedNum = getTableFieldNums(tablename);
		List<Object[]> results = new ArrayList<Object[]>();
		String sql = " select * from   " + tablename;
		results=getJdbcTemplate().query(sql, new ResultSetExtractor<List<Object[]>>(){
			public List<Object[]> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Object[]>result=new ArrayList<Object[]>();
				while(rs.next()){
					Object[] oneResult = new Object[filedNum];
					for (int i = 1; i <= filedNum; i++) {
						oneResult[i - 1] = rs.getObject(i);
						if(rs.getObject(i)instanceof Clob){
							oneResult[i - 1]=lobHandler.getClobAsString(rs, i);
						}
					}
					result.add(oneResult);
				}
				return result;
			}
		});
		return results;
	}
	
	public List<String> getFkConstraints(String tablename){
		String sql = " select NAME from SYSIBM.SYSTABCONST where CONSTRAINTYP='F' and  tbname='"+tablename+"'"; 
		List<String> constraints = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			constraints.add(set.getString(1));
		}
		return constraints;
	}
	
	public boolean constraintExist(String constraint){
		String sql = " select NAME from SYSIBM.SYSTABCONST where  name='"+constraint+"'"; 
		List<String>cols = getJdbcTemplate().queryForList(sql, String.class);
		if(cols.size()>0){
			return true;
		}
		return false;
	}

	public Integer getMaxValueOfIdentityColumn(String tablename){
		String identityColumn=getIdentityColumn(tablename);
		Integer max=null;
		if(StringUtils.isNotBlank(identityColumn)){
			String sql="SELECT max("+identityColumn+") FROM "+tablename;
			max =getJdbcTemplate().queryForObject(sql, Integer.class);
		}
		return max;
	}
	
	public String getIdentityColumn(String tablename)   {
		String sql = " select NAME from sysibm.syscolumns where TBNAME ='"+tablename+"' and GENERATED='A' ";
		String col="";
		List<String>cols = getJdbcTemplate().queryForList(sql, String.class);
		if(cols.size()>0){
			return cols.get(0);
		}
		return col;
	}
	

	public void executeSQL(String sql) {
			getJdbcTemplate().execute(sql.trim());
	}

	private int getTableFieldNums(String tablename) {
		String sql = "select NAME from sysibm.syscolumns where TBNAME ='"+tablename+"' order by COLNO ASC";
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		int rownum = 0;
		while (set.next()) {
			rownum++;
		}
		return rownum;
	}
	private LobHandler lobHandler;

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

}