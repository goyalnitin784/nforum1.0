package com.nforum.platform.commons.beans;

public class UserPreferencesBean {

	private long timeStamp;
	
	private String value;

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getSearchCriteria() {
		return value.split("~",2)[1];
	}

	/*public boolean isSameSearch(UserPreferencesBean bean) {
		String key1 = bean.getValue().split("~",2)[1];
		String key2 = this.value.split("~",2)[1];
		return key2.equals(key1);
	}*/
	
}
