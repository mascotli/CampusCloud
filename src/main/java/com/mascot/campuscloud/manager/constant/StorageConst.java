package com.mascot.campuscloud.manager.constant;

public interface StorageConst {
	
	long COMMON_USER_STORAGE_SIZE = (1L << 41L);  // byte 2TB = 2199023255552B
	long COMMON_USER_STORAGE_SIZE_BYTE = (1L << 41L);  // byte 
	long COMMON_USER_STORAGE_SIZE_KIBI_BYTE = (1L << 31L);  // kibibyte
	long COMMON_USER_STORAGE_SIZE_MEBI_BYTE = (1L << 21L);  // mebibyte
	long COMMON_USER_STORAGE_SIZE_GIBI_BYTE = (1L << 11L);  // gibibyte
	long COMMON_USER_STORAGE_SIZE_TEBI_BYTE = (1L << 1L);  // tebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_PEBI_BYTE = 1024 * 1024 * 1024 * 10;  // pebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_EXBI_BYTE = 1024 * 1024 * 1024 * 10;  // exbibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_ZEBI_BYTE = 1024 * 1024 * 1024 * 10;  // zebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_YOBI_BYTE = 1024 * 1024 * 1024 * 10;  // yobibyte

}
