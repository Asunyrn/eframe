package com.framework.common.security.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.framework.base.po.Tlogin;
import com.framework.base.po.TloginTrole;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.base.utils.MD5;
import com.framework.base.utils.Page;
import com.framework.common.security.service.UserService;
import com.framework.common.security.vo.TloginVo;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService{

	public int getTloginSum(String loginname,String username,String status,String trole){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(t.tloginid) ");
		sql.append("  from tlogin t  ");
		sql.append("  left join tlogintrole a on a.tloginid = t.tloginid ");
		sql.append(" where 1 = 1 ");
		if(loginname!= null&&!loginname.equals("")){
			sql.append(" and t.loginname = '"+loginname+"' ");
	    }
        if(username!= null&&!username.equals("")){
        	sql.append(" and t.username = '"+username+"' ");
	    }
        if(trole!= null&&!trole.equals("")){
        	sql.append(" and a.troleid = "+trole+" ");
	    }
        if(status!= null&&!status.equals("")){
        	sql.append(" and t.status = "+status+" ");
	    }
        return Integer.parseInt(this.getSession().createSQLQuery(sql.toString()).uniqueResult()+"");
	}
	
	public List<TloginVo> listTlogin(String loginname,String username,String status,String trole,Page p){
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.tloginid,t.loginname,t.username,t.password,t.registrationuser, ");
		sql.append("        to_char(t.registrationtime,'yyyy-mm-dd'),t.status,a.troleid ");
		sql.append("  from tlogin t  ");
		sql.append("  left join tlogintrole a on a.tloginid = t.tloginid ");
		sql.append(" where 1 = 1 ");
		if(loginname!= null&&!loginname.equals("")){
			sql.append(" and t.loginname = '"+loginname+"' ");
	    }
        if(username!= null&&!username.equals("")){
        	sql.append(" and t.username = '"+username+"' ");
	    }
        if(trole!= null&&!trole.equals("")){
        	sql.append(" and a.troleid = "+trole+" ");
	    }
        if(status!= null&&!status.equals("")){
        	sql.append(" and t.status = "+status+" ");
	    }
        sql.append(" order by t.tloginid ");
	    Query q = this.getSession().createSQLQuery(sql.toString());
	    q.setFirstResult(p.getStartInfo()); 
	    q.setMaxResults(p.getPageInfoCount());
	    List<TloginVo> list = new ArrayList<TloginVo>();
	    for(Iterator<?> iterator = q.list().iterator();iterator.hasNext();){
	    	Object[] obj = (Object[]) iterator.next();
	    	TloginVo vo = new TloginVo();
	    	vo.setTloginid(obj[0].toString());
	    	vo.setLoginname(obj[1].toString());
	    	vo.setUsername(obj[2].toString());
	    	vo.setPassword(obj[3].toString());
	    	vo.setRegistrationuser(obj[4].toString());
	    	vo.setRegistrationtime(obj[5].toString());
	    	vo.setStatus(obj[6].toString());
	    	vo.setTroleid(obj[7].toString());
	    	list.add(vo);
	    }
	    return list;
	}
	public void saveTlogin(Tlogin t,TloginTrole tr){
		Session session = this.getSession();
		MD5 m = new MD5();
		t.setPassword(m.getMD5ofStr(t.getPassword()));
		session.save(t);
		tr.setTloginid(t.getTloginid());
		session.save(tr);
	}
	public void updateTlogin(Tlogin t,String troleid){
		Session session = this.getSession();
		Tlogin t1 = (Tlogin) session.get(Tlogin.class, t.getTloginid());
		t1.setUsername(t.getUsername());
		t1.setStatus(t.getStatus());
		session.update(t1);
		Query q = session.createQuery(" update  TloginTrole t set t.troleid = :troleid where t.tloginid = :tloginid  ");
		q.setString("troleid", troleid);
		q.setString("tloginid", t.getTloginid());
		q.executeUpdate();
	}
	public String deleteTlogin(String id){
		Session session = this.getSession();
		Query q = session.createQuery(" delete from TloginTrole t  where t.tloginid = :tloginid ");
		q.setString("tloginid", id);
		q.executeUpdate();
		
		Tlogin t = (Tlogin) session.get(Tlogin.class, id);
		session.delete(t);
		return "";
	}
	public boolean getUserByLoginname(String loginname){
		Session session = this.getSession();
		Query q = session.createQuery("from Tlogin t where t.loginname = :loginname ");
		q.setString("loginname", loginname);
		Tlogin t = (Tlogin) q.uniqueResult();
		if(t==null){
			return false;
		}else{
			return true;
		}
	}
}
