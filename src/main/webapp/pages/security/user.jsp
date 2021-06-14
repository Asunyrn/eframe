<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/mytaglib" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>

<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<cc:basic path="<%=path %>"/>
	<cc:gridformatter name="status" code="USABLE"/>
	<cc:gridformatter name="troleid" code="TROLE"/>

	<script type="text/javascript">
	    $(document).ready(function(e) {
			//第一个值： 你期望导航在距离顶部多少的位置浮动
			//第二个值： 导航zindex
			$('#toolbar-nav').navfix(1, 999);
		});
		$(function() {
			//datagrid初始化  
			$('#userlist').datagrid({
			    height : 370,
				nowrap : false,//单元格信息自动换行
				striped : true,
				border : true,
				url:'userController/gridform1.do',
				remoteSort : false,
				idField : 'fldId',
				singleSelect : true,//是否单选  
				pagination : true,//分页控件  
				rownumbers : true,//行号  
				columns:[[  
                    {field:'tloginid',title:'用户ID',width:250},  
                    {field:'loginname',title:'登录名',width:120},  
                    {field:'username',title:'用户名',width:120}, 
                    {field:'password',title:'密码',width:120},
                    {field:'registrationuser',title:'注册人',width:120},
                    {field:'registrationtime',title:'注册时间',width:120},
                    {field:'troleid',title:'角色名',width:120,formatter: troleidformatter, editor: { type: 'combobox', options: { data: status, valueField: "value", textField: "text" }}},
                    {field:'status', title:'用户状态', width: 120, formatter: statusformatter, editor: { type: 'combobox', options: { data: status, valueField: "value", textField: "text" }}}
                ]],  
				toolbar : [ {
					text : '添加',
					iconCls : 'icon-add',
					handler : function() {
						$('#dlg_save').dialog('open').dialog('setTitle','添加用户');
						$('#fm').form('clear');
					}
				}, '-', {
					text : '修改',
					iconCls : 'icon-edit',
					handler : function() {
					    var row = $('#userlist').datagrid('getSelected');
                        if (row){
					        $('#dlg_update').dialog('open').dialog('setTitle','修改用户');
                            $('#fm_update').form('load',{
                                id_dlg_update       : row.tloginid,
                                loginname_dlg_update: row.loginname,
                                username_dlg_update : row.username,
                                trole_dlg_update    : row.troleid,
                                status_dlg_update   : row.status
                            });
                        }else{
                           $.messager.show({
                               title: '系统消息',
                                msg: '请选择需要修改的数据！'
                           });
                        };
					}
				}, '-', {
					text : '删除',
					iconCls : 'icon-remove',
					handler : function() {
					    $.messager.confirm('提示', '你确定要删除这条信息吗？', function (r) {  
					        if (r) {  
				                var row = $('#userlist').datagrid('getSelected');
				                if (row){
				                    $.post('userController/userdelete.do',{id:row.tloginid},function(result){
                                        if (result.errorMsg!=''){
                                            $.messager.show({   
                                                title: '系统消息',
                                                msg: result.errorMsg
                                           });
                                        }else{
                                            $('#userlist').datagrid('reload');   
                                            $.messager.show({   
                                                title: '系统消息',
                                                msg: '删除成功！'
                                            });
                                        }
                                    },'json'); 
				                }else{
                                    $.messager.show({
                                       title: '系统消息',
                                       msg: '请选择需要删除的数据！'
                                    });
                                };
                           };
                    });
				    }
				}]
			});
		});
		//查询
		function submitForm(){
            $("#userlist").datagrid('reload',{
                loginname: $('#loginname').val(),
                username : $('#username').val(),
                status   : $('#status').combobox('getValue'),
                trole    : $('#trole').combobox('getValue')
            });
        }
        //保存用户
        function saveUser(){
            $('#fm').form('submit',{
                url: 'userController/usersave.do',
                onSubmit: function(){
                    if(!$('#trole_dlg').combobox('getValue')){
                        $.messager.show({
                            title: '系统消息',
                            msg: '角色不能为空！'
                        });
                        return false ;
                    };
                    if(!$('#status_dlg').combobox('getValue')){
                        $.messager.show({
                            title: '系统消息',
                            msg: '是否可用不能为空！'
                        });
                        return false ;
                    };
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg!=''){
                        $.messager.show({
                            title: '系统消息',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg_save').dialog('close');        // close the dialog
                        $.messager.show({
                            title: '系统消息',
                            msg: '添加成功！'
                        });
                        $('#userlist').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        //修改用户
        function updateUser(){
            $('#fm_update').form('submit',{
           url: 'userController/userupdate.do',
                onSubmit: function(){
                    if(!$('#trole_dlg_update').combobox('getValue')){
                        $.messager.show({
                            title: '系统消息',
                            msg: '角色不能为空！'
                        });
                        return false ;
                    };
                    if(!$('#status_dlg_update').combobox('getValue')){
                        $.messager.show({
                            title: '系统消息',
                            msg: '是否可用不能为空！'
                        });
                        return false ;
                    };
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg!=''){
                        $.messager.show({
                            title: '系统消息',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg_update').dialog('close'); 
                        $.messager.show({
                            title: '系统消息',
                            msg: '修改成功！'
                        });
                        $('#userlist').datagrid('reload');   
                    }
                }
            });
        }

	</script>
  </head>
  
  <body>
    <div id="toolbar-nav"> 
    	<div class="easyui-panel" style="padding:5px;">
        	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" style="width:80px"  onclick="submitForm()">查询</a>
    	</div>
    </div>
        
  	<div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="查询条件"  >
        <table cellpadding="5">
            <tr>
                <td  class="normal" >登录名:</td>
                <td><input id = "loginname" name="loginname" class="easyui-validatebox" ></td>
                <td  class="normal">用户名:</td>
                <td><input id = "username"  name="username" class="easyui-validatebox" ></td>
                <td  class="normal">是否可用:</td>
				<td><input class="easyui-combobox" id="status" name="status" style="width:150px;" editable="false" data-options=" url:'baseController/getComboBox.do?type=USABLE&isnull=true',valueField:'id',textField:'text'"></td>
            	<td  class="normal">角色:</td>
            	<td><input class="easyui-combobox" id="trole" name="trole" style="width:150px;" editable="false" data-options=" url:'baseController/getComboBox.do?type=TROLE&isnull=true',valueField:'id',textField:'text'"></td>
            </tr>
        </table>
    </div>
    
	<div style="margin:20px 0;"></div>
	
	<table id="userlist" title="用户信息" ></table>
	
    <div id="dlg_save" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">登录名:</td>
					<td><input id="loginname_dlg"  name="loginname_dlg" class="easyui-validatebox" required="true"  style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">密码:</td>
					<td><input id="password_dlg"  name="password_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">用户名:</td>
					<td><input id="username_dlg"  name="username_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal"width="200px" >角色:</td>
					<td><input class="easyui-combobox" id="trole_dlg" name="trole_dlg" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=TROLE',valueField:'id',textField:'text'"></td>
				</tr>
				<tr>
					<td class="normal"width="100px" >是否可用:</td>
					<td><input class="easyui-combobox" id="status_dlg" name="status_dlg" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=USABLE',valueField:'id',textField:'text'"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_save').dialog('close')">关闭</a>
    </div>
    
    <div id="dlg_update" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
    <form id="fm_update" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">登录名:</td>
					<td><input id="loginname_dlg_update"  name="loginname_dlg_update" class="easyui-validatebox" required="true" readonly="readonly" style="width:150px;">
				        <input  id="id_dlg_update"  name="id_dlg_update"  type="hidden">
					</td>
				</tr>
				<tr>
					<td class="normal" width="200px">用户名:</td>
					<td><input id="username_dlg_update"  name="username_dlg_update" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal"width="200px" >角色:</td>
					<td><input class="easyui-combobox" id="trole_dlg_update" name="trole_dlg_update" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=TROLE',valueField:'id',textField:'text'"></td>
				</tr>
				<tr>
					<td class="normal"width="100px" >是否可用:</td>
					<td><input class="easyui-combobox" id="status_dlg_update" name="status_dlg_update" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=USABLE',valueField:'id',textField:'text'"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_update').dialog('close')">关闭</a>
    </div>
</body>
</html>
