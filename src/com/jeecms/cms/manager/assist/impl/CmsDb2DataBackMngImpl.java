package com.jeecms.cms.manager.assist.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.Constants;
import com.jeecms.cms.dao.assist.CmsDb2DataBackDao;
import com.jeecms.cms.entity.back.CmsField;
import com.jeecms.cms.manager.assist.CmsDb2DataBackMng;
import com.jeecms.common.util.StrUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;

@Service
@Transactional
public class CmsDb2DataBackMngImpl implements CmsDb2DataBackMng {
	private static String INSERT_INTO = " INSERT INTO ";
	private static String VALUES = " VALUES";
	private static String BRANCH = ";";
	private static String LEFTBRACE = "(";
	private static String RIGHTBRACE = ")";
	private static String QUOTES = "'";
	private static String COMMA = ",";
	private static String BR = "\r\n";
	private static String SLASH = "\\";
	private static String SQL_NODE_PREFIX = "--";
	private static String COMMENT_PREFIX = "COMMENT ON ";
	private static String SQL_CONNECT_PREFIX = "CONNECT TO";
	private static String SQL_COMMIT_PREFIX = "COMMIT WORK";
	private static String SQL_CONNECT_RESET_PREFIX = "CONNECT RESET";
	private static String SQL_TERMINATE_PREFIX = "TERMINATE";
	private static String ALTER_TABLE = "ALTER TABLE ";
	private static String CREATE_TABLE = "CREATE TABLE ";
	private static String ALTER_FOREIGN_KEY = "  ALTER FOREIGN KEY ";
	private static String ADD_CONSTRAINT = " ADD CONSTRAINT ";
	private static String FOREIGN_KEY = " FOREIGN KEY ";
	private static String REFERENCES = " REFERENCES ";
	private static String NOT_ENFORCED = "  NOT ENFORCED ";
	private static String ENFORCED = "  ENFORCED ";

	@Transactional(readOnly = true)
	public List<String> listTables() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)
	public List<CmsField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Transactional(readOnly = true)
	public String createTableDDL(String tablename, String encoding)
			throws FileNotFoundException, SQLException {
		//String foreignKeyDDL=createTableForeignKeyDDL(tablename, encoding);
		File tableSqlFile = createTableSQLFile(tablename);
		StringBuffer buffer = new StringBuffer();
		String schema = dao.getDefaultSchema();
		if (tableSqlFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(tableSqlFile), encoding));
				String str;
				while ((str = in.readLine()) != null) {
					// 去除空格、注释、connect连接语句、commit等语句
					if (!isUselessSql(str)) {
						// 去除模式前缀
						str = str.replace("\"" + schema + "\".", "");
						// 注释作为单独一条sql
						if (str.startsWith(CREATE_TABLE)
								|| str.startsWith(COMMENT_PREFIX)
								|| str.startsWith(ALTER_TABLE)) {
							buffer.append(Constants.ONESQL_PREFIX);
						}
						buffer.append(str + BR);
					}
				}
				in.close();
				tableSqlFile.delete();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	//	buffer.substring(0,buffer.indexOf(foreignKeyDDL));
		return buffer.toString();
	}
	
	@Transactional(readOnly = true)
	public String createTableForeignKeyDDL(String tablename, String encoding)
			throws FileNotFoundException, SQLException {
		File tableSqlFile = createTableSQLFile(tablename.toLowerCase());
		StringBuffer buffer = new StringBuffer();
		String schema = dao.getDefaultSchema();
		if (tableSqlFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(tableSqlFile), encoding));
				String str,strbefore="";
				boolean foreignKeyBegin=false;
				str = in.readLine();
				while (str!= null) {
					// 去除空格、注释、connect连接语句、commit等语句
					if (!isUselessSql(str)) {
						// 去除模式前缀
						str = str.replace("\"" + schema + "\".", "");
						// 外键约束开始
						if (strbefore.startsWith(ALTER_TABLE)&&str.contains(FOREIGN_KEY.trim())) {
							foreignKeyBegin=true;
						}
						if(foreignKeyBegin){
							//每个外键语句加前缀
							if(str.startsWith(ALTER_TABLE)){
								buffer.append(Constants.ONESQL_PREFIX);
							}
							//第一个外键约束
							if(buffer.length()<=0){
								buffer.append(Constants.ONESQL_PREFIX);
								buffer.append(strbefore+ BR);
							}
							buffer.append(str + BR);
						}
					}
					strbefore=str;
					str=in.readLine();
				}
				in.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return buffer.toString();
	}

	@Transactional(readOnly = true)
	public String createTableDataSQL(String tablename) {
		StringBuffer buffer = new StringBuffer();
		List<Object[]> results = dao.createTableData(tablename);
		for (Object[] oneResult : results) {
			buffer.append(Constants.ONESQL_PREFIX + INSERT_INTO + tablename
					+ VALUES + LEFTBRACE);
			for (int j = 0; j < oneResult.length; j++) {
				if (oneResult[j] != null) {
					if (oneResult[j] instanceof Date) {
						buffer.append(QUOTES + oneResult[j] + QUOTES);
					} else if (oneResult[j] instanceof String) {
						buffer
								.append(QUOTES
										+ StrUtils
												.replaceKeyString((String) oneResult[j])
										+ QUOTES);
					} else if (oneResult[j] instanceof Boolean) {
						if ((Boolean) oneResult[j]) {
							buffer.append(1);
						} else {
							buffer.append(0);
						}
					} else {
						buffer.append(oneResult[j]);
					}
				} else {
					buffer.append(oneResult[j]);
				}
				buffer.append(COMMA);
			}
			buffer = buffer.deleteCharAt(buffer.lastIndexOf(COMMA));
			buffer.append(RIGHTBRACE + BRANCH + BR);
		}
		return buffer.toString();
	}

	public String disableORenbaleFK(boolean isEnable) {
		List<String> tables = listTables();
		StringBuffer buffer = new StringBuffer();
		String enable = NOT_ENFORCED;
		for (String table : tables) {
			for (String fk : dao.getFkConstraints(table)) {
				if (isEnable) {
					enable = ENFORCED;
				}
				buffer.append(Constants.ONESQL_PREFIX + ALTER_TABLE + table
						+ ALTER_FOREIGN_KEY + fk + enable + ";" + BR);
			}
		}
		return buffer.toString();
	}

	public String getIdentityColumn(String tablename) {
		return dao.getIdentityColumn(tablename);
	}
	
	public Integer getMaxValueOfIdentityColumn(String tablename){
		return dao.getMaxValueOfIdentityColumn(tablename);
	}
	
	public String getTableReferences(String tablename) {
		StringBuffer buffer = new StringBuffer();
		List<Object[]> references = dao.getTableReferences(tablename);
		for (Object[] refer : references) {
			//自身表字段关联不能重复建立约束,已经存在约束不用创建
			if(!tablename.equals(refer[0])){
				buffer.append(Constants.ONESQL_PREFIX).append(ALTER_TABLE).append(
						refer[0]).append(ADD_CONSTRAINT).append(refer[1]).append(
						FOREIGN_KEY).append(LEFTBRACE).append(refer[2]).append(
						RIGHTBRACE).append(REFERENCES).append(tablename).append(
						LEFTBRACE).append(refer[3]).append(RIGHTBRACE).append(NOT_ENFORCED).append(";")
						.append(BR);
			}
		}
		return buffer.toString();
	}

	public void executeSQL(String sqls) {
		// sqls是多条sql语句
		String[] s = sqls.split(Constants.ONESQL_PREFIX);
		for (String sql : s) {
			if (StringUtils.isNotBlank(sql)) {
				// 去除最后的分号;
				sql = sql.substring(0, sql.lastIndexOf(BRANCH));
				try {
					dao.executeSQL(sql);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

	
	private boolean isUselessSql(String str) {
		if (StringUtils.isBlank(str)) {
			return true;
		}
		if (str.startsWith(SQL_NODE_PREFIX)) {
			return true;
		}
		if (str.startsWith(SQL_CONNECT_PREFIX)) {
			return true;
		}
		if (str.startsWith(SQL_COMMIT_PREFIX)) {
			return true;
		}
		if (str.startsWith(SQL_CONNECT_RESET_PREFIX)) {
			return true;
		}
		if (str.startsWith(SQL_TERMINATE_PREFIX)) {
			return true;
		}
		return false;
	}

	private File createTableSQLFile(String tablename) throws SQLException {
		String catalog = dao.getDefaultCatalog();
		String filePath = Constants.BACKUP_PATH + SLASH + tablename + ".sql";
		filePath = realPathResolver.get(filePath);
		File file = new File(filePath);
		String command = "db2look -d " + catalog + " -t " + tablename
				+ "  -a -e  -c -o " + filePath;
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	private CmsDb2DataBackDao dao;
	@Autowired
	private RealPathResolver realPathResolver;

	@Autowired
	public void setDao(CmsDb2DataBackDao dao) {
		this.dao = dao;
	}
}