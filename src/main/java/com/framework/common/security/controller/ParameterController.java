package com.framework.common.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.base.po.Tparameter;
import com.framework.base.utils.Page;
import com.framework.common.security.service.ParameterService;

@Controller
@RequestMapping("/parameterController")
public class ParameterController {
	
	@Resource(name="parameterService")
	private ParameterService parameterService;
	
	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	@RequestMapping(value = "gridform1.do")
	@ResponseBody
	public Object gridform1(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String type = request.getParameter("type");
		//����
		int sum = parameterService.queryCount(type);
		//��ҳ��Ϣ
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Page p = new Page();
		p.setIntPage(Integer.parseInt(page));
		p.setPageInfoCount(Integer.parseInt(rows));
		//��ѯ��Ϣ
		List<Tparameter> list = parameterService.queryList(type, p) ;
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("total",sum);  
        result.put("rows",list) ;
        return result;
	}
	
	@RequestMapping(value = "save.do")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String area = request.getParameter("area_dlg");
		String type = request.getParameter("type_dlg");
		String value = request.getParameter("value_dlg");
		String remarks = request.getParameter("remarks_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(parameterService.getParameterByType(area,type)){
			result.put("errorMsg","ϵͳ�����ظ�!");  
			return result;
		}
		Tparameter t = new Tparameter();
		t.setArea(area);
		t.setType(type);
		t.setValue(value);
		t.setRemarks(remarks);
		parameterService.save(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String tparameterid = request.getParameter("tparameterid_dlg");
		String area = request.getParameter("area_dlg");
		String type = request.getParameter("type_dlg");
		String value = request.getParameter("value_dlg");
		String remarks = request.getParameter("remarks_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(parameterService.getParameterByType(area,type,tparameterid)){
			result.put("errorMsg","ϵͳ�����ظ�!");  
			return result;
		}
		
		Tparameter t = new Tparameter();
		t.setArea(area);
		t.setType(type);
		t.setValue(value);
		t.setRemarks(remarks);
		t.setTparameterid(tparameterid);
		
		parameterService.update(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ��Ҫ�޸ĵ���Ϣ
		String tparameterid = request.getParameter("tparameterid");
		parameterService.delete(Tparameter.class,tparameterid);
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("errorMsg","");  
        return result;
	}
}
