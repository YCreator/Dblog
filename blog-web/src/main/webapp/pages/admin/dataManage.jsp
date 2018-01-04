<%@page import="com.dong.blog.web.util.ConfigUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/lib/jquery-easyui-1.3.3/themes/default/easyui.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#result_message {
		margin: 30px 30px;
		padding: 10px 10px;
		background-color: #FFFAFA;
		height:300px;
		overflow-y:scroll; 
		border:0.5px solid;
		border-color:#cccccc;
	}
</style>
<%@page import="com.dong.blog.web.util.ConfigUtil " %>
<% String ws = String.format("ws://%s/blog-web/websocket/client/client", ConfigUtil.SOCKET_CLIENT_HOST); %>
<script type="text/javascript">
 var websocket = null;
 
 Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}

//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
 websocket = new WebSocket("<%=ws%>");
}
else{
 alert('Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function(){
 setMessageInnerHTML("error");
};

//连接成功建立的回调方法
websocket.onopen = function(event){
 var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
 setMessageInnerHTML(date+"  准备就绪...");
}

//接收到消息的回调方法
websocket.onmessage = function(event){
var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
if (event.data.indexOf("count:")>=0) {
	var div = document.getElementById('count');
	div.innerHTML = event.data.split(":")[1];
} else {
	setMessageInnerHTML(date+"  "+event.data);
}
 
}

//连接关闭的回调方法
websocket.onclose = function(){
	var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
 setMessageInnerHTML(date+"	结束...");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
 websocket.close();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML){
	var div = document.getElementById('result_message');
	div.innerHTML += innerHTML + '<br/>';
	div.scrollTop = div.scrollHeight;
}

//关闭连接
function closeWebSocket(){
 websocket.close();
}

//发送消息
function send(){
 var message = document.getElementById('text').value;
 websocket.send(message);
} 

function collect() {
var startPage = $("#startPage").val();
var endPage = $("#endPage").val();
//$.post("${pageContext.request.contextPath}/socket.do");
		 $.post("${pageContext.request.contextPath}/collect.do", {
			startPage : startPage,
			endPage : endPage
		}, function() {
			
		}) 
}
</script>
<title>数据管理页面</title>
</head>
<body>
	<div>
		<ul>
			<li><b>码农网</b>&nbsp;&nbsp;起始页：<input type="text" id="startPage" name="startPage" value="1" />  结束页：<input type="text" id="endPage" name="endPage" value="1">&nbsp;&nbsp;<Button onclick="collect()">抓取</Button></li>
		</ul>
	</div>
	<div id="result_message">
	</div> 
</body>
</html>