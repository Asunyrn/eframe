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
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<cc:basic path="<%=path %>"/>
    <script type="text/javascript">
		$(document).ready(function(e) {
			//第一个值： 你期望导航在距离顶部多少的位置浮动
			//第二个值： 导航zindex
			$('#toolbar-nav').navfix(1, 999);
		});
    	//查询
		function submitForm(){
            $('#dictionarylist').datagrid('reload',{
                type  : $('#type').val(),
                code  : $('#code').val(),
                value : $('#value').val()
            });
        }
        var url;
        //打开新增页面
        function openNewUser(){
            $('#dlg').dialog('open').dialog('setTitle','添加字典');
            $('#fm').form('clear');
            url = 'dictionaryController/save.do';
        }
        //打开修改页面
        function openEditUser(){
            var row = $('#dictionarylist').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','修改字典');
                $('#fm').form('load',{
                    tdictionaryid_dlg : row.tdictionaryid,
                    type_dlg : row.type,
                    code_dlg : row.code,
                    value_dlg   : row.value
                });
                url = 'dictionaryController/update.do';
            }
        }
        //保存
        function saveUser(){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
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
                        $('#dlg').dialog('close');      
                        $.messager.show({
                            title: '系统消息',
                            msg: '保存成功！'
                        });
                        $('#dictionarylist').datagrid('reload');   
                    }
                }
            });
        }
        //删除
        function deleteUser(){
            var row = $('#dictionarylist').datagrid('getSelected');
            if (row){
                $.messager.confirm('提示','你确定要删除这条信息吗？',function(r){
                    if (r){
                        $.post('dictionaryController/delete.do',{tdictionaryid:row.tdictionaryid},function(result){
                            if (result.errorMsg!=''){
                                $.messager.show({    
                                    title: '系统消息',
                                    msg: result.errorMsg
                                });
                            } else {
                                $('#dictionarylist').datagrid('reload');
                                $.messager.show({   
                                    title: '系统消息',
                                    msg: '删除成功！'
                                });    
                            }
                        },'json');
                    }
                });
            }
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
                <td  class="normal" >类别:</td>
                <td><input id = "type" name="type" class="easyui-validatebox textbox" ></td>
                <td  class="normal">值:</td>
                <td><input id = "code"  name="code" class="easyui-validatebox textbox" ></td>
                <td  class="normal">含义:</td>
                <td><input id = "value"  name="value" class="easyui-validatebox textbox" ></td>
            </tr>
        </table>
    </div>
    
    <div style="margin:20px 0;"></div>
    
    <table id="dictionarylist" title="字典信息" class="easyui-datagrid" style="height:370px;" url="dictionaryController/gridform1.do"
           toolbar="#toolbar" pagination="true" rownumbers="true"  fitColumns=false singleSelect="true" resizable="true">
        <thead>
            <tr>
                <th field="tdictionaryid" width="200" hidden="true">ID</th>
                <th field="type" width="200">类别</th>
                <th field="code" width="200">值</th>
                <th field=value width="200">含义</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openNewUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">类别</td>
					<td><input id="type_dlg"  name="type_dlg" class="easyui-validatebox" required="true"  style="width:150px;">
						<input id="tdictionaryid_dlg" name="tdictionaryid_dlg" type="hidden">
					</td>
				</tr>
				<tr>
					<td class="normal" width="200px">值</td>
					<td><input id="code_dlg"  name="code_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">含义</td>
					<td><input id="value_dlg"  name="value_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
    </div>
    
  </body>
</html>
