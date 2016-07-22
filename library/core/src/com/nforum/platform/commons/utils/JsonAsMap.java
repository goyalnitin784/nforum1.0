package com.nforum.platform.commons.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;


public class JsonAsMap implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	private Map<String,String> commonData;
	private Map<String,JsonAsMap> otherPaymentData;
	
	public JsonAsMap(JSONObject jsonObject){
		commonData=new HashMap<String, String>();
		otherPaymentData=new HashMap<String, JsonAsMap>();
		
		createMapData(jsonObject);
	}
	public void createMapData(JSONObject jsonObject){
		if(jsonObject==null){
			return;
		}
		Set<String> keys=jsonObject.keySet();
		for(String key : keys){
			if(jsonObject.has(key)){
				if(jsonObject.get(key) instanceof String){
					commonData.put(key,getSafeString(jsonObject, key));
				}
				else if(jsonObject.get(key) instanceof Double){
					commonData.put(key,""+getSafeDouble(jsonObject, key));
				}
				else if(jsonObject.get(key) instanceof Integer){
					commonData.put(key,""+getSafeInteger(jsonObject, key));
				}
				else{
					otherPaymentData.put(key, new JsonAsMap(getSafeObject(jsonObject, key)));
				}
			}
		}
	}
	public JsonAsMap(){
		commonData=new HashMap<String, String>();
		otherPaymentData=new HashMap<String, JsonAsMap>();
		
	}
	public Map<String, String> getCommonData() {
		return commonData;
	}
	public void setCommonData(Map<String, String> commonData) {
		this.commonData = commonData;
	}
	public Map<String, JsonAsMap> getOtherPaymentData() {
		return otherPaymentData;
	}

	public JsonAsMap getJsonAsMap(String key){
		return otherPaymentData.get(key);
	}
	public String getString(String key){
		return commonData.get(key);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setOtherPaymentData(
			Map<String, JsonAsMap> otherPaymentData) {
		this.otherPaymentData = otherPaymentData;
	}

	public JSONObject getJsonObject(){
		
		JSONObject jsonObject=new JSONObject();
		
		for(String key : commonData.keySet()){
			jsonObject.put(key,commonData.get(key));
		}
		
		for(String key : otherPaymentData.keySet()){
						
			jsonObject.put(key, otherPaymentData.get(key).getJsonObject());
		}
		
		return jsonObject;
		
	}
	
	public boolean isEmply(){
		if(commonData==null&&otherPaymentData==null){
			return true;
		}
		else if(commonData!=null&&otherPaymentData==null&&commonData.size()==0){
			return true;
		}
		else if(commonData==null&&otherPaymentData!=null&&otherPaymentData.size()==0){
			return true;
		}
		else if(commonData!=null&&otherPaymentData!=null&&otherPaymentData.size()==0&&commonData.size()==0){
			return true;
		}
		return false;
	}
	public static String getSafeString(JSONObject jsonObject, String key) {

		if(jsonObject==null||!jsonObject.containsKey(key)){
			return "";
		}
		
		return jsonObject.getString(key);
		
	}
	
	public static double getSafeDouble(JSONObject jsonObject, String key) {

		if(jsonObject==null||!jsonObject.containsKey(key)){
			return 0d;
		}
		
	    return jsonObject.getDouble(key);

	}
	
	public static double getSafeInteger(JSONObject jsonObject, String key) {

		if(jsonObject==null||!jsonObject.containsKey(key)){
			return 0d;
		}
		
	    return jsonObject.getInt(key);

	}
	
	public static JSONObject getSafeObject(JSONObject jsonObject, String key) {
		
    	if(jsonObject==null||!jsonObject.containsKey(key)){
			return new JSONObject();
		}
		
	    return jsonObject.getJSONObject(key);
	}	
}
