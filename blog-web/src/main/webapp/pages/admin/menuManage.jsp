<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理页面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/icon.css">
<%-- <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/fontIconPicker-2.0.0/css/jquery.fonticonpicker.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/fontIconPicker-2.0.0/themes/bootstrap-theme/jquery.fonticonpicker.bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/fontIconPicker-2.0.0/icomoon/style.css" /> --%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/icons.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/shCore.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/fontIconPicker-2.0.0/jquery.fonticonpicker.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/fontIconPicker-2.0.0/iconpicker.js"></script> --%>

<script type="text/javascript">
	var url;
	
	$(function() {
		$("#dg").datagrid({
			toolbar: '#tb',
			fitColumns:true, 
			pagination:true, 
			rownumbers:true, 
			url:'${pageContext.request.contextPath}/admin/menu/list.do',
			fit:true,
			columns:[[
			  		{field:'cb',checkbox:'true',align:'center'},
			  		{field:'id',title:'编号',width:10,align:'center'},
			  		{field:'icon',title:'图标',width:10,align:'center',
			  			formatter:function(value, row, index){
			  				var div = "<div style='width:16px; height:16px; margin:0 auto;' class="
			  				return div + value + "></div>";
			  			}},
			  		{field:'title',title:'标题',width:50,align:'center'},
			  		{field:'menuType',title:'类型',width:20,align:'center'},
			  		{field:'createTime',title:'创建时间',width:30,align:'center'},
			  		{field:'pageUrl',title:'页面路径',width:100,align:'center'},
			  		{field:'description',title:'描述',width:100,align:'center'},
			      ]],
		});
		
		$('#icon').combo({
			required : true,
			editable : false
		});
		
		$('#sp').appendTo($('#icon').combo('panel'));
	});
	
	var openMenuAddDialog = function() {
		getIcons();
		$("#dlg").dialog("open").dialog("setTitle", "添加菜单");
		$("#menuType").removeAttr("disabled");
		url = "${pageContext.request.contextPath}/admin/menu/save.do";
	}

	var openMenuModifyDialog = function() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要编辑的数据！");
			return;
		}
		getIcons();
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改菜单");
		$("#fm").form("load", row);
		$('#icon').combo('setValue', row.icon).combo('setText', row.icon)
				.combo('hidePanel');
		$('#div-icon').attr("class", row.icon);
		$("#menuType").attr("disabled", "disabled");
		url = "${pageContext.request.contextPath}/admin/menu/save.do?id="
				+ row.id;
	}

	var openMenuDeleteDialog = function() {
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
		$.messager
				.confirm(
						"系统提示",
						"您确定要删除这<font color=red>" + selectedRows.length
								+ "</font>条数据吗？",
						function(r) {
							if (r) {
								$.post(
												"${pageContext.request.contextPath}/admin/menu/delete.do",
												{
													ids : ids
												},
												function(result) {
													if (result.success) {
														if (result.exist) {
															$.messager
																	.alert(
																			"系统提示",
																			result.exist);
														} else {
															$.messager.alert(
																	"系统提示",
																	"数据已成功删除！");
														}
														$("#dg").datagrid(
																"reload");
													} else {
														$.messager.alert(
																"系统提示",
																result.message);
													}
												}, "json");
							}
						});
	}

	var getIcons = function() {
		var iconJson = $.ajax({
			url : "${pageContext.request.contextPath}/css/icons.json",
			async : false
		});
		var iconObj = eval('(' + iconJson.responseText + ')');
		for (var i = 0; i < iconObj.length; i++) {
			var value = iconObj[i].icon;
			$("#sp")
					.append(
							"<div style='display:flex; margin:6px;'><div style='width:16px; height:16px' class="
				+ value +"></div ><span style='margin-left:3px;cursor:pointer'>"
									+ value + "</span></div>")
		}
		$('#sp span').click(
				function() {
					var v = $(this).text();
					$('#icon').combo('setValue', v).combo('setText', v).combo(
							'hidePanel');
					$('#div-icon').attr("class", v);
				});
	}

	var saveMenu = function() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				return $(this).form("validate");
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

	var closeMenuDialog = function() {
		$("#dlg").dialog("close");
		resetValue();
	}

	function resetValue() {
		$("#title").val("");
		$('#icon').combo('setValue', '').combo('setText', '').combo('hidePanel');
		$("#menuType").val("");
		$("#pageUrl").val("");
		$("#description").val("");
		$("#sort").val("");
		$("#div-icon").attr("class", "");
	}

</script>

</head>
<body style="margin: 1px">
	<table id="dg" class="easyui-datagrid" title="菜单管理"></table>
	<div id="tb">
		<div>
			<a href="javascript:openMenuAddDialog()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openMenuModifyDialog()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:openMenuDeleteDialog()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;标题：&nbsp;<input type="text" id="s_title" size="20"
				onkeydown="if(event.keyCode==13) searchBlog()" /> <a
				href="javascript:searchBlog()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 600px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">

		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>菜单标题：</td>
					<td><input type="text" id="title" name="title"
						class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>图标：</td>
					<td>
						<div style="display: flex;">
							<select id="icon" style="width: 150px" name="icon"
								data-options="panelWidth: 200">
							</select>
							<div id="div-icon"
								style='width: 16px; height: 16px; margin: 3px;'></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>菜单类型：</td>
					<td><select type="text" id="menuType" name="menuType"
						style="width: 80px">
							<option value="目录">目录</option>
							<option value="菜单">菜单</option>
							<option value="窗口">窗口</option>
					</select></td>
				</tr>
				<tr>
					<td>页面路径：</td>
					<td><input type="text" id="pageUrl" name="pageUrl"
						class="easyui-validatebox" required="true" style="width: 370px" /></td>
				</tr>
				<tr>
					<td>菜单描述：</td>
					<td><textarea id="description" name="description"
							class="easyui-validatebox" required="false" cols="50" rows="10"></textarea></td>
				</tr>
				<tr>
					<td>排序：</td>
					<td><input type="text" id="sort" name="sort"
						class="easyui-numberbox" required="" false"" style="width: 60px" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="sp" class="easyui-panel"></div>

	<div id="dlg-buttons">
		<a href="javascript:saveMenu()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeMenuDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>