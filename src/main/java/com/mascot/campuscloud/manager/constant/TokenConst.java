package com.mascot.campuscloud.manager.constant;

public interface TokenConst {
	
    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;
    
    /**
     * token增加保存时长（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR_ADD = 1;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";
}
