package com.framework.common.security.serviceImpl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import com.framework.base.po.Tdictionary;
import com.framework.base.serviceImpl.BaseServiceImpl;
import com.framework.base.utils.Page;
import com.framework.common.security.service.DictionaryService;

@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseServiceImpl implements DictionaryService {
	
	public int queryCount(String type,String code,String value){
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(t.tdictionaryid)  ");
		hql.append("   from Tdictionary t   ");
		hql.append("  where 1 = 1  ");
		if(type!= null&&!type.equals("")){
			hql.append(" and t.type = '"+type+"' ");
	    }
		if(code!= null&&!code.equals("")){
			hql.append(" and t.code = '"+code+"' ");
	    }
		if(value!= null&&!value.equals("")){
			hql.append(" and t.vaule = '"+value+"' ");
	    }
		hql.append("  order by t.type,t.code ");
        return Integer.parseInt(this.getSession().createQuery(hql.toString()).uniqueResult()+"");
	}
	
	public List<Tdictionary> queryList(String type,String code,String value,Page p){
		StringBuffer hql = new StringBuffer();
		hql.append("   from Tdictionary t   ");
		hql.append("  where 1 = 1  ");
		if(type!= null&&!type.equals("")){
			hql.append(" and t.type = '"+type+"' ");
	    }
		if(code!= null&&!code.equals("")){
			hql.append(" and t.code = '"+code+"' ");
	    }
		if(value!= null&&!value.equals("")){
			hql.append(" and t.vaule = '"+value+"' ");
	    }
		hql.append("  order by t.type,t.code ");
		
	    Query q = this.getSession().createQuery(hql.toString());
	    q.setFirstResult(p.getStartInfo()); 
	    q.setMaxResults(p.getPageInfoCount());
	    return q.list();
	}
	public boolean getDictionaryByType(String type,String code,String value){
		Query q = this.getSession().createQuery("from Tdictionary t where t.type = :type and t.code = :code and t.value= :value ");
		q.setString("type", type);
		q.setString("code", code);
		q.setString("value", value);
		Tdictionary t = (Tdictionary) q.uniqueResult();
		if(t == null){
			return false;
		}else{
			return true;
		}
	}
}
