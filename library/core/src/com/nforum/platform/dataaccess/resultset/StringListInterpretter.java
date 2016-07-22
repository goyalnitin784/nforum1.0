package com.nforum.platform.dataaccess.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StringListInterpretter implements ResultSetInterpretter {

	List<String> lstItems = new LinkedList<String>();
	
	@Override
	public void interpretResultSet(ResultSet resultSet) throws SQLException {
		lstItems.add(resultSet.getString(1));
	}

	public List<String> getLstItems() {
		return lstItems;
	}
}
