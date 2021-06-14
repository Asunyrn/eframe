package com.framework.common.security.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.framework.base.po.Tlogin;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.base.utils.MD5;
import com.framework.common.security.service.LoginUserService;
import com.framework.common.security.vo.LoginUserVO;
import com.framework.common.security.vo.TreeAttr;
import com.framework.common.security.vo.TreeNew;

@Service("loginUserService")
public class LoginUserServiceImpl extends BaseServiceImpl implements LoginUserService {

	public LoginUserVO loginUser(String loginname,String password){
		StringBuffer sql = new StringBuffer();
		sql = sql.append(" select t.loginname,t.password,t.username, ");
		sql = sql.append("       (select t1.rolename ");
		sql = sql.append("        from   trole t1 ");
		sql = sql.append("        where  t1.troleid =(select t2.troleid from tlogintrole t2 where  t2.tloginid = t.tloginid)) ");
		sql = sql.append(" from   tLogin t ");
		sql = sql.append(" where  t.status = 1 and t.loginname = :loginname and t.password = :password ");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString("loginname", loginname);
		MD5 m = new MD5();
		query.setString("password",  m.getMD5ofStr(password));
		List<Object[]> list = query.list();
		if(list.size()>0){
			Object[] u = list.get(0);
			LoginUserVO vo = new LoginUserVO();
			vo.setLoginname(u[0].toString());
			vo.setPassword(u[1].toString());
			vo.setUsername(u[2].toString());
			vo.setRolename(u[3].toString());
		    return vo ;
		}else{
			return null;			
		}
	}
	
	public List<TreeNew> getUserTree(String rolename, String pid) {
		StringBuffer sql = new StringBuffer();
		sql = sql.append(" select a.tpermissionid,a.permissionname,a.isleaf,a.url,a.orders,a.parentid ");  
		sql = sql.append("   from troletpermission t ");
		sql = sql.append("   left join tpermission a on a.tpermissionid = t.tpermissionid ");
		sql = sql.append("  where t.troleid in (select b.troleid from trole b where b.rolename = :rolename) and a.parentid = :parentid ");
		sql = sql.append("  order by a.orders ");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString("rolename", rolename);
		query.setString("parentid", pid);
		List<Object[]> list = query.list();
		List<TreeNew> list_p = new ArrayList<TreeNew>();
		for (int i = 0; i < list.size(); i++) {
			Object[] o = list.get(i);
			TreeNew v = new TreeNew();
			TreeAttr attr = new TreeAttr();
			v.setId(o[0].toString());
			v.setText(o[1].toString());
			v.setState((o[2].toString()).equals("1") ? "" : "closed");
			attr.setIsleaf((o[2].toString()).equals("1") ? true : false);
			attr.setUrl(o[3].toString());
			attr.setParentid(o[5].toString());
			v.setAttributes(attr);
			list_p.add(v);
		}
		return list_p;
	}
	
	public Tlogin getPasssWordOld(String loginname , String password_old){
		MD5 md5 = new MD5();
		Query q = this.getSession().createQuery(" from Tlogin t where t.loginname = :loginname and t.password = :password ");
        q.setString("loginname", loginname);
        q.setString("password", md5.getMD5ofStr(password_old));
        return (Tlogin)q.uniqueResult();
	}
	
}
