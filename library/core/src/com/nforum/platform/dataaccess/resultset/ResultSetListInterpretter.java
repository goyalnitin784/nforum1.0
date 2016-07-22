package com.nforum.platform.dataaccess.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetListInterpretter implements ResultSetInterpretter {

	String columnsArr[];
	Map<String,String> values;
	List<Map<String,String>> valuesList = new ArrayList<Map<String,String>>();;
	
	@Override
	public void interpretResultSet(ResultSet rs) throws SQLException {
		values = new HashMap<String,String>();
		for(String column:columnsArr)
		{
			values.put(column, rs.getString(column));
		}
		valuesList.add(values);
	}

	public void prepare(String columns)
	{
		columnsArr = columns.split(",");		
		for(int i =0; i<columnsArr.length; i++){
			columnsArr[i] = columnsArr[i].trim();
		}
		valuesList.clear(); 
	}
	
	public String getString(String string)
	{
		if(valuesList.size()>0)
			return valuesList.get(0).get(string);
		else return "";
	}
	
	public List<Map<String,String>> getValueList()
	{
		return valuesList;
	}
	
}
