<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/mytaglib" prefix="cc"%>
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
<cc:basic path="<%=path %>" />

<script type="text/javascript">
	$(function() {
		//初始胡ajax树
		$('#asyncTree').tree({
			onClick : function(node) {
				$.post('permissionController/getPermission.do', {
					id : node.id
				}, function(result) {
					$('#form1').form('load', {
						permissionname : result.permissionname,
						tpermissionid : result.tpermissionid,
						action : result.action,
						url : result.url,
						parentid : result.parentid,
						orders : result.orders,
						isleaf : result.isleaf + ''
					});
				}, 'json');
			}
		});
	});

	$.extend($.fn.validatebox.defaults.rules, {
		number : {
			validator : function(value, param) {
				return /^[0-9]*$/.test(value);
			},
			message : "请输入数字"
		}
	});
	
	//添加按钮
	function clearForm() {
		//添加必须存在父节点id
		var node = $('#asyncTree').tree('getSelected');
		var isleaf = $('#isleaf').combobox('getValue');
		if (isleaf == 1) {
			$.messager.show({
				title : '系统消息',
				msg : '无法添加该权限！'
			});
		} else if (node == null) {
			$.messager.show({
				title : '系统消息',
				msg : '请选择节点!'
			});
		} else {
			$('#form1').form('clear');
			$('#tpermissionid').val(node.id);
		}
	}
	var url;
	//保存
	function addForm() {
		if ($('#parentid').val() == '') {
			url = 'permissionController/addPermission.do';
		} else {
			url = 'permissionController/updatePermission.do';
		}
		;
		$('#form1').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg != '') {
					$.messager.show({
						title : '系统消息',
						msg : result.errorMsg
					});
				} else {
					$('#form1').form('clear');
					$('#asyncTree').tree('reload');
					$.messager.show({
						title : '系统消息',
						msg : '保存成功！'
					});
				}
			}
		});
	}
	//删除
	
	function deleteForm() {
		$.messager.confirm('提示', '你确定要删除这条信息吗？', function(r) {
			if (r) {
				$.post('permissionController/deletePermission.do', {
					tpermissionid : $('#tpermissionid').val()
				}, function(result) {
					if (result.errorMsg != '') {
						$.messager.show({
							title : '系统消息',
							msg : result.errorMsg
						});
					} else {
						$('#form1').form('clear');
						$('#asyncTree').tree('reload');
						$.messager.show({
							title : '系统消息',
							msg : '删除成功！'
						});
					}
				}, 'json');
			}
		});
	}
</script>
</head>

<body class="easyui-layout" >

	<div data-options="region:'west',title:'权限列表',split:true" style="width:200px;">
		<ul id="asyncTree" class="easyui-tree" data-options="url:'permissionController/getUserTree.do'"></ul>
	</div>
	
	<div id="mainPanle" data-options="region:'center',border:true,border:false " style="padding:10px 10px 10px 10px">

		<div class="easyui-panel" style="padding:5px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true"	data-options="iconCls:'icon-add'" style="width:80px" onclick="clearForm()">添加</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-save'" style="width:80px" onclick="addForm()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-remove'" style="width:80px" onclick="deleteForm()">删除</a>
		</div>
		
		<div style="margin:20px 0;"></div>
		
		<div class="easyui-panel" style="padding:5px;">
		<form id="form1" method="post" >
			<table cellpadding="5">
				<tr>
					<td class="normal" style="width:200px;">节点ID:</td>
					<td><input id="tpermissionid" name="tpermissionid" style="width:200px;" readonly="readonly" class="easyui-validatebox textbox" data-options="required:true">
					<input type="hidden" id="parentid" name="parentid"/>
					</td>
				</tr>
				<tr>
					<td class="normal" style="width:200px;">权限名:</td>
					<td><input id="permissionname" name="permissionname" style="width:200px;" class="easyui-validatebox textbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td class="normal" style="width:200px;">权限类型:</td>
					<td><input id="action" name="action" style="width:200px;" class="easyui-combobox" data-options=" url:'baseController/getComboBox.do?type=ACTION',required:true,valueField:'id',textField:'text',editable:false"> 
				</tr>
				<tr>
					<td class="normal" style="width:200px;">URL:</td>
					<td><input id="url" name="url" style="width:200px;"  class="easyui-validatebox textbox" data-options="required:true">
					</td>
				<tr>
				<tr>
					<td class="normal" style="width:200px;">序号:</td>
					<td><input id="orders" name="orders" style="width:200px;"  class="easyui-validatebox textbox" data-options="required:true,validType:'number'">
					</td>
				<tr>
				<tr>
					<td class="normal" style="width:200px;">是否为叶子节点:</td>
					<td><input id="isleaf" name="isleaf" style="width:200px;" class="easyui-combobox" data-options=" url:'baseController/getComboBox.do?type=ISLEAF',required:true,valueField:'id',textField:'text',editable:false"> </td>
				</tr>
			</table>
		</form>
		</div>
		
	</div>
</body>
</html>
