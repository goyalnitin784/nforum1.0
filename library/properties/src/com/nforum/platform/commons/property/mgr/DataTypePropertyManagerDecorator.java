package com.nforum.platform.commons.property.mgr;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nforum.platform.commons.property.PropertyManager;
import com.nforum.platform.commons.property.PropertyManagerSimple;
/**
 * A decorator around simple proprty manager that serves parsed data out ouf string
 * properties
 *
 */
@Component("propertyManager")
public class DataTypePropertyManagerDecorator implements PropertyManager {

	Logger logger = Logger.getLogger(DataTypePropertyManagerDecorator.class);

	@Autowired(required=false)
	@Qualifier("simplePropertyManager")
	PropertyManagerSimple properties;

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

    @Override
    public Integer getPropertyAsInt(String key) {
        Integer retValue;
        try {
            retValue = Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException nfExp) {
        	logger.error("integer property not found - " + key);
            return 0;
        }
        return retValue;
    }

    @Override
    public Long getPropertyAsLong(String key) {
        Long retValue;
        try {
            retValue = Long.parseLong(properties.getProperty(key));
        } catch (NumberFormatException nfExp) {
            return 0L;
        }
        return retValue;
    }


    @Override
    public Double getPropertyAsDouble(String key) {
        Double retValue;
        try {
            retValue = Double.parseDouble(properties.getProperty(key));
        } catch (NumberFormatException nfExp) {
        	logger.error("double property not found - " + key);
            return 0D;
        }
        return retValue;
    }

    @Override
    public Boolean getPropertyAsBoolean(String key) {
    	String objProperty = properties.getProperty(key);
    	if(objProperty!=null)
    		return new Boolean(objProperty);
    	else
    		return false;
    }

    @Override
    public Byte getPropertyAsByte(String key) {
        Byte retValue;
        try {
            retValue = Byte.parseByte(properties.getProperty(key));
        } catch (NumberFormatException nfExp) {
        	logger.error("double property not found - " + key);
            return null;
        }
        return retValue;
    }

	@Override
	public String[] getPropertyAsStringArray(String key) {
		String stringProp = properties.getProperty(key);
		if(stringProp == null){
			return null;
		}
		String[] strPieces = stringProp.split(",");
		return strPieces;
	}

	@Override
	public Boolean[] getPropertyAsBooleanArray(String key) {
		String stringProp = properties.getProperty(key);
		String[] strPieces = stringProp.split(",");
		Boolean [] boolPieces = new Boolean[strPieces.length];
		for (int i = 0; i < strPieces.length; i++) {
			boolPieces[i] = Boolean.parseBoolean(strPieces[i]);
		}
		return boolPieces;
	}

	@Override
	public Integer[] getPropertyAsIntArray(String key) {
		String stringProp = properties.getProperty(key);
		String[] strPieces = stringProp.split(",");
		Integer [] intPieces = new Integer[strPieces.length];
		for (int i = 0; i < strPieces.length; i++) {
			intPieces[i] = Integer.parseInt(strPieces[i]);
		}
		return intPieces;
	}

	@Override
	public String getNonNullProperty(String key) {
		if(key==null || getProperty(key)==null)
			return "";
		return getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String retValue = getNonNullProperty(key);
		if(retValue.isEmpty())
			return defaultValue;
		return retValue;
	}

	@Override
	public List<String> getPropertyAsList(String key) {
		List<String> list=new ArrayList<String>();
		String stringProp = properties.getProperty(key);
		if(stringProp == null){
			return null;
		}
		String[] strPieces = stringProp.split(",");
		for(int i=0; i < strPieces.length;i++){
			list.add(strPieces[i]);
		}

		return list;
	}
}
