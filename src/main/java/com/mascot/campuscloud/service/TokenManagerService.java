package com.mascot.campuscloud.service;

import com.mascot.campuscloud.dao.entity.TokenModelDO;
import com.mascot.campuscloud.manager.exception.TokenErrorException;

public interface TokenManagerService {

    /**
     * 创建一个token关联上指定用户
     * @param username 指定用户的id
     * @return 生成的token
     */
    public TokenModelDO createToken(String username);

    /**
     * 设置token
     * @param username 指定用户的id
     * @return 生成的token
     */
	public void setToken(TokenModelDO token) throws TokenErrorException ;
		
    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(TokenModelDO model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public TokenModelDO getToken(String authentication);

    /**
     * 清除token
     * @param username 登录用户的id
     */
    public void deleteToken(String username);
    
}
