package com.framework.common.security.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.framework.base.po.Trole;
import com.framework.base.po.TroleTpermission;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.base.utils.Page;
import com.framework.common.security.service.RoleService;
import com.framework.common.security.vo.TreeAttr;
import com.framework.common.security.vo.TreeNew;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService{
	public int queryCount(String rolename){
		Session session = this.getSession();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(t.troleid)  ");
		sql.append("   from Trole t   ");
		if(rolename!= null&&!rolename.equals("")){
			sql.append(" where t.rolename = '"+rolename+"' ");
	    }
        return Integer.parseInt(session.createSQLQuery(sql.toString()).uniqueResult()+"");
	}
	public List<Trole> queryList(String rolename,Page p){
		Session session = this.getSession();
		StringBuffer hql = new StringBuffer();
		hql.append("   from Trole t   ");
		if(rolename!= null&&!rolename.equals("")){
			hql.append(" where  t.rolename = '"+rolename+"' ");
	    }
	    Query q = session.createQuery(hql.toString());
	    q.setFirstResult(p.getStartInfo()); 
	    q.setMaxResults(p.getPageInfoCount());
	    return q.list();
	}
	public boolean getTroleByRolename(String rolename){
		Session session = this.getSession();
		Query q = session.createQuery("from Trole t where t.rolename = :rolename  ");
		q.setString("rolename", rolename);
		Trole t = (Trole) q.uniqueResult();
		if(t == null){
			return false;
		}else{
			return true;
		}
	}
	public boolean getTlogintrole(String troleid){
		Session session = this.getSession();
		Query q = session.createQuery("select count(t.tlogintroleid) from TloginTrole t where t.troleid = :troleid  ");
		q.setString("troleid", troleid);
	    String count = q.uniqueResult()+"";
		if(count.equals("0")){
			return false;
		}else{
			return true;
		}
	}
	public void deleteTrole(String troleid){
		Session session = this.getSession();
		Trole t = (Trole) session.get(Trole.class, troleid);
		session.delete(t);
		Query query = session.createQuery(" delete from TroleTpermission where troleid = :troleid  ");
		query.setString("troleid", troleid);
		query.executeUpdate();
	}
	public List<TreeNew> getTree(String pid,String roleid) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select a.tpermissionid,a.permissionname,a.isleaf,a.url,a.parentid,b.troleid    ");
		sql.append(" from tpermission a ");
		sql.append(" left join troletpermission b ");
		sql.append(" on a.tpermissionid = b.tpermissionid and b.troleid = :troleid ");
		sql.append(" where a.parentid = :parentid ");
		sql.append(" order by a.orders  ");
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setString("troleid", roleid);
		query.setString("parentid", pid);
		List<Object[]> list = query.list();
		List<TreeNew> list_p = new ArrayList<TreeNew>();
		for(int i = 0;i<list.size();i++){
			Object[] o= list.get(i);
			TreeNew v = new TreeNew();
			v.setId(o[0].toString());
			v.setText(o[1].toString());
			v.setState((o[2].toString()+"").equals("1") ? "" : "closed");
			v.setChecked(o[5] == null ? false : true);
			TreeAttr attr = new TreeAttr();
			attr.setIsleaf((o[2].toString()+"").equals("1") ? true : false);
			attr.setUrl(o[3].toString());
			attr.setParentid(o[4].toString());
			v.setAttributes(attr);
			list_p.add(v);
		}
		return list_p;
	}
	public void role_Save(String role,String id){
		String [] tpermissionid = role.split(",");
		Session session = this.getSession();
		Query query = session.createQuery(" delete from TroleTpermission where troleid = :troleid ");
		query.setString("troleid", id);
		query.executeUpdate();
		for(int i= 0;i<tpermissionid.length;i++){
			TroleTpermission t = new TroleTpermission();
			t.setTroleid(id);
			t.setTpermissionid(tpermissionid[i]);
			session.save(t);
		}
	}
}
