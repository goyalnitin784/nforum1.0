package com.nforum.platform.util.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class XmlDateAdapter extends XmlAdapter<String, Date> {
	
	    DateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    
	    @Override
	    public Date unmarshal(String v) throws Exception {
	        return f.parse(v);
	    }

	    @Override
	    public String marshal(Date v) throws Exception {
	        return f.format(v);
	    }
	


}
