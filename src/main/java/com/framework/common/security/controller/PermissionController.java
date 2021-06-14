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
		//��ȡ����
		String tpermissionid = request.getParameter("tpermissionid");
		String permissionname = request.getParameter("permissionname");
		String action = request.getParameter("action");
		String url = request.getParameter("url");
		String orders = request.getParameter("orders");
		String isleaf = request.getParameter("isleaf");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(permissionService.getTpermissionByPermissionname(permissionname)){
			result.put("errorMsg","Ȩ�����ظ�!");  
			return result;
		}
		Tpermission t = new Tpermission();
		t.setParentid(tpermissionid);
		t.setPermissionname(permissionname);
		t.setAction(Long.parseLong(action));
		t.setUrl(url);
		t.setOrders(Long.parseLong(orders));
		t.setIsleaf(Long.parseLong(isleaf));
		//����
		permissionService.save(t);
        result.put("errorMsg","");  
        return result;
	}
	
	@RequestMapping(value = "updatePermission.do")
	@ResponseBody
	public Object updatePermission(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ����
		String tpermissionid = request.getParameter("tpermissionid");
		String permissionname = request.getParameter("permissionname");
		String action = request.getParameter("action");
		String url = request.getParameter("url");
		String orders = request.getParameter("orders");
		String isleaf = request.getParameter("isleaf");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��ظ�
		if(permissionService.getByPermissionname(permissionname, tpermissionid)){
			result.put("errorMsg","Ȩ�����ظ�!");  
			return result;
		}
		Tpermission t = permissionService.get(Tpermission.class,tpermissionid);
		t.setPermissionname(permissionname);
		t.setAction(Long.parseLong(action));
		t.setUrl(url);
		t.setOrders(Long.parseLong(orders));
		t.setIsleaf(Long.parseLong(isleaf));
		//����
		permissionService.update(t);
        result.put("errorMsg","");  
        return result;
	}
	@RequestMapping(value = "deletePermission.do")
	@ResponseBody
	public Object deletePemission(HttpServletRequest request,HttpServletResponse response) {
		String tpermissionid = request.getParameter("tpermissionid");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;
		//�ж��Ƿ���ڽ�ɫʹ�ø�Ȩ��
		if(permissionService.getTroleTpermission(tpermissionid)){
			result.put("errorMsg","����ʹ�ø�Ȩ�޵Ľ�ɫ������ɾ��!");  
			return result;
		}
		//�ж��Ƿ������Ȩ��
		if(permissionService.getSunTpermissionCount(tpermissionid)){
			result.put("errorMsg","��Ȩ�޴�����Ȩ�ޣ�����ɾ��!");  
			return result;
		}
		//ɾ��
		permissionService.delete(Tpermission.class,tpermissionid);
        result.put("errorMsg","");  
        return result;
	}
}
