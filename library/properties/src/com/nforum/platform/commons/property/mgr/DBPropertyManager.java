package com.nforum.platform.commons.property.mgr;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.nforum.platform.commons.property.PropertyChangeListener;
import com.nforum.platform.commons.property.PropertyManagerSimple;

public class DBPropertyManager implements PropertyManagerSimple, PropertyChangeListener {

	private HibernateTemplate hibernateTemplate; // the only mandatory input
	private String tableName="property";
	private String keyColumn="name";
	private String valueColumn="value";
	private boolean shouldCache=true;
	private String clientPropertyPrefix=null;

	private Map<String,String> cache = new HashMap<String,String>();
	private static final Logger logger = Logger.getLogger(DBPropertyManager.class);
	

	@Override
	public String getProperty(String key) {
		
		//If client property prefix has been configured, I will proceed only if the
		//given key starts with that prefix
		if(clientPropertyPrefix !=null)
		{
			if(key.startsWith(clientPropertyPrefix))
				key = key.substring(clientPropertyPrefix.length());
			else
				return null;
		}
		else
			return null;
		
		if(shouldCache && cache.containsKey(key))
			return cache.get(key);
		
		String value = getPropertyFromDB(key);

		if(shouldCache)
			cache.put(key, value);
		
		return value;
	}

	private String getPropertyFromDB(final String key) {

        @SuppressWarnings({ "unchecked", "rawtypes" })
		List results = (List)		
		hibernateTemplate.execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(
						"select " + valueColumn 
						+ " from " + tableName 
						+ " where " + keyColumn 
						+" = '" + key +"'");
				return query.list();
			}
		});
        
        return (results.size()>0)? (String)results.get(0): null; 
	}

	@Override
	public void propertyChanged() {
		logger.info("Clearing database property cache");
		cache.clear();
	}

	/////////////////////////////////////////////
	// Getters and Setters beyond this point
	//////////////////////////////////////////////
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public boolean isShouldCache() {
		return shouldCache;
	}

	public void setShouldCache(boolean shouldCache) {
		this.shouldCache = shouldCache;
	}

	public String getClientPropertyPrefix() {
		return clientPropertyPrefix;
	}

	public void setClientPropertyPrefix(String clientPropertyPrefix) {
		this.clientPropertyPrefix = clientPropertyPrefix;
	}
}
