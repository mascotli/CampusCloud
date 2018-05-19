package com.mascot.campuscloud.manager.hadoop;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HdfsConn {
	private FileSystem fileSystem = null;
	private Configuration configuration = null;
	private String hdfsuri = "hdfs://godfather.mascot.com.cn:9000/";

	private static class SingletonHolder {
		private static final HdfsConn INSTANCE = new HdfsConn();
	}

	private HdfsConn() {
		try {
			// ====== Init HDFS File System Object
			configuration = new Configuration();
			/* 远程访问hdfs */
			// Set FileSystem URI
			configuration.set("fs.defaultFS", hdfsuri);
			// Because of Maven
			configuration.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
			// configuration.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
			// Get the filesystem - HDFS
			URI uri = URI.create("hdfs://godfather.mascot.com.cn:9000/");
			fileSystem = FileSystem.get(uri, configuration, "root");

			/* 本地有hadoop环境 */
			// configuration.set("fs.default.name", "hdfs://godfather.mascot.com.cn:9000/");
			// fileSystem = FileSystem.get(configuration);
		} catch (IOException | InterruptedException e) {
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
		final FileSystem hdfs = getFileSystem();
		System.out.println(hdfs);
	}
}
