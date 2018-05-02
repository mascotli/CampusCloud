package com.mascot.campuscloud.manager.constant;

public interface StorageConst {
	
	@Deprecated long COMMON_USER_STORAGE_SIZE = (1 << 40) * 2;  // byte bug here
	@Deprecated long COMMON_USER_STORAGE_SIZE_BYTE = 1024 * 1024 * 1024 * 1024 * 2;  // byte
	@Deprecated long COMMON_USER_STORAGE_SIZE_KIBI_BYTE = 1024 * 1024 * 1024 * 2;  // kibibyte
	long COMMON_USER_STORAGE_SIZE_MEBI_BYTE = 1024 * 1024 * 2;  // mebibyte
	long COMMON_USER_STORAGE_SIZE_GIBI_BYTE = 1024 * 2;  // gibibyte
	long COMMON_USER_STORAGE_SIZE_TEBI_BYTE = 2;  // tebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_PEBI_BYTE = 1024 * 1024 * 1024 * 10;  // pebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_EXBI_BYTE = 1024 * 1024 * 1024 * 10;  // exbibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_ZEBI_BYTE = 1024 * 1024 * 1024 * 10;  // zebibyte
	@Deprecated long COMMON_USER_STORAGE_SIZE_YOBI_BYTE = 1024 * 1024 * 1024 * 10;  // yobibyte

}
