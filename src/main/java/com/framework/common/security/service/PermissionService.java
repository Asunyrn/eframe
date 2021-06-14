package com.framework.common.security.service;

import java.util.List;
import com.framework.base.service.BaseService;
import com.framework.common.security.vo.TreeNew;

public interface PermissionService extends BaseService{
	public List<TreeNew> getTree(String pid);
	public boolean getTpermissionByPermissionname(String permissionname);
	public boolean getByPermissionname(String permissionname,String tpermissionid);
	public boolean getTroleTpermission(String tpermissionid );
	public boolean getSunTpermissionCount(String tpermissionid );
}
