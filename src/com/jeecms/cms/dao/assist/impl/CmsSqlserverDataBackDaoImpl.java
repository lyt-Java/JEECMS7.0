package com.jeecms.cms.dao.assist.impl;

import com.jeecms.cms.Constants;
import com.jeecms.cms.dao.assist.CmsSqlserverDataBackDao;
import com.jeecms.cms.entity.back.CmsField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class CmsSqlserverDataBackDaoImpl extends JdbcDaoSupport implements
		CmsSqlserverDataBackDao {
	private static String BR = "\r\n";
	private static String SPLIT_BR = ",\r\n";
	private static String COMMA = ",";

	public String createTableDDL(String tablename) {
		String sql = " exec sp_help " + tablename;
		StringBuffer ddlBuffer = new StringBuffer();
		Connection conn = getConnection();

		int resultSetIndex = 1;

		String[] identityString = getIdentityString(tablename);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ddlBuffer.append(Constants.ONESQL_PREFIX);
			ddlBuffer
					.append("if  exists (select * from sysobjects where [name] = '"
							+ tablename + "' and xtype='U')");
			ddlBuffer.append("drop   table   [" + tablename + "]");
			ddlBuffer.append(BR);

			ddlBuffer.append(" CREATE TABLE [dbo].[" + tablename + "]" + "("
					+ BR);
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 2) {
					ddlBuffer.append(setColumnsDDLBuffer(rs, identityString[0],
							identityString[1], identityString[2]));
					
				}
				if (resultSetIndex == 7) {
					String pkConstraint = getPKConstraintDDLBuffer(rs);
					if (StringUtils.isBlank(pkConstraint)) {
						ddlBuffer = new StringBuffer(ddlBuffer.subSequence(0,
								ddlBuffer.lastIndexOf(COMMA)));
						ddlBuffer.append(BR + ")" + BR);
						
					} else {
						ddlBuffer.append(pkConstraint);
					}
				}
				resultSetIndex++;
			} while (ps.getMoreResults());
			
			//整合版
			if (resultSetIndex < 8) {
				ddlBuffer = new StringBuffer(ddlBuffer.subSequence(0, ddlBuffer
						.lastIndexOf(COMMA)));
				ddlBuffer.append(BR + ")" + BR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ddlBuffer.toString();
	}

	public String createConstraintDDL(String sql, String tablename) {
		String sql_help = " exec sp_help " + tablename;
		StringBuffer ddlBuffer = new StringBuffer();
		Connection conn = getConnection();

		int resultSetIndex = 1;
		String fkConstraintString = "";
		try {
			PreparedStatement ps = conn.prepareStatement(sql_help);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 7) {
					fkConstraintString = getFKConstraintDDLBuffer(tablename);
					if (!sql.contains(fkConstraintString)) {
						ddlBuffer.append(fkConstraintString);
					}
				}
				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ddlBuffer.toString();
	}

	public List<String> getColumns(String tablename) {
		String sql = " exec sp_help " + tablename;
		Connection conn = getConnection();

		int resultSetIndex = 1;
		List<String> columns = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 2) {
					while (rs.next()) {
						columns.add(rs.getString(1));
					}
				}
				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}

	public List<Object[]> createTableData(String tablename) {
		int filedNum = getTableFieldNums(tablename);
		List<Object[]> results = new ArrayList<Object[]>();
		String sql = " select * from   " + tablename;
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			Object[] oneResult = new Object[filedNum];
			for (int i = 1; i <= filedNum; i++) {
				oneResult[(i - 1)] = set.getObject(i);
			}
			results.add(oneResult);
		}
		return results;
	}

	public List<CmsField> listFields(String tablename) {
		String sql = " select  syscolumns.name,systypes.name,syscolumns.isnullable,syscolumns.length from syscolumns inner join systypes on syscolumns.xusertype=systypes.xusertype where syscolumns.id=object_id('"
				+ tablename + "');  ";
		List<CmsField> fields = new ArrayList<CmsField>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			CmsField field = new CmsField();
			field.setName(set.getString(1));
			field.setFieldType(set.getString(2));
			field.setNullable(set.getString(3));
			field.setLength(set.getString(4));
			fields.add(field);
		}
		return fields;
	}

	public List<String> listTables() {
		String sql = " select name from sysobjects where xtype='U' order by name ";
		List<String> tables = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			tables.add(set.getString(1));
		}
		return tables;
	}

	public List<String> listDataBases() {
		String sql = " exec sp_databases ";
		List<String> tables = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			tables.add(set.getString(1));
		}
		return tables;
	}

	public String getDefaultCatalog() throws SQLException {
		return getJdbcTemplate().getDataSource().getConnection().getCatalog();
	}

	public void setDefaultCatalog(String catalog) throws SQLException {
		getJdbcTemplate().getDataSource().getConnection().setCatalog(catalog);
	}

	public List<String> getNoCheckConstraintSql(String tablename) {
		String sql = " select  'ALTER TABLE ['  + b.name +  '] NOCHECK CONSTRAINT ' +  a.name  from  sysobjects  a ,sysobjects  b    where  a.xtype ='f' and  a.parent_obj = b.id and b.name='"
				+ tablename + "'";
		List<String> result = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			result.add(set.getString(1));
		}
		return result;
	}

	public List<String> getCheckConstraintSql(String tablename) {
		String sql = " select  'ALTER TABLE ['  + b.name +  ']  CHECK CONSTRAINT ' +  a.name  from  sysobjects  a ,sysobjects  b    where  a.xtype ='f' and  a.parent_obj = b.id and b.name='"
				+ tablename + "'";
		List<String> result = new ArrayList<String>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			result.add(set.getString(1));
		}
		return result;
	}

	public List<String> getBeReferForeignKeyFromTable(String tablename) {
		String sql = " exec sp_help " + tablename;
		Connection conn = getConnection();

		int resultSetIndex = 1;
		List<String> result = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 8) {
					while (rs.next()) {
						result.add(rs.getString(1));
					}
				}
				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getTableFieldNums(String tablename) {
		String sql = " select count(*) from syscolumns where syscolumns.id=object_id('"
				+ tablename + "')";
		int rownum = 0;
		rownum = Integer.parseInt((String) getJdbcTemplate().queryForObject(
				sql, new RowMapper() {
					public String mapRow(ResultSet set, int arg1)
							throws SQLException {
						return set.getString(1);
					}
				}));
		return rownum;
	}

	public String getFKConstraintByName(String tablename,
			String fkConstraintName) {
		int resultSetIndex = 1;
		String sql = " exec sp_help " + tablename;
		Connection conn = getConnection();

		StringBuffer buffer = new StringBuffer();
		String FKConstraintName = "";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 7) {
					while (rs.next()) {
						if ((!rs.getString(2).equals(fkConstraintName))
								|| (!rs.getString(2).startsWith("FK_")))
							continue;
						FKConstraintName = rs.getString(2);
						buffer.append(Constants.ONESQL_PREFIX);
						buffer.append("ALTER TABLE [dbo].[" + tablename + "]"
								+ " WITH CHECK ADD  CONSTRAINT " + "["
								+ rs.getString(2) + "]" + " FOREIGN KEY " + "("
								+ "[" + rs.getString(7) + "]" + ")" + BR
								+ " REFERENCES ");

						break;
					}

					rs.next();
					if (rs.getString(7).startsWith("REFERENCES")) {
						buffer.append("[dbo].");
						String str = rs.getString(7).split("dbo.")[1];
						String t_name = str.substring(0, str.indexOf("("))
								.trim();
						buffer.append(str.replace("(", "([").replace(")", "])")
								.replace(t_name, "[" + t_name + "]"));
						buffer.append(BR);

						buffer.append(Constants.ONESQL_PREFIX);
						buffer.append("ALTER TABLE [dbo].[" + tablename + "]"
								+ " CHECK CONSTRAINT " + "[" + FKConstraintName
								+ "]");
						buffer.append(BR);
					}
				}

				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public Boolean executeSQL(String sql) {
		try {
			String[] s = sql.split(Constants.ONESQL_PREFIX);
			for (String sqls : s)
				if (StringUtils.isNotBlank(sqls))
					getJdbcTemplate().execute(sqls.trim());
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(true);
	}

	public void executeSQLWithNoResult(String sql) {
		getJdbcTemplate().execute(sql.trim());
	}

	private String setColumnsDDLBuffer(ResultSet rs, String identityColumn,
			String identitySeed, String identityIncrement) {
		StringBuffer buffer = new StringBuffer();
		try {
			while (rs.next()) {
				buffer.append("[" + rs.getString(1) + "]" + "["
						+ rs.getString(2) + "]");

				if (rs.getString(1).equals(identityColumn)) {
					buffer.append(" IDENTITY(" + identitySeed + ","
							+ identityIncrement + ")");
				}
				if (rs.getString(2).equals("nvarchar")) {
					//整合版
					Integer size = Integer.parseInt(rs.getString(4))/2;
					
					buffer.append("(" + size.toString() + ")");
				}
				if (rs.getString(7).equals("no"))
					buffer.append(" NOT NULL");
				else {
					buffer.append(" NULL");
				}
				buffer.append(SPLIT_BR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private String getPKConstraintDDLBuffer(ResultSet rs) {
		StringBuffer buffer = new StringBuffer();
		try {
			while (rs.next()) {
				if (!rs.getString(2).startsWith("PK_"))
					continue;
				if (rs.getString(7).contains(",")) {
					String[] pks = rs.getString(7).split(",");
					buffer.append("CONSTRAINT [" + rs.getString(2) + "]"
							+ " PRIMARY KEY  " + BR + "(" + BR);
					for (int i = 0; i < pks.length - 1; i++) {
						buffer.append("[" + pks[i].trim() + "]" + " ASC "
								+ COMMA + BR);
					}
					buffer.append("[" + pks[(pks.length - 1)].trim() + "]"
							+ " ASC ");
					buffer.append(BR + ")" + BR + ")");
				} else {
					buffer.append("CONSTRAINT [" + rs.getString(2) + "]"
							+ " PRIMARY KEY  " + BR + "(" + BR + "["
							+ rs.getString(7) + "]" + " ASC " + BR + ")" + BR
							+ ")");
				}
				buffer.append(BR);
			}

			buffer.append(BR);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private String getFKConstraintDDLBuffer(String tablename) {
		int resultSetIndex = 1;
		String sql = " exec sp_help " + tablename;
		Connection conn = getConnection();

		StringBuffer buffer = new StringBuffer();
		String FKConstraintName = "";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 7) {
					while (rs.next()) {
						if ((!rs.getString(2).trim().equals(
								"FK_jc_channel_jc_channel"))
								&& (rs.getString(2).startsWith("FK_"))) {
							FKConstraintName = rs.getString(2).trim();
							buffer.append(Constants.ONESQL_PREFIX);
							buffer.append("ALTER TABLE [dbo].[" + tablename
									+ "]" + " WITH CHECK ADD  CONSTRAINT "
									+ "[" + rs.getString(2) + "]"
									+ " FOREIGN KEY " + "(" + "["
									+ rs.getString(7) + "]" + ")" + BR
									+ " REFERENCES ");
						}

						if ((StringUtils.isNotBlank(FKConstraintName))
								&& (rs.getString(7).startsWith("REFERENCES"))) {
							buffer.append("[dbo].");
							String str = rs.getString(7).split("dbo.")[1];
							String t_name = str.substring(0, str.indexOf("("))
									.trim();
							buffer.append(str.replace("(", "([").replace(")",
									"])").replace(t_name, "[" + t_name + "]"));
							buffer.append(BR);

							buffer.append(Constants.ONESQL_PREFIX);
							buffer.append("ALTER TABLE [dbo].[" + tablename
									+ "]" + " CHECK CONSTRAINT " + "["
									+ FKConstraintName + "]");
							buffer.append(BR);
						}

						if (rs.getString(2).startsWith("DF")) {
							buffer.append(Constants.ONESQL_PREFIX);
							buffer.append("ALTER TABLE [dbo].[" + tablename
									+ "]" + " ADD  DEFAULT" + rs.getString(7)
									+ " FOR " + "["
									+ rs.getString(1).split("column")[1].trim()
									+ "]");
							buffer.append(BR);
						}
					}
				}

				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private String[] getIdentityString(String tablename) {
		int resultSetIndex = 1;
		String sql = " exec sp_help " + tablename;
		Connection conn = getConnection();

		String[] identityStrings = new String[3];
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			do {
				rs = ps.getResultSet();

				if (resultSetIndex == 3) {
					while (rs.next()) {
						if ((rs.getString(2) != null)
								&& (!rs.getString(2).equals(" NULL"))) {
							identityStrings[0] = rs.getString(1);
							identityStrings[1] = rs.getString(2);
							identityStrings[2] = rs.getString(3);
						}
					}
				}
				resultSetIndex++;
			} while (ps.getMoreResults());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return identityStrings;
	}
}