package com.nforum.platform.commons.utils;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class JsonFormatter {

	public String format(String unformattedJson)
	{
		try{
		JSONObject objJson = (JSONObject) JSONSerializer.toJSON(unformattedJson);
		return objJson.toString(3);
		}catch(Exception e){}
		return unformattedJson;
	}
}
