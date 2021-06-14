package com.framework.common.security.service;

import java.util.List;
import com.framework.base.po.Tlogin;
import com.framework.base.po.TloginTrole;
import com.framework.base.service.BaseService;
import com.framework.base.utils.Page;
import com.framework.common.security.vo.TloginVo;

public interface UserService extends BaseService{
	public int getTloginSum(String loginname,String username,String status,String trole);
	public List<TloginVo> listTlogin(String loginname,String username,String status,String trole,Page p);
	public void saveTlogin(Tlogin t,TloginTrole tr);
	public void updateTlogin(Tlogin t,String troleid);
	public String deleteTlogin(String id);
	public boolean getUserByLoginname(String loginname);
}
