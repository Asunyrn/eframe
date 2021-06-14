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

import com.framework.base.controller.BaseController;
import com.framework.base.po.Trole;
import com.framework.base.utils.Page;
import com.framework.common.security.service.RoleService;
import com.framework.common.security.vo.TreeNew;

@Controller
@RequestMapping("/roleController")
public class RoleController  extends BaseController {
	@Resource(name="roleService")
	private RoleService roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@RequestMapping(value = "gridform1.do")
	@ResponseBody
	public Object gridform1(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		String rolename = request.getParameter("rolename");
		//总数
		int sum = roleService.queryCount(rolename);
		//分页信息
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Page p = new Page();
		p.setIntPage(Integer.parseInt(page));
		p.setPageInfoCount(Integer.parseInt(rows));
		//查询信息
		List<Trole> list = roleService.queryList(rolename, p) ;
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("total",sum);  
        result.put("rows",list) ;
        return result;
	}
	
	@RequestMapping(value = "save.do")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		String rolename = request.getParameter("rolename_dlg");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//判断重复
		if(roleService.getTroleByRolename(rolename)){
			result.put("errorMsg","角色名重复!");  
			return result;
		}
		Trole t = new Trole();
		t.setRolename(rolename);
		roleService.save(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		String rolename = request.getParameter("rolename_dlg");
		String troleid = request.getParameter("troleid_dlg");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//判断重复
		if(roleService.getTroleByRolename(rolename)){
			result.put("errorMsg","角色名重复!");  
			return result;
		}
		Trole t = new Trole();
		t.setTroleid(troleid);
		t.setRolename(rolename);
		roleService.update(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response) {
		//获取需要修改的信息
		String troleid = request.getParameter("troleid");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ;  
		//如果有用户使用该角色，不允许删除
		if(roleService.getTlogintrole(troleid)){
			result.put("errorMsg","存在使用该角色的用户!");  
			return result;
		}
		roleService.deleteTrole(troleid);
		//返回结果
        result.put("errorMsg","");  
        return result;
	}
	
    @RequestMapping(value="getUserTree.do")  
	@ResponseBody
	public Object getUserTree(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("id");
		String roleid = request.getParameter("roleid");
		List<TreeNew> list ;
		if(roleid == null||roleid.equals("")){
			roleid = "";
		}
		if(pid != null&&!pid.equals("")){
			list = roleService.getTree(pid,roleid);
		}else{
			list = roleService.getTree("0",roleid);
		}
		return list;
	}
    
	@RequestMapping(value = "role_save.do")
	@ResponseBody
	public Object role_save(HttpServletRequest request,HttpServletResponse response) {
		String role_text= request.getParameter("role_tree_text");
		String roleid = request.getParameter("role_id");
		role_text.substring(0,role_text.length()-1);
		roleService.role_Save(role_text,roleid);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorMsg","");  
		return result;
	}
}