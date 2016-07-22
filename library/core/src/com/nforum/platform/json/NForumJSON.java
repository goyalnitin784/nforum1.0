package com.nforum.platform.json;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class NForumJSON implements JSON, Serializable {

	private static final long serialVersionUID = -3810581486231498454L;

	private JSON json=null;
	private String jsonStr=null;
	

	public NForumJSON(JSON json) {
		this.json = json;
		this.jsonStr = json.toString();
	}

	public NForumJSON() {
		this.jsonStr ="";
		this.json = new JSONObject();
	}

	public NForumJSON(String jsonStr) {
		this(jsonStr,false);
	}

	public NForumJSON(String jsonStr, boolean delayedParse) {
		this.jsonStr = jsonStr;
		if(!delayedParse)
			parseJson(jsonStr);
	}

	private void parseJson(String jsonStr)
	{
		json = JSONSerializer.toJSON(jsonStr);
	}
	
	private JSON getJsonInternal()
	{
		if(json==null)
			parseJson(jsonStr);
		return json;
	}
	public JSONObject getJsonObject() {
		if (getJsonInternal() instanceof JSONObject)
			return (JSONObject) getJsonInternal();
		else
			return null;
	}
	public JSONArray getJsonArray() {
		if (getJsonInternal() instanceof JSONArray)
			return (JSONArray) getJsonInternal();
		else
			return null;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeObject(jsonStr);
	}

	private void readObject(ObjectInputStream ois) throws IOException,
			ClassNotFoundException {
		jsonStr = (String) ois.readObject();
	}

	@Override
	public boolean isArray() {
		return getJsonInternal().isArray();
	}

	@Override
	public boolean isEmpty() {
		return getJsonInternal().isEmpty();
	}

	@Override
	public int size() {
		return getJsonInternal().size();
	}

	@Override
	public String toString(int arg0) {
		return getJsonInternal().toString(arg0);
	}

	@Override
	public String toString(int arg0, int arg1) {
		return getJsonInternal().toString(arg0, arg1);
	}

	@Override
	public Writer write(Writer arg0) {
		return getJsonInternal().write(arg0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jsonStr == null) ? 0 : jsonStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NForumJSON other = (NForumJSON) obj;
		if (jsonStr == null) {
			if (other.jsonStr != null)
				return false;
		} else if (!jsonStr.equals(other.jsonStr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getJsonObject().toString(1);

	}
}
