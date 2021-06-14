package com.framework.common.security.service;

import java.util.List;
import com.framework.base.po.Tdictionary;
import com.framework.base.service.BaseService;
import com.framework.base.utils.Page;

public interface DictionaryService extends BaseService{
	public int queryCount(String type,String code,String value);
	public List<Tdictionary> queryList(String type,String code,String value,Page p);
	public boolean getDictionaryByType(String type,String code,String value);
}
