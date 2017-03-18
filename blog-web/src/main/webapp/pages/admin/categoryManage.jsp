<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客主分类管理页面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;
	var temp_list;

	/*打开新增分类的窗口 */
	function openCategoryAddDialog() {
		$.post("${pageContext.request.contextPath}/admin/blogType/list.do", {
			page : "1",
			rows : "100"
		}, function(data, status) {
			if (status == "success") {
				var obj = $.parseJSON(data);
				createBlogTypeTable(obj.rows, 5, createTableRows);
			}

		});
		$("#dlg").dialog({
			height : 300,
			width : 530
		}).dialog("open").dialog("setTitle", "添加主分类信息");
		resetValue();
		url = "${pageContext.request.contextPath}/admin/category/save.do";
	}

	/*打开修改分类的窗口 */
	function openCategoryModifyDialog() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		temp_list = row.list;
		$.post("${pageContext.request.contextPath}/admin/blogType/list.do", {
			page : "1",
			rows : "100"
		}, function(data, status) {
			if (status == "success") {
				var obj = $.parseJSON(data);
				createBlogTypeTable(obj.rows, 5, createModifyRows);
			}

		});
		$("#dlg").dialog({
			height : 300,
			width : 530
		}).dialog("open").dialog("setTitle", "编辑主分类信息");
		$("#fm").form("load", row);
		url = "${pageContext.request.contextPath}/admin/category/save.do?id="
				+ row.id;
	}

	/*删除分类提示窗口 */
	function deleteCategory() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示","您确定要删除这<font color=red>" 
						+ selectedRows.length 
						+ "</font>条数据吗？",
						function(r) {
							if (r) {
								$.post("${pageContext.request.contextPath}/admin/category/delete.do",
										{
											ids : ids
										},
										function(result) {
											if (result.success) {
												if (result.exist) {
													$.messager.alert(
															"系统提示",
															result.exist);
												} else {
													$.messager.alert(
															"系统提示",
															"数据已成功删除！");
												}
												$("#dg").datagrid("reload");
											} else {
												$.messager.alert(
														"系统提示",
														"数据删除失败！");
											}
										}, "json");
							}
						});
	}

	/*rows->数据源 ， rowNum -> 表的最大行数 ， callback -> 生成表列的回调方法  */
	function createBlogTypeTable(rows, rowNum, callback) {
		var len = getJsonLength(rows);
		var l = Math.floor(len / rowNum);
		var r = len % rowNum;
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
					tableStr = tableStr + callback(rows[i + j], false);
				}
				tableStr = tableStr + "</tr>";
			}
		} else if (l == 0) {
			for (var i = 0; i < len; i++) {
				tableStr = tableStr + "<tr>" + callback(rows[i], false)
						+ "</tr>";
			}
		}
		/* 添加一个隐藏的选中的checkbox，解决当只选中一个的时候提交json字符串会被拆分不全以至于解析json出错的问题  */
		tableStr = tableStr
				+ "<tr style='display:none;'><td><input name='blogTypeJsons' type='checkbox' value='' checked='checked' /></td><td></td><tr>"
		tableStr = tableStr + "</table>";
		$("#dataTable").html(tableStr);
	}

	function createTableRows(row, checked) {
		return "<td><input id='mycheckbox' name='blogTypeJsons' type='checkbox' value='"
				+ JSON.stringify(row)
				+ "' "
				+ (checked ? "checked='checked' " : "")
				+ "/></td><td>"
				+ row.typeName + "</td>";
	}

	function createModifyRows(row, checked) {
		$.each(temp_list, function(i, item) {
			if (row.id == item.id) {
				checked = true;
				return false;
			}
		});
		return createTableRows(row, checked);
	}

	/*保存分类 */
	function saveCategory() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$.messager.alert("系统提示", "保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示", "保存失败！");
					return;
				}
			}
		});
	}

	/*重置表单内的参数 */
	function resetValue() {
		$("#categoryName").val("");
		$("#sort").val("");
		//'input:checkbox'
		$("#mycheckbox").each(function() {
			$(this).attr("checked", false);
		});
	}

	function closeCategoryDialog() {
		$("#dlg").dialog("close");
		resetValue();
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
		for ( var i in obj) {
			var property = obj[i];
			description += i + " = " + property + "\n";
		}
		alert(description);
	}

	/*获取json内的对象长度 */
	function getJsonLength(jsonData) {
		var jsonLength = 0;
		for ( var item in jsonData) {
			jsonLength++;

		}
		return jsonLength;
	}
</script>
</head>
<body style="margin: 1px">
	<table id="dg" title="博客主分类管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/admin/category/list.do"
		fit="true" toolbar="#tb">
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
			<a href="javascript:openCategoryAddDialog()"
				class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openCategoryModifyDialog()"
				class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteCategory()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 500px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">

		<form id="fm" method="post">
			<table cellspacing="8px" style="width: 500px; height: 200px">
				<tr>
					<td>主分类名称：</td>
					<td><input type="text" id="categoryName" name="categoryName"
						class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>分类排序：</td>
					<td><input type="text" id="sort" name="sort"
						class="easyui-numberbox" required="true" style="width: 60px" />&nbsp;(根据排序序号从小到大排序)</td>
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
		<a href="javascript:saveCategory()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeCategoryDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>