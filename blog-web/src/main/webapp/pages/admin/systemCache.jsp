<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/default/easyui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#dg").datagrid({
				rownumbers:true, 
				url:'${pageContext.request.contextPath}/systemCache.json',
				fit:true,
				columns:[[
				  		{field:'id',title:'编号',width:50,align:'center'},
				  		{field:'name',title:'缓存领域名',width:120,align:'center'},
				  		{field:'keyName',title:'缓存键名',width:120,align:'center'},
				  		{field:'cacheMethod',title:'缓存方式',width:120,align:'center'},
				  		{field:'_operation',title:'操作',width:100,align:'center',formatter:formatOper},
				      ]],
			});
	});
	
	var formatOper = function(val, row, index) {
		return "<a href=\"#\" onclick=\"clearCache('"+index+"')\">刷新缓存</a>";
	}
	
	var clearCache = function(index) {
		$('#dg').datagrid('selectRow',index);
		var row = $("#dg").datagrid("getSelected");
		if (row) {
			$.messager.confirm(
					"系统提示",
					"您确定要清除<font color=red>" + row.name+ "</font>的缓存吗？",
					function(r) {
						if (r) {
							$.post("${pageContext.request.contextPath}/admin/system/refreshSystem.do"
									,{
										id: row.id,
										key: row.keyName,
									 	method: row.cacheMethod
									 }
									,function(result) {
										if (result.success) {
											$.messager.alert("系统提示", "刷新完毕!");
										} else {
											$.messager.alert("系统提示", "刷新失败!");
										}
									},"json");
						}	
					});
		}
	}
</script>
</head>
<body style="margin: 1px">
<table id="dg" class="easyui-datagrid" title="缓存管理"></table>
</body>
</html>