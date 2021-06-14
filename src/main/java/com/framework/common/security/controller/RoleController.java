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
		//��ȡ����
		String rolename = request.getParameter("rolename");
		//����
		int sum = roleService.queryCount(rolename);
		//��ҳ��Ϣ
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Page p = new Page();
		p.setIntPage(Integer.parseInt(page));
		p.setPageInfoCount(Integer.parseInt(rows));
		//��ѯ��Ϣ
		List<Trole> list = roleService.queryList(rolename, p) ;
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
		String rolename = request.getParameter("rolename_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(roleService.getTroleByRolename(rolename)){
			result.put("errorMsg","��ɫ���ظ�!");  
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
		//��ȡ����
		String rolename = request.getParameter("rolename_dlg");
		String troleid = request.getParameter("troleid_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(roleService.getTroleByRolename(rolename)){
			result.put("errorMsg","��ɫ���ظ�!");  
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
		//��ȡ��Ҫ�޸ĵ���Ϣ
		String troleid = request.getParameter("troleid");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
		//������û�ʹ�øý�ɫ��������ɾ��
		if(roleService.getTlogintrole(troleid)){
			result.put("errorMsg","����ʹ�øý�ɫ���û�!");  
			return result;
		}
		roleService.deleteTrole(troleid);
		//���ؽ��
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