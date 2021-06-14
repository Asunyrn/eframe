package com.framework.common.security.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.common.security.vo.LoginUserVO;
import com.framework.common.security.vo.TloginVo;
import com.framework.base.controller.BaseController;
import com.framework.base.po.Tlogin;
import com.framework.base.po.TloginTrole;
import com.framework.base.utils.Page;
import com.framework.common.security.service.UserService;

@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	@Resource(name="userService")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

	@RequestMapping(value = "gridform1.do")
	@ResponseBody
	public Object gridform1(HttpServletRequest request,HttpServletResponse response) {
		//�û�����½��
		String loginname = request.getParameter("loginname");
		String username = request.getParameter("username");
		String status = request.getParameter("status");
		String trole = request.getParameter("trole");
		//����
		int sum = userService.getTloginSum(loginname,username,status,trole);
		//��ҳ��Ϣ
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Page p = new Page();
		p.setIntPage(Integer.parseInt(page));
		p.setPageInfoCount(Integer.parseInt(rows));
		//��ѯ��Ϣ
		List<TloginVo> list = userService.listTlogin(loginname,username,status,trole,p);
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("total",sum);  
        result.put("rows",list) ;
        return result;
	}
	@RequestMapping(value = "usersave.do")
	@ResponseBody
	public Object usersave(HttpServletRequest request,HttpServletResponse response) {
		LoginUserVO vo = (LoginUserVO)request.getSession().getAttribute("LOGINUSER");
		//�û�����½��
		String loginname_dlg = request.getParameter("loginname_dlg");
		String password_dlg = request.getParameter("password_dlg");
		String username_dlg = request.getParameter("username_dlg");
		String trole_dlg = request.getParameter("trole_dlg");
		String status_dlg = request.getParameter("status_dlg");
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ; 
		//�ж��û����Ƿ��ظ�
		if(userService.getUserByLoginname(loginname_dlg)){
	        result.put("errorMsg","��¼���ظ�!");  
	        return result;
		}
		
		//����Tlogin,Tlogin_trole
		Tlogin t = new Tlogin();
		t.setLoginname(loginname_dlg);
		t.setUsername(username_dlg);
		t.setPassword(password_dlg);
		t.setStatus(Integer.parseInt(status_dlg));
		t.setRegistrationuser(vo.getLoginname());
		t.setRegistrationtime(new Date());
		
		TloginTrole tr = new TloginTrole();
		tr.setTroleid(trole_dlg);
		
		userService.saveTlogin(t,tr);
        result.put("errorMsg","");  
        return result;
	}
	@RequestMapping(value = "userupdate.do")
	@ResponseBody
	public Object userupdate(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ��Ҫ�޸ĵ���Ϣ
		String id_dlg_update = request.getParameter("id_dlg_update");
		String username_dlg_update = request.getParameter("username_dlg_update");
		String trole_dlg_update = request.getParameter("trole_dlg_update");
		String status_dlg_update = request.getParameter("status_dlg_update");
        //����Tlogin
		Tlogin t = new Tlogin();
		t.setTloginid(id_dlg_update);
		t.setUsername(username_dlg_update);
		t.setStatus(Integer.parseInt(status_dlg_update));
		//��login
		userService.updateTlogin(t,trole_dlg_update);
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("errorMsg","");  
        return result;
	}
	@RequestMapping(value = "userdelete.do")
	@ResponseBody
	public Object userdelete(HttpServletRequest request,HttpServletResponse response) {
		//��ȡ��Ҫ�޸ĵ���Ϣ
		String id = request.getParameter("id");
		userService.deleteTlogin(id);
		//���ؽ��
		Map<String, Object> result = new HashMap<String, Object>() ;  
        result.put("errorMsg","");  
        return result;
	}
}
