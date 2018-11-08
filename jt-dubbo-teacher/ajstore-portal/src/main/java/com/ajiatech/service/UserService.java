package com.ajiatech.service;

import com.ajiatech.pojo.AjiaUser;

public interface UserService {
	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public AjiaUser selectByUsername(String username)
			throws Exception;
	
	/**
	 * 添加用户
	 * @param ajiaUser
	 * @return
	 * @throws Exception
	 */
	public int insert (AjiaUser ajiaUser) 
			throws Exception;
}
