package com.framework.base.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.framework.base.dao.BaseDao;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao{
	@Resource(name="template")
	private HibernateTemplate template;
	
	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
	
	public Session getSession(){
		return template.getSessionFactory().getCurrentSession();
	}
}
