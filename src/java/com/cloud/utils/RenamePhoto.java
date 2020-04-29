package com.cloud.utils;

import java.util.UUID;

public class RenamePhoto {
	public static String rename(String origin) {

		StringBuffer sb = new StringBuffer(origin);
		String reverseStr = sb.reverse().toString();
		int indexOf = reverseStr.indexOf(".");
		String subString = reverseStr.substring(0,indexOf + 1);
		String str2 = new StringBuffer(subString).reverse().toString();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String newStr = str.replace("-", "");
		return newStr.concat(str2);
	}

}
