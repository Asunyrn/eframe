package com.framework.common.security.serviceImpl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import com.framework.base.po.Tparameter;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.base.utils.Page;
import com.framework.common.security.service.ParameterService;

@Service("parameterService")
public class ParameterServiceImpl extends BaseServiceImpl implements ParameterService {
	
	public int queryCount(String type){
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(t.tparameterid)  ");
		hql.append("   from Tparameter t   ");
		hql.append("  where 1 = 1  ");
		if(type!= null&&!type.equals("")){
			hql.append(" and t.type = '"+type+"' ");
	    }
		hql.append("  order by t.type ");
        return Integer.parseInt(this.getSession().createQuery(hql.toString()).uniqueResult()+"");
	}
	
	public List<Tparameter> queryList(String type,Page p){
		StringBuffer hql = new StringBuffer();
		hql.append("   from Tparameter t   ");
		hql.append("  where 1 = 1  ");
		if(type!= null&&!type.equals("")){
			hql.append(" and t.type = '"+type+"' ");
	    }
		hql.append("  order by t.type ");
		
	    Query q = this.getSession().createQuery(hql.toString());
	    q.setFirstResult(p.getStartInfo()); 
	    q.setMaxResults(p.getPageInfoCount());
	    return q.list();
	}
	public boolean getParameterByType(String area,String type){
		Query q = this.getSession().createQuery("from Tparameter t where t.area = :area and t.type = :type ");
		q.setString("area", area);
		q.setString("type", type);
		Tparameter t = (Tparameter) q.uniqueResult();
		if(t == null){
			return false;
		}else{
			return true;
		}
	}
	public boolean getParameterByType(String area,String type,String tparameterid){
		Query q = this.getSession().createQuery("from Tparameter t where t.tparameterid <> :tparameterid and t.area = :area and t.type = :type ");
		q.setString("tparameterid", tparameterid);
		q.setString("area", area);
		q.setString("type", type);
		Tparameter t = (Tparameter) q.uniqueResult();
		if(t == null){
			return false;
		}else{
			return true;
		}
	}
}
