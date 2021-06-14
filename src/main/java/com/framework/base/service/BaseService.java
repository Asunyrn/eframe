package com.framework.base.service;

import java.io.Serializable;
import java.util.List;
import com.framework.base.po.Tparameter;
import com.framework.base.vo.ComboBox;

public interface BaseService {
	public List<ComboBox> getComboBox(String type,String isnull);
	public Tparameter getTparameter(String type);
	public Long getSeqvalue(String seq);
	public <T> void save(T t);
	public <T> void update(T t);
	public <T> void delete(T t);
	public <T> void delete(Class<T> objectClass, Serializable id);
	public <T> T get(Class<T> objectClass, Serializable id);
}
