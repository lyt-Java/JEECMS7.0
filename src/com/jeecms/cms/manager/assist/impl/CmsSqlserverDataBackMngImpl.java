package com.jeecms.cms.manager.assist.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.assist.CmsSqlserverDataBackDao;
import com.jeecms.cms.entity.back.CmsField;
import com.jeecms.cms.manager.assist.CmsSqlserverDataBackMng;

@Service
@Transactional
public class CmsSqlserverDataBackMngImpl implements CmsSqlserverDataBackMng {
	private static String BR = "\r\n";

	@Transactional(readOnly = true)
	public String createTableDDL(String tablename) {
		return dao.createTableDDL(tablename);
	}

	@Transactional(readOnly = true)
	public String createConstraintDDL(String sql, String tablename) {
		return dao.createConstraintDDL(sql, tablename);
	}

	@Transactional(readOnly = true)
	public List<Object[]> createTableData(String tablename) {
		return dao.createTableData(tablename);
	}

	@Transactional(readOnly = true)
	public List<CmsField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	public List<String> getColumns(String tablename) {
		return dao.getColumns(tablename);
	}

	@Transactional(readOnly = true)
	public List<String> listTabels() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)
	public List<String> listDataBases() {
		return dao.listDataBases();
	}

	@Transactional(readOnly = true)
	public String getDefaultCatalog() throws SQLException {
		return dao.getDefaultCatalog();
	}

	public void setDefaultCatalog(String catalog) throws SQLException {
		dao.setDefaultCatalog(catalog);
	}

	public String getNoCheckConstraintSql(String tablename) {
		List<String> sqls = dao.getNoCheckConstraintSql(tablename);
		StringBuffer buffer = new StringBuffer();
		for (String s : sqls) {
			buffer.append(s + BR);
		}

		return buffer.toString();
	}

	public String getCheckConstraintSql(String tablename) {
		List<String> sqls = dao.getCheckConstraintSql(tablename);
		StringBuffer buffer = new StringBuffer();
		for (String s : sqls) {
			buffer.append(s + BR);
		}

		return buffer.toString();
	}

	public Map<String, String> getBeReferForeignKeyFromTable(String tablename) {
		List<String> references = dao.getBeReferForeignKeyFromTable(tablename);
		Map<String,String> result = new HashMap<String, String>();
		if ((references != null) && (references.size() > 0)) {
			for (String str : references) {
				String column_name = str.split(": ")[1];
				String table_name = str.split(": ")[0].split("dbo.")[1];
				result.put(column_name, table_name);
			}
		}
		return result;
	}

	public String getFKConstraintByName(String tablename,
			String fkConstraintName) {
		return dao.getFKConstraintByName(tablename, fkConstraintName);
	}

	public Boolean executeSQL(String sql) {
		return dao.executeSQL(sql);
	}

	private CmsSqlserverDataBackDao dao;

	@Autowired
	public void setDao(CmsSqlserverDataBackDao dao) {
		this.dao = dao;
	}


}