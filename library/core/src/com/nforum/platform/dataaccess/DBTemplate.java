package com.nforum.platform.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.nforum.platform.dataaccess.resultset.CountInterpretter;
import com.nforum.platform.dataaccess.resultset.ResultSetInterpretter;
import com.nforum.platform.dataaccess.resultset.StringListInterpretter;

public class DBTemplate {

	DataSource dataSource;
	
	public DBTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void executeQuery(String sql,
			ResultSetInterpretter resultSetInterpretter) {

		ResultSet rs = null;
		Connection conn =null;
		try {
			conn = dataSource.getConnection();
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				resultSetInterpretter.interpretResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	
	}
	
	public int countQuery(String sql){
		CountInterpretter countInterPretter = new CountInterpretter();
		executeQuery(sql, countInterPretter);
		return countInterPretter.getCount();
	}
	
	public List<String> listQuery(String sql){
		StringListInterpretter listInterPretter = new StringListInterpretter();
		executeQuery(sql, listInterPretter);
		return listInterPretter.getLstItems();
	}

	public void executeUpdate(String sql) {
		Connection conn =null;
		try {
			conn = dataSource.getConnection();
			conn.createStatement().executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
	}
	
}
