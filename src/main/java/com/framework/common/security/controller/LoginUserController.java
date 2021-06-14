package com.framework.common.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.base.po.Tlogin;
import com.framework.base.utils.MD5;
import com.framework.common.security.service.LoginUserService;
import com.framework.common.security.vo.LoginUserVO;
import com.framework.common.security.vo.TreeNew;

@Controller
@RequestMapping("/loginUserController")
public class LoginUserController  {
	@Resource(name="loginUserService")
	private LoginUserService loginUserService;
	public LoginUserService getLoginUserService() {
		return loginUserService;
	}
	public void setLoginUserService(LoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}
	
    @RequestMapping(value="login.do")  
    public String login(HttpServletRequest request,HttpServletResponse response) {
    	Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);   
    	String code = request.getParameter("code");

    	if(!captcha.getAnswer().equals(code)){
    		request.setAttribute("ERROR", "校验码不正确!");
    		return "login";
    	}
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	LoginUserVO vo = loginUserService.loginUser(username, password);
    	if(vo != null){
    		request.getSession().setAttribute("LOGINUSER", vo);
    		return "index";
    	}else{
    		request.setAttribute("ERROR", "用户名或密码不正确!");
    		return "login";
    	}
    }
    
    @RequestMapping(value="getUserTree.do")  
	@ResponseBody
	public Object getUserTree(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("id");
		LoginUserVO vo = (LoginUserVO)request.getSession().getAttribute("LOGINUSER");
		List<TreeNew> list ;
		if(pid!=null&&!pid.equals("")){
			list = loginUserService.getUserTree(vo.getRolename(),pid);
		}else{
			list = loginUserService.getUserTree(vo.getRolename(),"d3600285-b3a8-42dc-a99e-e97ff15fcf7c");
		}
		return list;
	}
    
	@RequestMapping(value = "updatePassWord.do")
	@ResponseBody
	public Object updatePassWord(HttpServletRequest request,HttpServletResponse response) {
		String password_old = request.getParameter("password_old");
		String password_new1 = request.getParameter("password_new1");
		String password_new2 = request.getParameter("password_new2");
		LoginUserVO vo = (LoginUserVO)request.getSession().getAttribute("LOGINUSER");
		Map<String, Object> result = new HashMap<String, Object>();
		if(!password_new1.equals(password_new2)){
			result.put("errorMsg","新密码与确认新密码不一致!");  
			return result;
		}
		Tlogin t  = loginUserService.getPasssWordOld(vo.getLoginname(),password_old);
		if(t == null){
			result.put("errorMsg","原密码不正确!");  
			return result;
		}
		MD5 md5 = new MD5();
		t.setPassword(md5.getMD5ofStr(password_new1));
		loginUserService.update(t);
		result.put("errorMsg","");  
		return result;
	}
	
	@RequestMapping(value = "logout.do")
	public String logout(HttpServletRequest request) {
		return "login";
	}
	
}
