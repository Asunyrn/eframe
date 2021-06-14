package com.framework.common.security.service;

import java.util.List;
import com.framework.base.po.Tlogin;
import com.framework.base.service.BaseService;
import com.framework.common.security.vo.LoginUserVO;
import com.framework.common.security.vo.TreeNew;

public interface LoginUserService extends BaseService{
	public LoginUserVO loginUser(String loginname,String password);
	public List<TreeNew> getUserTree(String rolename,String pid);
	public Tlogin getPasssWordOld(String loginname , String password_old);
}
