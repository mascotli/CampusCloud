package com.mascot.campuscloud.manager;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	private static final String staticSalt = "   ffms   " + "   @#@^&%&^%$^$#(*(*&*^&?~+/,<>><   "
			+ "   campuscloud   ";

	/*
	 * md5
	 */
	public static String encode(String message) {
		return DigestUtils.md5Hex(message + staticSalt);
	}

	/**
	 * 判断用户输入的密码是否正确
	 * 
	 * @param userPwd
	 *            当前输入的密码
	 * @param dbPwd
	 *            数据库中存储的密码
	 * @return true:输入正确 false：输入错误
	 */
	public static boolean isPwdRight(String userPwd, String dbPwd) {
		boolean rs = false;
		if (encode(userPwd).equals(dbPwd)) {
			rs = true;
		}
		return rs;
	}

	public static void main(String[] args) {
		System.out.println("xx=" + MD5Util.encode("123456"));
		System.out.println("yy=" + MD5Util.isPwdRight("123456", "cb323f94f7575f0c5cee65521507e1da"));
	}

}