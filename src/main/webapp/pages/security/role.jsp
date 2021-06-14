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
			//��һ��ֵ�� �����������ھ��붥�����ٵ�λ�ø���
			//�ڶ���ֵ�� ����zindex
			$('#toolbar-nav').navfix(1, 999);
		});
    	//��ѯ
		function submitForm(){
            $('#troledategrid').datagrid('reload',{
                rolename  : $('#rolename').val()
            });
        }
        var url;
        //������ҳ��
        function openAdd(){
            $('#dlg').dialog('open').dialog('setTitle','���');
            $('#fm').form('clear');
            url = 'roleController/save.do';
        }
        //����Ȩҳ��
        function openSave(){
            var row = $('#troledategrid').datagrid('getSelected');
            if (row){
           		 $('#fm_role').form('load',{
           		 	role_id : row.troleid,
                    role_name : row.rolename
                });
                $('#roleTree').tree({
                    url:'roleController/getUserTree.do?roleid='+row.troleid,
                    onLoadSuccess:function(node,data){
				        $('#roleTree').tree('expandAll');
                    }
                });
	            $('#dlg_role').dialog('open').dialog('setTitle','��Ȩ');
            }
        }
        //���޸�ҳ��
        function openEdit(){
            var row = $('#troledategrid').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','�޸�');
                $('#fm').form('load',{
                    troleid_dlg  : row.troleid,
                    rolename_dlg : row.rolename
                });
                 url = 'roleController/update.do';
            }
        }
        //��Ȩ����
        function role_save(){
        var role_text_1 = new Array();
        var role_text_2 = new Array();
       	role_text_1 = $('#roleTree').tree('getChecked');
       	role_text_2 = $('#roleTree').tree('getChecked', 'indeterminate');
       	var tree_text="";
       	for(var i=0;i<role_text_1.length;i++){
       		tree_text += role_text_1[i].id;
       		tree_text+=",";	
       	}
       	for(var i=0;i<role_text_2.length;i++){
       		tree_text += role_text_2[i].id;
       		tree_text+=",";
       	}
        $('#role_tree_text').val(tree_text);
	         $('#fm_role').form('submit',{
                url : 'roleController/role_save.do',
                onSubmit: function(){
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
                        $('#dlg_role').dialog('close');      
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '����ɹ���'
                        });  
                    }
                }
            });
	          
        }
        //����
        function save(){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
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
                        $('#dlg').dialog('close');      
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '����ɹ���'
                        });
                        $('#troledategrid').datagrid('reload');   
                    }
                }
            });
        }
        //ɾ��
        function deleteObject(){
            var row = $('#troledategrid').datagrid('getSelected');
            if (row){
                $.messager.confirm('��ʾ','��ȷ��Ҫɾ��������Ϣ��',function(r){
                    if (r){
                        $.post('roleController/delete.do',{troleid:row.troleid},function(result){
                            if (result.errorMsg!=''){
                                $.messager.show({    
                                    title: 'ϵͳ��Ϣ',
                                    msg: result.errorMsg
                                });
                            } else {
                                $('#troledategrid').datagrid('reload');
                                $.messager.show({   
                                    title: 'ϵͳ��Ϣ',
                                    msg: 'ɾ���ɹ���'
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
        	<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'" style="width:80px"  onclick="submitForm()">��ѯ</a>
        </div>
    </div>
    
    <div style="margin:20px 0;"></div>
    
    <div class="easyui-panel" title="��ѯ����"  >
        <table cellpadding="5">
            <tr>
                <td  class="normal" >��ɫ��:</td>
                <td><input id = "rolename" name="rolename" class="easyui-validatebox textbox" ></td>
            </tr>
        </table>
    </div>
    
    <div style="margin:20px 0;"></div>
    
    <table id="troledategrid" title="��ɫ��Ϣ" class="easyui-datagrid" style="height:370px;" url="roleController/gridform1.do"
           toolbar="#toolbar" pagination="true" rownumbers="true"  fitColumns=false singleSelect="true" resizable="true">
	        <thead>
	            <tr>
	                <th field="troleid" width="200" hidden="true" >ID</th>
	                <th field="rolename" width="200" >��ɫ��</th>
	            </tr>
	        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAdd()">���</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEdit()">�޸�</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openSave()">��Ȩ</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteObject()">ɾ��</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style=" width:400px; height:280px; padding:10px 20px;" data-options="resizable:true,modal:true"  closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">��ɫ��</td>
					<td><input id="rolename_dlg"  name="rolename_dlg" class="easyui-validatebox" required="true"  style="width:150px;">
						<input id="troleid_dlg" name="troleid_dlg" type="hidden">
					</td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a id="dlg-save"  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">����</a>
        <a id="dlg-close" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">�ر�</a>
    </div>
    
    <div id="dlg_role" class="easyui-dialog" style=" width:280px; height:450px; padding:10px 20px;" data-options="resizable:true,modal:true"  closed="true"  buttons="#dlg-role-buttons">
       <form id="fm_role" method="post" >
      	  <input id="role_tree_text" name="role_tree_text" type="hidden" >
      	  <input id="role_name" name="role_name" type="hidden" >
      	  <input id="role_id" name="role_id" type="hidden" >
      	  <ul id="roleTree" class="easyui-tree" checkbox="true" ></ul>
 	   </form>
    </div>
    <div id="dlg-role-buttons">
        <a id="dlg-role-save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="role_save()">����</a>
        <a id="dlg-role-close" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_role').dialog('close')">�ر�</a>
    </div>
    
  </body>
</html>

