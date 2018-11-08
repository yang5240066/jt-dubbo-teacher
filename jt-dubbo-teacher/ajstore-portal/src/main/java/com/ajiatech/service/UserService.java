package com.ajiatech.service;

import com.ajiatech.pojo.AjiaUser;

public interface UserService {
	/**
	 * �����û�����ѯ�û�
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public AjiaUser selectByUsername(String username)
			throws Exception;
	
	/**
	 * ����û�
	 * @param ajiaUser
	 * @return
	 * @throws Exception
	 */
	public int insert (AjiaUser ajiaUser) 
			throws Exception;
}
