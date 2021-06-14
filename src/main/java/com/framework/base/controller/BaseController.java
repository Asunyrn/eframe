package com.framework.base.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.base.service.BaseService;
import com.framework.base.vo.ComboBox;

@RequestMapping("/baseController")
@Controller
public class BaseController {
	@Resource(name="baseService")
	private BaseService baseService;
	
	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
    @RequestMapping(value="getComboBox.do")  
	@ResponseBody
	public Object getComboBox(HttpServletRequest request,HttpServletResponse response) {
		String type = request.getParameter("type");
		String isnull = request.getParameter("isnull");
		List<ComboBox> list = baseService.getComboBox(type,isnull);
		return list;
	}
    
}
