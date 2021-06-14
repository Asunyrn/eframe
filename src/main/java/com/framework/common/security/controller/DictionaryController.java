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
import com.framework.base.po.Tdictionary;
import com.framework.base.utils.Page;
import com.framework.common.security.service.DictionaryService;

@Controller
@RequestMapping("/dictionaryController")
public class DictionaryController {
	
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	@RequestMapping(value = "gridform1.do")
	@ResponseBody
	public Object gridform1(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String type = request.getParameter("type");
		String code = request.getParameter("code");
		String value = request.getParameter("value");
		//����
		int sum = dictionaryService.queryCount(type, code, value);
		//��ҳ��Ϣ
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Page p = new Page();
		p.setIntPage(Integer.parseInt(page));
		p.setPageInfoCount(Integer.parseInt(rows));
		//��ѯ��Ϣ
		List<Tdictionary> list = dictionaryService.queryList(type, code, value, p) ;
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
		String type_dlg = request.getParameter("type_dlg");
		String code_dlg = request.getParameter("code_dlg");
		String value_dlg = request.getParameter("value_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(dictionaryService.getDictionaryByType(type_dlg,code_dlg,value_dlg)){
			result.put("errorMsg","�ֵ��ظ�!");  
			return result;
		}
		Tdictionary t = new Tdictionary();
		t.setType(type_dlg);
		t.setCode(code_dlg);
		t.setValue(value_dlg);
		dictionaryService.save(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String type_dlg = request.getParameter("type_dlg");
		String code_dlg = request.getParameter("code_dlg");
		String value_dlg = request.getParameter("value_dlg");
		String tdictionaryid_dlg = request.getParameter("tdictionaryid_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(dictionaryService.getDictionaryByType(type_dlg,code_dlg,value_dlg)){
			result.put("errorMsg","�ֵ��ظ�!");  
			return result;
		}
		Tdictionary t = new Tdictionary();
		t.setType(type_dlg);
		t.setCode(code_dlg);
		t.setValue(value_dlg);
		t.setTdictionaryid(Long.parseLong(tdictionaryid_dlg));
		dictionaryService.update(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ��Ҫ�޸ĵ���Ϣ
		String tdictionaryid = request.getParameter("tdictionaryid");
		dictionaryService.delete(Tdictionary.class,Long.parseLong(tdictionaryid));
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("errorMsg","");  
        return result;
	}
}
