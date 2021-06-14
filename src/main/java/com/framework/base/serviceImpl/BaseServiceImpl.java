package com.framework.base.serviceImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.framework.base.dao.BaseDao;
import com.framework.base.po.Tparameter;
import com.framework.base.service.BaseService;
import com.framework.base.vo.ComboBox;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("baseService")
public class BaseServiceImpl implements BaseService{
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Session getSession(){
		return baseDao.getSession();
	}
	
	//获取字典
	public List<ComboBox> getComboBox(String type,String isnull){
		Query q = this.getSession().createSQLQuery(" select t.code,t.value from v_tdictionary t where t.type = :type  order  by t.code   ");
		q.setString("type",type );
		List<Object[]> list = q.list();
		List<ComboBox> listComboBox = new ArrayList<ComboBox>();
		//添加空值
		if(isnull != null&&isnull.equals("true")){
			ComboBox c2 = new ComboBox();
			c2.setId("");
			c2.setText("请选择");
			listComboBox.add(c2);
		}

		for(Object[] o : list){
			ComboBox c = new ComboBox();
			c.setId(o[0].toString());
			c.setText(o[1].toString());
			listComboBox.add(c);
		}
		
		return listComboBox;
	}
	
    //获取系统参数
	public Tparameter getTparameter(String type){
		Query q = this.getSession().createQuery("from tparameter t where t.type = :type ");
		q.setString("type", type);
		return (Tparameter)q.uniqueResult();
	}

    //获取序列
	public Long getSeqvalue(String seq){
		BigDecimal seqvalue = (BigDecimal) this.getSession().createSQLQuery("select "+seq+".nextval from dual").list().get(0);
		return seqvalue.longValue();
	}
	
	public <T> void save(T t){
		Session session = this.getSession();
		session.save(t);
		session.flush();
	}
	
	public <T> void update(T t){
		Session session = this.getSession();
		session.update(t);
		session.flush();
	}
	
	public <T> void delete(T t){
		Session session = this.getSession();
		session.delete(t);
		session.flush();
	}
	
	public <T> void delete(Class<T> objectClass, Serializable id){
		Session session = this.getSession();
		T t = (T) this.get(objectClass, id);
		session.delete(t);
		session.flush();
	}
	
	public <T> T get(Class<T> objectClass, Serializable id){
		Session session = this.getSession();
		return (T) session.get(objectClass, (Serializable) id);
	}
}
