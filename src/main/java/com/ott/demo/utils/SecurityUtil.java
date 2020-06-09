package com.ott.demo.utils;

import org.springframework.util.StringUtils;

public final class SecurityUtil {

	private static final String BASIC_AUTH = "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=";

	private SecurityUtil() {

	}

	public static boolean isValidAuthorization(String value) {
		if (!StringUtils.isEmpty(value) && value.equals(BASIC_AUTH)) {
			return true;
		}
		return false;
	}

}