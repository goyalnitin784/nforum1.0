package com.nforum.platform.dataaccess.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountInterpretter implements ResultSetInterpretter{

	int count;
	@Override
	public void interpretResultSet(ResultSet resultSet) throws SQLException {
		count = resultSet.getInt(1);
	}

	public int getCount()
	{
		return count;
	}
}
