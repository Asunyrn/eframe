package com.framework.common.security.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.framework.base.po.Tpermission;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.common.security.service.PermissionService;
import com.framework.common.security.vo.TreeAttr;
import com.framework.common.security.vo.TreeNew;

@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {
	
	public boolean getTpermissionByPermissionname(String permissionname){
		Session session = this.getSession();
		Query q = session.createQuery("select count(t.tpermissionid) from Tpermission t where t.permissionname = :permissionname  ");
		q.setString("permissionname", permissionname);
		String count = q.uniqueResult() + "";
		if (count.equals("0")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getByPermissionname(String permissionname,String tpermissionid){
		Session session = this.getSession();
		Query q = session.createQuery("select count(t.tpermissionid) from Tpermission t where t.permissionname = :permissionname  and t.tpermissionid != :tpermissionid ");
		q.setString("permissionname", permissionname);
		q.setString("tpermissionid", tpermissionid);
		String count = q.uniqueResult() + "";
		if (count.equals("0")) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<TreeNew> getTree(String pid) {
		StringBuffer hql = new StringBuffer();
		hql.append("  from Tpermission t ");
		hql.append(" where t.parentid = :parentid ");
		hql.append(" order by t.orders  ");
		Session session = this.getSession();
		Query query = session.createQuery(hql.toString());
		query.setString("parentid", pid);
		List<Tpermission> list = query.list();
		List<TreeNew> list_p = new ArrayList<TreeNew>();
		for(Iterator<?> iterator = list.iterator();iterator.hasNext();){
			Tpermission t = (Tpermission) iterator.next();
			TreeNew v = new TreeNew();
			v.setId(t.getTpermissionid());
			v.setText(t.getPermissionname());
			v.setState((t.getIsleaf()+"").equals("1") ? "" : "closed");
			TreeAttr attr = new TreeAttr();
			attr.setIsleaf((t.getIsleaf()+"").equals("1") ? true : false);
			attr.setUrl(t.getUrl());
			attr.setParentid(t.getParentid());
			v.setAttributes(attr);
			list_p.add(v);
	    }
		return list_p;
	}
	
	public boolean getTroleTpermission(String tpermissionid ){
		Session session = this.getSession();
		Query q = session.createQuery("select count(t.troleTpermissionid) from TroleTpermission t where t.tpermissionid = :tpermissionid  ");
		q.setString("tpermissionid", tpermissionid);
	    String count = q.uniqueResult()+""; 
		if(count.equals("0")){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean getSunTpermissionCount(String tpermissionid ){
		Session session = this.getSession();
		Query q = session.createQuery(" select count(t.tpermissionid) from Tpermission t where t.parentid = :parentid ");
		q.setString("parentid", tpermissionid);
	    String count = q.uniqueResult()+""; 
		if(count.equals("0")){
			return false;
		}else{
			return true;
		}
	}
}
