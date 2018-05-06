package com.mascot.campuscloud.test;

import java.io.File;

public class Test {
	
	static String FILE_BASE = "CampusCloud/video/";
	
	public static void main(String[] args) {
		File dir = new File(FILE_BASE);		
		if (dir.exists()) {
			System.out.println(dir.getAbsolutePath());
		} else {
			System.out.println("mkdir " + dir.getName());
			dir.mkdirs();
			System.out.println(dir.getAbsolutePath());
		}
	}
	
}
