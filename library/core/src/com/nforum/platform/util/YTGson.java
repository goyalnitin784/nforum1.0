package com.nforum.platform.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YTGson {

	public static Gson axisGson() {
		return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			public boolean shouldSkipField(FieldAttributes f) {
				if (f.getName().equals("__equalsCalc") || f.getName().equals("__hashCodeCalc"))
					return true;
				else
					return false;
			}

		}).create();
	}
}
