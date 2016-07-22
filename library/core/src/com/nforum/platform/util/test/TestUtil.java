package com.nforum.platform.util.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

public class TestUtil {

	static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
	static SimpleDateFormat sdfDateOnly = new SimpleDateFormat("ddMMyyyy");
	
	public static void assertSame(Date d1, Date d2)
	{
		assertSame(d1, d2,false);
	}

	public static void assertSame(Date d1, Date d2, boolean dateOnly)
	{
		String d1Str = dateOnly?sdfDateOnly.format(d1):sdf.format(d1);
		String d2Str = dateOnly?sdfDateOnly.format(d2):sdf.format(d2);
		Assert.assertEquals(d1Str, d2Str);
	}

}