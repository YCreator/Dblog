<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客主分类管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

	var url;
	
	function openLinkAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加友情链接信息");
		url="${pageContext.request.contextPath}/admin/category/save.do";
	}
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客主分类管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/admin/category/list.do" fit="true" toolbar="#tb">
    <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="10" align="center">编号</th>
   		<th field="categoryname" width="30" align="center">博客主分类名称</th>
   		<th field="typeIds" width="50" align="center">包含的博客类别名称</th>
   		<th field="sort" width="20" align="center">排序序号</th>
   	</tr>
   </thead>
</table>
<div id="tb">
 	<div>
 	    <a href="javascript:openLinkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
 		<a href="javascript:void()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:void()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 	</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
 </div>
 
 <div id="dlg" class="easyui-dialog" style="width:500px;height:200px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>主分类名称：</td>
   			<td><input type="text" id="linkName" name="linkName" class="easyui-validatebox" required="true"/></td>
   		</tr>
   		<tr>
   			<td>分类排序：</td>
   			<td><input type="text" id="orderNo" name="orderNo" class="easyui-numberbox" required="true" style="width: 60px"/>&nbsp;(友情链接根据排序序号从小到大排序)</td>
   		</tr>
   		<tr>
   			<td>关联博客类别：</td>
   			<td>
				<table id="dg" title=""
					   fitColumns="true" pagination="true" rownumbers="true"
					   url="${pageContext.request.contextPath}/admin/blogType/list.do" fit="true" toolbar="#tb">
					   <thead>
					   	<tr>
					   		<th field="cb" checkbox="true" align="center"></th>
					   		<th field="typeName" width="100" align="center">博客类型名称</th>
					   	</tr>
					   </thead>
					 </table>
			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:void()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:void()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>