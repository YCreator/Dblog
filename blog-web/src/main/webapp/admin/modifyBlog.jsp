<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改博客页面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" charset="gbk"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js">
	
</script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk"
	src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		
	});

	//选择图片预览
	function previewImg(obj) {
		if (checkImage) {
			var objUrl = getObjectURL(obj.files[0]);
			console.log("objUrl = " + objUrl);
			if (objUrl) {
				$("#myimg").attr("src", objUrl);
				$("#picPath").val("");
			}
		}
	}

	//建立一個可存取到該file的url
	function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}

	function submitData() {
		var title = $("#title").val();
		var blogTypeId = $("#blogTypeId").combobox("getValue");
		var content = UE.getEditor('editor').getContent();
		var keyWord = $("#keyWord").val();
		var picPath = $("#picPath").val();

		if (title == null || title == '') {
			alert("请输入标题！");
		} else if (blogTypeId == null || blogTypeId == '') {
			alert("请选择博客类别！");
		} else if (content == null || content == '') {
			alert("请输入内容！");
		} else {
			$.post("${pageContext.request.contextPath}/admin/blog/save.do", {
				'id' : '${param.id}',
				'title' : title,
				'blogTypeDTO.id' : blogTypeId,
				'content' : content,
				'contentNoTag' : UE.getEditor('editor').getContentTxt(),
				'summary' : UE.getEditor('editor').getContentTxt().substr(0,
						155),
				'keyWord' : keyWord,
				'picPath' : picPath
			}, function(result) {
				if (result.success) {
					alert("博客修改成功！");
				} else {
					alert("博客修改失败！");
				}
			}, "json");
		}
	}

	function uploadImg() {
		if (checkParams()) {
			var pic = $("#picPath").val();
			if (picPath == null || picPath == '') {
				$("#fm").form("submit",
						{
								url : "${pageContext.request.contextPath}/admin/blog/uploadImg.do",
								success : function(result) {
									var result = eval('(' + result + ')');
									if (result.success) {
										$("#picPath").val(result.imgPath);
										submitData();
									} else {
										alert("error");
									}
								}
						});
			} else {
				submitData();
			}
		}
	}

	function checkParams() {
		var title = $("#title").val();
		var blogTypeId = $("#blogTypeId").combobox("getValue");
		var content = UE.getEditor('editor').getContent();
		var keyWord = $("#keyWord").val();
		var picPath = $("#imageFile").val();
		if (title == null || title == '') {
			alert("请输入标题！");
			return false;
		} else if (blogTypeId == null || blogTypeId == '') {
			alert("请选择博客类别！");
			return false;
		} else if (content == null || content == '') {
			alert("请输入内容！");
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body style="margin: 10px">
	<form id="fm" method="post" enctype="multipart/form-data">
		<div id="p" class="easyui-panel" title="修改博客" style="padding: 10px">
			<table cellspacing="20px">
				<tr>
					<td width="80px">博客标题：</td>
					<td><input type="text" id="title" name="title"
						style="width: 400px;" /></td>
				</tr>
				<tr>
					<td>所属类别：</td>
					<td><select class="easyui-combobox" style="width: 154px"
						id="blogTypeId" name="blogTypeDTO.id" editable="false"
						panelHeight="auto">
							<option value="">请选择博客类别...</option>
							<c:forEach var="blogType" items="${blogTypeCountList }">
								<option value="${blogType.id }">${blogType.typeName }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td valign="top">博客内容：</td>
					<td><script id="editor" type="text/plain"
							style="width:100%;height:500px;"></script></td>
				</tr>
				<tr>
					<td>关键字：</td>
					<td><input type="text" id="keyWord" name="keyWord"
						style="width: 400px;" />&nbsp;(多个关键字中间用空格隔开)</td>
				</tr>
				<tr>
					<td>图片介绍：</td>
					<td><div class="uploadImg">
							<img id="myimg" alt="" src=""> <input type="file"
								id="imageFile" name="imageFile" style="width: 400px;"
								multiple="multiple" onchange="previewImg(this);" />
						</div></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="hidden" id="picPath" name="picPath"
						style="width: 400px;" /></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="javascript:uploadImg()"
						class="easyui-linkbutton" data-options="iconCls:'icon-submit'">发布博客</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor', {
			autoHeightEnabled : true,
			autoFloatEnabled : false
		});

		ue.addListener(
				"ready",
				function() {
					//通过ajax请求数据
					UE.ajax.request(
							"${pageContext.request.contextPath}/admin/blog/findById.do",
							{
								method : "post",
								async : false,
								data : {
										"id" : "${param.id}"
								},
								onsuccess : function(result) {
										result = eval("("
												+ result.responseText
												+ ")");
										$("#title").val(result.title);
										$("#keyWord").val(result.keyWord);
										$("#blogTypeId").combobox("setValue",result.blogTypeDTO.id);
										$("#picPath").val(result.picPath);
										UE.getEditor('editor').setContent(result.content);
										$("#myimg").attr("src", "${pageContext.request.contextPath}"+result.picPath);
									}
							});
		});
	</script>
</body>
</html>

