package com.mascot.campuscloud.manager;

public class StringUtils {
	
	public static boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}
	
	public static boolean isNotEmpty(Object str) {
		return !(org.springframework.util.StringUtils.isEmpty(str));
	}
	
	public static boolean hasLength(String str) {
		return org.springframework.util.StringUtils.hasLength(str);
	}
	
	public static boolean hasText(CharSequence str) {
		return org.springframework.util.StringUtils.hasText(str);
	}
	
	public static boolean hasText(String str) {
		return org.springframework.util.StringUtils.hasText(str);
	}
	
	public static boolean containsWhitespace(CharSequence str) {
		return org.springframework.util.StringUtils.containsWhitespace(str);
	}

}
