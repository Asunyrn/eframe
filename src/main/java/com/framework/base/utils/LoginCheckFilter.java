package com.framework.base.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.common.security.vo.LoginUserVO;

public class LoginCheckFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) arg0;
        HttpServletResponse response=(HttpServletResponse) arg1;
        //������jsp����jsp�����ƣ�������Servlet���ص���Servlet��url��patterm
        String path = request.getServletPath();
        //�ж��Ƿ��ǵ�¼ҳ��
        if("/pages/login.jsp".equals(path)|| "/loginUserController/login.do".equals(path)){
            arg2.doFilter(request, response);
        }else{
        	//�ж�session�Ƿ����
        	LoginUserVO v = (LoginUserVO) request.getSession().getAttribute("LOGINUSER");
            if(v == null){
               	//�ж��Ƿ���ajxa����
            	if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            		response.addHeader("sessionstatus", "timeout"); 
        		}else{
        			PrintWriter out = response.getWriter();
        			out.println("<script language='JavaScript'>" +"alert('session time-out!'); location.href='"+request.getContextPath()+"/pages/login.jsp';</script>");
        		}
            }else{
                arg2.doFilter(request, response);
            }
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
