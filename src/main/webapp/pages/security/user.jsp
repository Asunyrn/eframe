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
			//��һ��ֵ�� �����������ھ��붥�����ٵ�λ�ø���
			//�ڶ���ֵ�� ����zindex
			$('#toolbar-nav').navfix(1, 999);
		});
		$(function() {
			//datagrid��ʼ��  
			$('#userlist').datagrid({
			    height : 370,
				nowrap : false,//��Ԫ����Ϣ�Զ�����
				striped : true,
				border : true,
				url:'userController/gridform1.do',
				remoteSort : false,
				idField : 'fldId',
				singleSelect : true,//�Ƿ�ѡ  
				pagination : true,//��ҳ�ؼ�  
				rownumbers : true,//�к�  
				columns:[[  
                    {field:'tloginid',title:'�û�ID',width:250},  
                    {field:'loginname',title:'��¼��',width:120},  
                    {field:'username',title:'�û���',width:120}, 
                    {field:'password',title:'����',width:120},
                    {field:'registrationuser',title:'ע����',width:120},
                    {field:'registrationtime',title:'ע��ʱ��',width:120},
                    {field:'troleid',title:'��ɫ��',width:120,formatter: troleidformatter, editor: { type: 'combobox', options: { data: status, valueField: "value", textField: "text" }}},
                    {field:'status', title:'�û�״̬', width: 120, formatter: statusformatter, editor: { type: 'combobox', options: { data: status, valueField: "value", textField: "text" }}}
                ]],  
				toolbar : [ {
					text : '���',
					iconCls : 'icon-add',
					handler : function() {
						$('#dlg_save').dialog('open').dialog('setTitle','����û�');
						$('#fm').form('clear');
					}
				}, '-', {
					text : '�޸�',
					iconCls : 'icon-edit',
					handler : function() {
					    var row = $('#userlist').datagrid('getSelected');
                        if (row){
					        $('#dlg_update').dialog('open').dialog('setTitle','�޸��û�');
                            $('#fm_update').form('load',{
                                id_dlg_update       : row.tloginid,
                                loginname_dlg_update: row.loginname,
                                username_dlg_update : row.username,
                                trole_dlg_update    : row.troleid,
                                status_dlg_update   : row.status
                            });
                        }else{
                           $.messager.show({
                               title: 'ϵͳ��Ϣ',
                                msg: '��ѡ����Ҫ�޸ĵ����ݣ�'
                           });
                        };
					}
				}, '-', {
					text : 'ɾ��',
					iconCls : 'icon-remove',
					handler : function() {
					    $.messager.confirm('��ʾ', '��ȷ��Ҫɾ��������Ϣ��', function (r) {  
					        if (r) {  
				                var row = $('#userlist').datagrid('getSelected');
				                if (row){
				                    $.post('userController/userdelete.do',{id:row.tloginid},function(result){
                                        if (result.errorMsg!=''){
                                            $.messager.show({   
                                                title: 'ϵͳ��Ϣ',
                                                msg: result.errorMsg
                                           });
                                        }else{
                                            $('#userlist').datagrid('reload');   
                                            $.messager.show({   
                                                title: 'ϵͳ��Ϣ',
                                                msg: 'ɾ���ɹ���'
                                            });
                                        }
                                    },'json'); 
				                }else{
                                    $.messager.show({
                                       title: 'ϵͳ��Ϣ',
                                       msg: '��ѡ����Ҫɾ�������ݣ�'
                                    });
                                };
                           };
                    });
				    }
				}]
			});
		});
		//��ѯ
		function submitForm(){
            $("#userlist").datagrid('reload',{
                loginname: $('#loginname').val(),
                username : $('#username').val(),
                status   : $('#status').combobox('getValue'),
                trole    : $('#trole').combobox('getValue')
            });
        }
        //�����û�
        function saveUser(){
            $('#fm').form('submit',{
                url: 'userController/usersave.do',
                onSubmit: function(){
                    if(!$('#trole_dlg').combobox('getValue')){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '��ɫ����Ϊ�գ�'
                        });
                        return false ;
                    };
                    if(!$('#status_dlg').combobox('getValue')){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '�Ƿ���ò���Ϊ�գ�'
                        });
                        return false ;
                    };
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg!=''){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg_save').dialog('close');        // close the dialog
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '��ӳɹ���'
                        });
                        $('#userlist').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
        //�޸��û�
        function updateUser(){
            $('#fm_update').form('submit',{
           url: 'userController/userupdate.do',
                onSubmit: function(){
                    if(!$('#trole_dlg_update').combobox('getValue')){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '��ɫ����Ϊ�գ�'
                        });
                        return false ;
                    };
                    if(!$('#status_dlg_update').combobox('getValue')){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '�Ƿ���ò���Ϊ�գ�'
                        });
                        return false ;
                    };
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg!=''){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg_update').dialog('close'); 
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '�޸ĳɹ���'
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
        	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" style="width:80px"  onclick="submitForm()">��ѯ</a>
    	</div>
    </div>
        
  	<div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="��ѯ����"  >
        <table cellpadding="5">
            <tr>
                <td  class="normal" >��¼��:</td>
                <td><input id = "loginname" name="loginname" class="easyui-validatebox" ></td>
                <td  class="normal">�û���:</td>
                <td><input id = "username"  name="username" class="easyui-validatebox" ></td>
                <td  class="normal">�Ƿ����:</td>
				<td><input class="easyui-combobox" id="status" name="status" style="width:150px;" editable="false" data-options=" url:'baseController/getComboBox.do?type=USABLE&isnull=true',valueField:'id',textField:'text'"></td>
            	<td  class="normal">��ɫ:</td>
            	<td><input class="easyui-combobox" id="trole" name="trole" style="width:150px;" editable="false" data-options=" url:'baseController/getComboBox.do?type=TROLE&isnull=true',valueField:'id',textField:'text'"></td>
            </tr>
        </table>
    </div>
    
	<div style="margin:20px 0;"></div>
	
	<table id="userlist" title="�û���Ϣ" ></table>
	
    <div id="dlg_save" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">��¼��:</td>
					<td><input id="loginname_dlg"  name="loginname_dlg" class="easyui-validatebox" required="true"  style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">����:</td>
					<td><input id="password_dlg"  name="password_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">�û���:</td>
					<td><input id="username_dlg"  name="username_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal"width="200px" >��ɫ:</td>
					<td><input class="easyui-combobox" id="trole_dlg" name="trole_dlg" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=TROLE',valueField:'id',textField:'text'"></td>
				</tr>
				<tr>
					<td class="normal"width="100px" >�Ƿ����:</td>
					<td><input class="easyui-combobox" id="status_dlg" name="status_dlg" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=USABLE',valueField:'id',textField:'text'"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">����</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_save').dialog('close')">�ر�</a>
    </div>
    
    <div id="dlg_update" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
    <form id="fm_update" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">��¼��:</td>
					<td><input id="loginname_dlg_update"  name="loginname_dlg_update" class="easyui-validatebox" required="true" readonly="readonly" style="width:150px;">
				        <input  id="id_dlg_update"  name="id_dlg_update"  type="hidden">
					</td>
				</tr>
				<tr>
					<td class="normal" width="200px">�û���:</td>
					<td><input id="username_dlg_update"  name="username_dlg_update" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal"width="200px" >��ɫ:</td>
					<td><input class="easyui-combobox" id="trole_dlg_update" name="trole_dlg_update" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=TROLE',valueField:'id',textField:'text'"></td>
				</tr>
				<tr>
					<td class="normal"width="100px" >�Ƿ����:</td>
					<td><input class="easyui-combobox" id="status_dlg_update" name="status_dlg_update" editable="false" style="width:150px;"  required="true" data-options=" url:'baseController/getComboBox.do?type=USABLE',valueField:'id',textField:'text'"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateUser()">�޸�</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_update').dialog('close')">�ر�</a>
    </div>
</body>
</html>
