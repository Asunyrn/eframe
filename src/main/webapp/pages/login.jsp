<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/mytaglib" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>社会保障卡综合服务平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <cc:basic path="<%=path %>"/>
    <link rel="stylesheet" type="text/css" href="css/login.css" />

	<script type="text/javascript"> 
		if (top != self) {
			if (top.location != self.location) {
				top.location = self.location;
			}
		}
		//登陆
		function submitform() {
			if ($('#username').val() != '' && $('#password').val() != '') {
				$('#fm').submit();
			} else {
				$.messager.alert('系统消息', '用户名密码不允许为空!');
			}
		}
		//重置
		function clearform() {
			$('#fm').form('clear');
		}
	</script>
</head>
<body>
<%
	String error = (String) request.getAttribute("ERROR");
	if (error != null) {
		out.println("<script language='JavaScript'>" + "alert('"+ error + "');</script>");
	}
%>
	<div class="content">
		<form id="fm" method="post" action="loginUserController/login.do">
			<div class="a">
				<input id="username" name="username" class="easyui-textbox" style="width:210px;height:30px;">
			</div>
			<div class="b">
			    <input id="password" name="password" type="password" class="easyui-textbox" style="width:210px;height:30px;"> 
			</div>
			<div class="d">
				<input id="code" name="code" class="easyui-textbox" style="width:103px;height:30px;"> 
			</div>
			<div class="e">
			    <img src="<%=basePath %>stickyImg?<%= Math.random() %>" />
			</div>
			<div class="c">
			    <div class="ca"  onclick="submitform()" onmousemove="this.style.cursor='hand';"></div>
				<div class="cb"  onclick="clearform()" onmousemove="this.style.cursor='hand';"></div>
			</div>
		</form>
	</div>
	
</body>
</html>
