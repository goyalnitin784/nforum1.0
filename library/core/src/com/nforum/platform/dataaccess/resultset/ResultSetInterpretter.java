package com.nforum.platform.dataaccess.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetInterpretter {

	public void interpretResultSet(ResultSet rs) throws SQLException;
}
