package com.framework.common.security.service;

import java.util.List;
import com.framework.base.po.Tparameter;
import com.framework.base.service.BaseService;
import com.framework.base.utils.Page;

public interface ParameterService extends BaseService{
	public int queryCount(String type);
	public List<Tparameter> queryList(String type,Page p);
	public boolean getParameterByType(String area,String type);
	public boolean getParameterByType(String area,String type,String tparameterid);
}
