package com.mascot.campuscloud.manager.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HdfsConn {
	private FileSystem fileSystem = null;
	private Configuration configuration = null;
	
	private static class SingletonHolder {
		private static final HdfsConn INSTANCE = new HdfsConn();
	}

	private HdfsConn() {
		try {
			configuration = new Configuration();
			configuration.set("fs.defaultFS", "hdfs://godfather.mascot.com.cn:9000/");
			fileSystem = FileSystem.get(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileSystem getFileSystem() {
		return SingletonHolder.INSTANCE.fileSystem;
	}
	
	public static Configuration getConfiguration() {
		return SingletonHolder.INSTANCE.configuration;
	}
	
	public static void main(String[] args) {
		// final FileSystem hdfs = getFileSystem();
		System.out.println(getFileSystem());
	}
}
