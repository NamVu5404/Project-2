package com.javaweb.utils;

public class NumberUtil {
	public static boolean checkNumber(String data) {
		try {
			Long.parseLong(data);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
