<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/icons.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	$(function() {
		$("#tt").treegrid({
			title : '菜单关系表',
			toolbar : '#tb',
			url : "${pageContext.request.contextPath}/admin/menu/getTree.do",
			method : "post",
			rownumbers: true,
			idField : 'id',
			treeField : 'title',
			animate : true,
			rownumbers : true,
			fit : true,
			loadFilter : function(data,parentId) {
				var rows = data.rows;
				$.each(rows, function(i) {
					var row = rows[i];
					var arr = new Array();
					$.each(row.children, function(j){
						var child = row.children[j];
						var r = new Object();
						r.id = child.id;
						r.title = child.title;
						r.pageUrl = child.pageUrl;
						r.description = child.description;
						r.isParent = child.isParent;
						r.parentId = row.id;
						row.children[j] = r;
					});
				});
				return data;
			},
			columns : [[
						{title:'菜单名',field:'title',width:180},
						{title:'页面路径',field:'pageUrl',width:200},
						{title:'描述',field:'description',width:800},
						{title:'操作',field:'id',width:100,align:'center',formatter:formatOper},
			]]
		});
		
		$('#cid').combo({
			required : true,
			editable : false
		});
		
		$('#csp').appendTo($('#cid').combo('panel'));
		
		$('#pid').combo({
			required : true,
			editable : false
		});
		
		$('#psp').appendTo($('#pid').combo('panel'));
	});
	
	var formatOper = function(val,row,index) {
		if (row.isParent == false) {
			val = row.parentId + "-" + val;
		}
		return "<a href=\"#\" onclick=\"relieveRelation('"+val+"')\">解除关系</a>";
	}
	
	function relieveRelation(val) {
		window.console.log(val);
		if (val) {
			$.messager.confirm("系统提示","您确定要解除这个菜单的关系吗？",
					function(r) {
						if (r) {
							if (val.indexOf("-") == -1) {
								parentRelieve(val);
							} else {
								var vals = val.split("-");
								childRelieve(vals[0], vals[1]);
							}
						}
					});
		}
	}
	
	var parentRelieve = function(parentId) {
		window.console.log(parentId);
		$.post("${pageContext.request.contextPath}/admin/menu/parentRelieve.do",
			{id:parentId},
			function(result) {
				if (result.success) {
					if (result.exist) {
						$.messager.alert("系统提示", result.exist);
					} else {
						$.messager.alert("系统提示", "解除成功");
					}
					$("#tt").treegrid("reload");
				} else {
					$.messager.alert(
							"系统提示",
							"解除失败");
				}
			}
			,"json"
		);
	}
	
	var childRelieve = function(parentId, childId) {
		window.console.log(parentId+'_'+childId);
		$.post("${pageContext.request.contextPath}/admin/menu/childRelieve.do",
				{
					pid:parentId,
					cid:childId
				},
				function(result) {
					if (result.success) {
						if (result.exist) {
							$.messager.alert("系统提示", result.exist);
						} else {
							$.messager.alert("系统提示", "解除成功");
						}
						$("#tt").treegrid("reload");
					} else {
						$.messager.alert(
								"系统提示",
								"解除失败");
					}
				}
				,"json"
			);
	}
	
	var openRelationDialog = function() {
		getChildren();
		getParent();
		$("#dlg").dialog("open").dialog("setTitle", "建立菜单关系");
		url = "${pageContext.request.contextPath}/admin/menu/addRelationship.do";
	}
	
	var createRelation = function() {
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
					$("#tt").treegrid("reload");
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
		$('#cid').combo('setValue', '').combo('setText', '').combo('hidePanel');
		$('#pid').combo('setValue', '').combo('setText', '').combo('hidePanel');
	}
	
	var getChildren = function() {
		$('#csp').empty();
		$.post("${pageContext.request.contextPath}/admin/menu/getAllChildNoParent.do", function(data) {
			if (data.success) {
				var d = data.data;
				$.each(d, function(i){
					var value = d[i];
					window.console.log(value);
					$("#csp").append("<div style='padding:6px;cursor:pointer' value='"+value.id+"'>"+value.title+"</div>");
				});
				$('#csp div').click(
						function() {
							var v = $(this).attr("value");
							var t = $(this).text();
							$('#cid').combo('setValue', v).combo('setText', t).combo(
									'hidePanel');
				});
			}
		});
	}
	
	var getParent = function() {
		$('#psp').empty();
		$.post("${pageContext.request.contextPath}/admin/menu/getParent.do", function(data) {
			if (data.success) {
				var d = data.data;
				$.each(d, function(i){
					var value = d[i];
					$("#psp").append("<div style='padding:6px;cursor:pointer' value='"+value.id+"'>"+value.title+"</div>");
				});
				$('#psp div').click(
						function() {
							var v = $(this).attr("value");
							var t = $(this).text();
							$('#pid').combo('setValue', v).combo('setText', t).combo(
									'hidePanel');
				});
			}
		});
	}
</script>
</head>
<body style="margin: 1px">
	<table id="tt" class="easyui-treegrid"></table>
	<div id="tb">
		<a href="javascript:openRelationDialog()" class="easyui-linkbutton"
			iconCls="icon-add" plain="true">建立关系</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">

		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>子菜单：</td>
					<td><select id="cid" style="width: 150px" name="cid"
						data-options="panelWidth: 200">
					</select></td>
				</tr>
				<tr>
					<td>目录：</td>
					<td><select id="pid" style="width: 150px" name="pid"
						data-options="panelWidth: 200">
					</select></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="csp"></div>

	<div id="psp"></div>

	<div id="dlg-buttons">
		<a href="javascript:createRelation()" class="easyui-linkbutton"
			iconCls="icon-ok">建立关系</a> <a href="javascript:closeMenuDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>