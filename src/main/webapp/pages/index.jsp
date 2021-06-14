<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.framework.common.security.vo.LoginUserVO"%>
<%@ taglib uri="/mytaglib" prefix="cc" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

LoginUserVO vo = (LoginUserVO)session.getAttribute("LOGINUSER");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>��ᱣ�Ͽ��ۺϷ���ƽ̨</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <cc:basic path="<%=path %>"/>

	<style type="text/css">
	.cs-tab-menu {
		width: 120px;
	}

	.head_a {
		height: 60px;
		float: left;
	}
	.head_aa {
	    height: 30px;
		float: left;
		font-size: 12px;
	}
	.head_ab {
		height: 30px;
		float: right;
	}
	</style>
	<script type="text/javascript">
	$(function(){
	    $.ajaxSetup({
	        complete:function(XMLHttpRequest,textStatus){   
        	    var sessionstatus=XMLHttpRequest.getResponseHeader('sessionstatus'); //ͨ��XMLHttpRequestȡ����Ӧͷ,sessionstatus�� 
                if(sessionstatus=='timeout'){  
                    alert('��½�Ѿ���ʱ�������µ�½��'); 
                    //��ת����½ҳ��
        		    location.href='<%=path %>/pages/login.jsp';
        	   }   
            }   
	    })
	})
	</script>	
	<script type="text/javascript">
	 	$(function(){	
	 	    $('#asyncTree').tree({
	            onClick: function(node){
		            var isleaf =  $('#asyncTree').tree('isLeaf', node.target);
		            if(isleaf){//�ж��Ƿ���Ҷ��
                        //�ж�ҳ���Ƿ����
                        if($('#tabs').tabs('exists', node.text)){
                            $('#tabs').tabs('select', node.text);//ѡ�в�ˢ��
		                    var currTab = $('#tabs').tabs('getSelected');
		                    var url = $(currTab.panel('options').content).attr('src');
		                    //url���ڣ����Ҳ�����ҳ
		                    if(url != undefined && currTab.panel('options').title != '��ҳ'){
			                    $('#tabs').tabs('update',{
				                    tab:currTab,
				                    options:{content:createFrame(url)}
			                    });
		                    }
	                    }else{
		                    var content = createFrame(node.attributes.url);
		                    $('#tabs').tabs('add',{
			                    title:node.text,
			                    content:content,
			                    closable:true
		                    });
	                    }
	                    tabClose();
		            }  
	            }
            });
            //����url����iframe
            function createFrame(url) {
	            var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	            return s;
            }
	 	    function tabClose() {
	        /*˫���ر�TABѡ�*/
	            $(".tabs-inner").dblclick(function(){
		            var subtitle = $(this).children(".tabs-closable").text();
		            $('#tabs').tabs('close',subtitle);
	            });
	        /*Ϊѡ����Ҽ�*/
	            $(".tabs-inner").bind('contextmenu',function(e){
		            $('#mm').menu('show', { left: e.pageX,top: e.pageY });
		            var subtitle =$(this).children(".tabs-closable").text();
		            $('#mm').data("currtab",subtitle);
		            $('#tabs').tabs('select',subtitle);
		            return false;
	            });
            }
            //���Ҽ��˵��¼�
            function tabCloseEven() {
	            //ˢ��
	            $('#mm-tabupdate').click(function(){
		            var currTab = $('#tabs').tabs('getSelected');
		            var url = $(currTab.panel('options').content).attr('src');
		            if(url != undefined && currTab.panel('options').title != '��ҳ') {
		            	$('#tabs').tabs('update',{
		            		tab:currTab,
		            		options:{ content:createFrame(url) }
			            });
		            }
	            });
		        //�رյ�ǰ
		        $('#mm-tabclose').click(function(){
		            var currtab_title = $('#mm').data("currtab");
		            $('#tabs').tabs('close',currtab_title);
		        });
	            //ȫ���ر�
		        $('#mm-tabcloseall').click(function(){
		        	$('.tabs-inner span').each(function(i,n){
		        		var t = $(n).text();
		        		if(t != '��ҳ') {
		        			$('#tabs').tabs('close',t);
		        		}
		        	});
		        });
		        //�رճ���ǰ֮���TAB
		        $('#mm-tabcloseother').click(function(){
		        	var prevall = $('.tabs-selected').prevAll();
		        	var nextall = $('.tabs-selected').nextAll();		
		        	if(prevall.length>0){
		        		prevall.each(function(i,n){
		        			var t=$('a:eq(0) span',$(n)).text();
			        		if(t != '��ҳ') {
		        				$('#tabs').tabs('close',t);
			        		}
			        	});
			        }
			        if(nextall.length>0) {
			        	nextall.each(function(i,n){
				        	var t=$('a:eq(0) span',$(n)).text();
				        	if(t != '��ҳ') {
				        		$('#tabs').tabs('close',t);
			        		}
				        });
			        }
			        return false;
		        });
	        }
            tabCloseEven();
	 	});
	 	//���޸�����ҳ��
        function openupdate(){
            $('#dlg').dialog('open').dialog('setTitle','�޸�����');
            $('#fm').form('clear');
        }
	 	
	 	//�޸�����
        function updatepassword(){
            $('#fm').form('submit',{
                url: 'loginUserController/updatePassWord.do',
                onSubmit: function(){
                    //�ж��������Ƿ�һ��
                    if($('#password_new1').val()!=$('#password_new2').val()){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '��������ȷ�������벻һ�£�'
                        }); 
                        return false ;
                    }
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg!=''){
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: result.errorMsg+''
                        });
                    } else {
                        $('#dlg').dialog('close');      
                        $.messager.show({
                            title: 'ϵͳ��Ϣ',
                            msg: '����ɹ���'
                        });
                    }
                }
            });
        }
        
        //�˳�ϵͳ
        function logout(){
		    $.messager.confirm('��ʾ', '��ȷ��Ҫ�˳���', function(r) {
				if (r) {
					window.location.href='<%=path %>/loginUserController/logout.do';
				}
			});
		}
	</script>
    
    
  </head>
  
  <body class="easyui-layout">  
	<div data-options="region:'north',split:true,border:true" style="height:70px;"class="cs-north">
		<div class="head_a" >
			<div class="head_aa">��ᱣ�Ͽ��ۺϷ���ƽ̨</div>
			<div class="head_ab" >
			          �û�����<%=vo.getUsername()%>
			          ��ɫ��<%=vo.getRolename()%>
				<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'" style="width:80px" onclick="openupdate()">�޸�����</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-back'" style="width:80px" onclick="logout()">�˳�</a>
			</div>
		</div>
	</div>
	
    <div data-options="region:'west',title:'����',split:true" style="width:200px;">
    
    <ul id="asyncTree" class="easyui-tree" data-options="url:'loginUserController/getUserTree.do'"></ul> 
    
    </div>  
    
    <div id="mainPanle" data-options="region:'center',border:true,border:false " >
		<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
            <div title="��ҳ">
				<div >
					������football98<br>
					˵����jQuery EasyUI 1.3.0
				</div>
			</div>
        </div>
    </div> 
    
    <div data-options="region:'south',split:true,border:false" style="height:25px;background:#EAEEF5;"><center>����֧��:�ػʵ����׿Ƽ����޹�˾</center></div>  
    
    <div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate">ˢ��</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">�ر�</div>
		<div id="mm-tabcloseother">�ر�����</div>
		<div id="mm-tabcloseall">�ر�ȫ��</div>
	</div>
	
	 <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" data-options="resizable:true,modal:true" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
			<table>
				<tr>
					<td class="normal" width="200px">ԭ����</td>
					<td><input id="password_old"  name="password_old" type="password" class="easyui-validatebox" required="true"  style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">������</td>
					<td><input id="password_new1"  name="password_new1" type="password"  class="easyui-validatebox" required="true"  style="width:150px;"></td>
				</tr>
				<tr>
					<td class="normal" width="200px">ȷ��������</td>
					<td><input id="password_new2"  name="password_new2" type="password"  class="easyui-validatebox" required="true"  style="width:150px;"></td>
				</tr>
			</table>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updatepassword()">����</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">�ر�</a>
    </div>
    
  </body>  
</html>
