package com.framework.common.security.service;

import java.util.List;
import com.framework.base.po.Trole;
import com.framework.base.service.BaseService;
import com.framework.base.utils.Page;
import com.framework.common.security.vo.TreeNew;

public interface RoleService extends BaseService {
	public int queryCount(String rolename);
	public List<Trole> queryList(String rolename,Page p);
	public boolean getTroleByRolename(String rolename);
	public boolean getTlogintrole(String troleid);
	public void deleteTrole(String troleid);
	public List<TreeNew> getTree(String pid,String roleid);
	public void role_Save(String role,String id);
}