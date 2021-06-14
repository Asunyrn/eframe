package com.framework.common.security.controller;

import java.io.UnsupportedEncodingException;
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
import com.framework.base.po.Tpermission;
import com.framework.common.security.service.PermissionService;
import com.framework.common.security.vo.TreeNew;

@Controller
@RequestMapping("/permissionController")
public class PermissionController extends BaseController {
	@Resource(name = "permissionService")
	private PermissionService permissionService;

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
    @RequestMapping(value="getUserTree.do")  
	@ResponseBody
	public Object getUserTree(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("id");
		List<TreeNew> list ;
		if(pid != null&&!pid.equals("")){
			list = permissionService.getTree(pid);
		}else{
			list = permissionService.getTree("0");
		}
		return list;
	}

	@RequestMapping(value = "getPermission.do")
	@ResponseBody
	public Object getPermission(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		Tpermission t = permissionService.get(Tpermission.class, id);
		return t;
	}
	
	@RequestMapping(value = "addPermission.do")
	@ResponseBody
	public Object addPermission(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		//获取参数
		String tpermissionid = request.getParameter("tpermissionid");
		String permissionname = request.getParameter("permissionname");
		String action = request.getParameter("action");
		String url = request.getParameter("url");
		String orders = request.getParameter("orders");
		String isleaf = request.getParameter("isleaf");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//判断重复
		if(permissionService.getTpermissionByPermissionname(permissionname)){
			result.put("errorMsg","权限名重复!");  
			return result;
		}
		Tpermission t = new Tpermission();
		t.setParentid(tpermissionid);
		t.setPermissionname(permissionname);
		t.setAction(Long.parseLong(action));
		t.setUrl(url);
		t.setOrders(Long.parseLong(orders));
		t.setIsleaf(Long.parseLong(isleaf));
		//保存
		permissionService.save(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "updatePermission.do")
	@ResponseBody
	public Object updatePermission(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		String tpermissionid = request.getParameter("tpermissionid");
		String permissionname = request.getParameter("permissionname");
		String action = request.getParameter("action");
		String url = request.getParameter("url");
		String orders = request.getParameter("orders");
		String isleaf = request.getParameter("isleaf");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//判断重复
		if(permissionService.getByPermissionname(permissionname, tpermissionid)){
			result.put("errorMsg","权限名重复!");  
			return result;
		}
		Tpermission t = permissionService.get(Tpermission.class,tpermissionid);
		t.setPermissionname(permissionname);
		t.setAction(Long.parseLong(action));
		t.setUrl(url);
		t.setOrders(Long.parseLong(orders));
		t.setIsleaf(Long.parseLong(isleaf));
		//保存
		permissionService.update(t);
        result.put("errorMsg","");  
        return result;
	}
	@RequestMapping(value = "deletePermission.do")
	@ResponseBody
	public Object deletePemission(HttpServletRequest request,HttpServletResponse response) {
		String tpermissionid = request.getParameter("tpermissionid");
		//返回结果
		Map<String, Object> result = new HashMap<String, Object>() ;
		//判断是否存在角色使用该权限
		if(permissionService.getTroleTpermission(tpermissionid)){
			result.put("errorMsg","存在使用该权限的角色，不能删除!");  
			return result;
		}
		//判断是否存在子权限
		if(permissionService.getSunTpermissionCount(tpermissionid)){
			result.put("errorMsg","该权限存在子权限，不能删除!");  
			return result;
		}
		//删除
		permissionService.delete(Tpermission.class,tpermissionid);
        result.put("errorMsg","");  
        return result;
	}
}
