package com.mascot.campuscloud.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.stereotype.Repository;

import com.mascot.campuscloud.dao.entity.FileDO;
import com.mascot.campuscloud.dao.entity.UserDO;
import com.mascot.campuscloud.manager.hadoop.HdfsConn;

import lombok.Cleanup;

@Repository("hdfsDAO")
public class HdfsDAO {

	private final static String basePath = "/CampusCloud/";

	/*
	 * 获得文件/文件夹在hdfs中的目录
	 * 
	 * @Param userId
	 * 
	 * @Param fileDigest
	 * 
	 * @return
	 */
	private String formatPathMethod(Long userId, String file) {
		return basePath + userId + File.pathSeparator + file;
	}

	/**
	 * 上传文件
	 * 
	 * @param inputStream
	 * @param file
	 * @param user
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void put(InputStream inputStream, FileDO file, UserDO user) throws IllegalArgumentException, IOException {
		String formatPath = formatPathMethod(user.getId(), file.getType() + File.pathSeparator + file.getMd5());
		OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(formatPath), new Progressable() {
			@Override
			public void progress() {
				// System.out.println("upload OK");
			}
		});
		IOUtils.copyBytes(inputStream, outputStream, 2048, true);
	}

	public void mkDir(FileDO file, UserDO user) throws IllegalArgumentException, IOException {
		String formatPath = formatPathMethod(user.getId(), file.getType());
		if (!HdfsConn.getFileSystem().exists(new Path(formatPath))) {
			HdfsConn.getFileSystem().mkdirs(new Path(formatPath));
		}
	}

	public void delete(FileDO file, UserDO user) throws IllegalArgumentException, IOException {
		String formatPath = formatPathMethod(user.getId(), file.getType() + File.pathSeparator + file.getMd5());
		if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
			// hdfs文件删除并不会立即删除库中的文件，仅仅将文件转移到/trash目录中
			HdfsConn.getFileSystem().delete(new Path(formatPath), true);
		}
	}

	public void rename(FileDO file, UserDO user, String newname) throws IllegalArgumentException, IOException {
		String formatPath = formatPathMethod(user.getId(), file.getType() + File.pathSeparator + file.getMd5());
		String newformatPath = formatPathMethod(user.getId(), file.getType() + File.pathSeparator + newname);
		if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
			HdfsConn.getFileSystem().rename(new Path(formatPath), new Path(newformatPath));
		}
	}

	public boolean download(UserDO user, FileDO file, String local) {
		try {
			String formatPath = formatPathMethod(user.getId(), file.getType() + File.pathSeparator + file.getMd5());
			if (HdfsConn.getFileSystem().exists(new Path(formatPath))) {
				@Cleanup FSDataInputStream inputStream = HdfsConn.getFileSystem().open(new Path(formatPath));
				@Cleanup OutputStream outputStream = new FileOutputStream(local);
				IOUtils.copyBytes(inputStream, outputStream, 4096, true);
				System.out.println(local);
				return true;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void copyOrMove(UserDO user, FileDO sourceFile, FileDO destFile, boolean flag)
			throws IllegalArgumentException, IOException {
		String sourceFormatPath = formatPathMethod(user.getId(),
				sourceFile.getType() + File.pathSeparator + sourceFile.getMd5());
		String destFormatPath = formatPathMethod(user.getId(),
				destFile.getType() + File.pathSeparator + destFile.getMd5());
		FileUtil.copy(HdfsConn.getFileSystem(), new Path(sourceFormatPath), HdfsConn.getFileSystem(),
				new Path(destFormatPath), flag, true, HdfsConn.getConfiguration());
	}

}
