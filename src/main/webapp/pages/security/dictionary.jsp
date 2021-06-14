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
            $('#dictionarylist').datagrid('reload',{
                type  : $('#type').val(),
                code  : $('#code').val(),
                value : $('#value').val()
            });
        }
        var url;
        //������ҳ��
        function openNewUser(){
            $('#dlg').dialog('open').dialog('setTitle','����ֵ�');
            $('#fm').form('clear');
            url = 'dictionaryController/save.do';
        }
        //���޸�ҳ��
        function openEditUser(){
            var row = $('#dictionarylist').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','�޸��ֵ�');
                $('#fm').form('load',{
                    tdictionaryid_dlg : row.tdictionaryid,
                    type_dlg : row.type,
                    code_dlg : row.code,
                    value_dlg   : row.value
                });
                url = 'dictionaryController/update.do';
            }
        }
        //����
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
                            title: 'ϵͳ��Ϣ',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close');      
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '����ɹ���'
                        });
                        $('#dictionarylist').datagrid('reload');   
                    }
                }
            });
        }
        //ɾ��
        function deleteUser(){
            var row = $('#dictionarylist').datagrid('getSelected');
            if (row){
                $.messager.confirm('��ʾ','��ȷ��Ҫɾ��������Ϣ��',function(r){
                    if (r){
                        $.post('dictionaryController/delete.do',{tdictionaryid:row.tdictionaryid},function(result){
                            if (result.errorMsg!=''){
                                $.messager.show({    
                                    title: 'ϵͳ��Ϣ',
                                    msg: result.errorMsg
                                });
                            } else {
                                $('#dictionarylist').datagrid('reload');
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
                <td  class="normal" >���:</td>
                <td><input id = "type" name="type" class="easyui-validatebox textbox" ></td>
                <td  class="normal">ֵ:</td>
                <td><input id = "code"  name="code" class="easyui-validatebox textbox" ></td>
                <td  class="normal">����:</td>
                <td><input id = "value"  name="value" class="easyui-validatebox textbox" ></td>
            </tr>
        </table>
    </div>
    
    <div style="margin:20px 0;"></div>
    
    <table id="dictionarylist" title="�ֵ���Ϣ" class="easyui-datagrid" style="height:370px;" url="dictionaryController/gridform1.do"
           toolbar="#toolbar" pagination="true" rownumbers="true"  fitColumns=false singleSelect="true" resizable="true">
        <thead>
            <tr>
                <th field="tdictionaryid" width="200" hidden="true">ID</th>
                <th field="type" width="200">���</th>
                <th field="code" width="200">ֵ</th>
                <th field=value width="200">����</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openNewUser()">���</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditUser()">�޸�</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">ɾ��</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">���</td>
					<td><input id="type_dlg"  name="type_dlg" class="easyui-validatebox" required="true"  style="width:150px;">
						<input id="tdictionaryid_dlg" name="tdictionaryid_dlg" type="hidden">
					</td>
				</tr>
				<tr>
					<td class="normal" width="200px">ֵ</td>
					<td><input id="code_dlg"  name="code_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">����</td>
					<td><input id="value_dlg"  name="value_dlg" class="easyui-validatebox" required="true" style="width:150px;"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">����</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">�ر�</a>
    </div>
    
  </body>
</html>
