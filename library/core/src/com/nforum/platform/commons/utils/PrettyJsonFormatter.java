package com.nforum.platform.commons.utils;

import com.nforum.platform.json.NForumJSON;
import com.nforum.platform.util.NForumUtil;

public class PrettyJsonFormatter {

	public static String format(String rawJson)
	{
		if(NForumUtil.isNullOrEmpty(rawJson))
			return "";
		return new NForumJSON(rawJson.trim()).getJsonObject().toString(1);
	}
}
