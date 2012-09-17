package com.binomed.sqli.gwt.client.utils;

public final class StringUtils {

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean equals(String string1, String string2) {
		if (string1 != null && string2 != null) {
			return string1.equals(string2);
		} else {
			return false;
		}

	}

}
