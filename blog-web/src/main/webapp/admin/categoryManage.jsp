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
		/* $.ajax({
			type:'POST',
			url:'${pageContext.request.contextPath}/admin/blogType/list.do?page=1&rows=10',
			dataType:'json',
			success:function(data){
				var description = "";
				var rows = data['rows'];
				var tableStr = "<table border='1'>";
				  tableStr = tableStr + "<thead><tr><th></th><th>博客类型名称</th></tr></thead>";
			    for(var i in rows){   
			        var property=rows[i];
			        tableStr = tableStr + "<tr><td><input type='checkbox' /></td><td>" + property['typeName'] + "</td></tr>"
			    }   
			    tableStr = tableStr + "</table>";
			    $("#dataTable").html(tableStr); 
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		}); */
		$.post("${pageContext.request.contextPath}/admin/blogType/list.do",
					{
						page:"1",
						rows:"100"
					},
					function(data, status) {
						if (status == "success") {
							var obj = $.parseJSON(data);
							var rows = obj.rows;
							var len = getJsonLength(rows);
							var l = Math.floor(len / 5);
							var r = len % 5;
							var tableStr = "<table>";
							if (l > 0) {
								var z = 0;
								for (var i = 0; i < len; i = i + z) {
									if (r > 0) {
										z = l + 1;
										r--;
									} else {
										z = l;
									}
									tableStr = tableStr + "<tr>";
									for (var j = 0; j < z; j++) {
										tableStr = tableStr + "<td><input name='blogTypeJsons' type='checkbox' value='"+ JSON.stringify(rows[i+j]) +"' /></td><td>" + rows[i+j].typeName+ "</td>";		
									}
									tableStr = tableStr + "</tr>";
								}
							} else if (l == 0) {
								for (var i = 0; i < len; i++) {		
									tableStr = tableStr + "<tr>"
										+"<td><input name='blogTypeJsons' type='checkbox' value='"+ JSON.stringify(rows[i]) +"' /></td><td>" + rows[i].typeName + "</td>"
										+"</tr>";	
								}
							}
							tableStr = tableStr + "</table>";
						    $("#dataTable").html(tableStr);
						}
						
					}
				);
		$("#dlg").dialog({height:300, width:530}).dialog("open").dialog("setTitle","添加主分类信息");
		url="${pageContext.request.contextPath}/admin/category/save.do";
	}
	
	function saveCategory() {
		$("#fm").form("submit", {
			url:url,
			onSubmit:function() {
				/* var array = new Array();
				$('input:checkbox').each(function(i, item){
					if ($(this).attr("checked")) {
						alert($(this).val());
					} 
				}); */
				return true;
			},
			success:function(result) {
				var result=eval('('+result+')');
				if (result.success) {
					$.messager.alert("系统提示","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示","保存失败！");
					return;
				}
			}
		});
	}
	
	function check() {
		var content = $('#fm').serialize();
		/* var obj = new Object();
		obj.categoryName = $("#category").val();
		obj.sort = $("#sort").val();
		var content = "result:";
		$('input:checkbox').each(function(){
			if ($(this).attr("checked")) {
				content = content + $(this).val() + ",";
			} else {
				content = content + ",";
			}
		}); */
		alert(content);
	}
	
	function checkClick(obj) {
		if ($(obj).attr("checked")) {
			$(obj).attr("checked", false);
		} else {
			$(obj).attr("checked", true);
		}
	}
	
	/*打印对象内的所有属性值 */
	function printObject(obj) {
		var description = ""; 
	    for(var i in obj){   
	        var property=obj[i];   
	        description+=i+" = "+property+"\n";
	    }   
	    alert(description); 
	}
	
	/*获取json内的对象长度 */
	function getJsonLength(jsonData){
		var jsonLength = 0;
		for(var item in jsonData){
			jsonLength++;

		}
		return jsonLength;

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
   		<th field="categoryName" width="30" align="center">博客主分类名称</th>
   		<th field="ids" width="50" align="center">包含的博客类别名称</th>
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
   	<table cellspacing="8px" style="width:500px;height:200px">
   		<tr>
   			<td>主分类名称：</td>
   			<td><input type="text" id="categoryName" name="categoryName" class="easyui-validatebox" required="true"/></td>
   		</tr>
   		<tr>
   			<td>分类排序：</td>
   			<td><input type="text" id="sort" name="sort" class="easyui-numberbox" required="true" style="width: 60px"/>&nbsp;(友情链接根据排序序号从小到大排序)</td>
   		</tr>
   		<tr>
   			<td>关联博客类别：</td>
   			<td>
				<div id="dataTable"></div>
			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCategory()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:void()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>