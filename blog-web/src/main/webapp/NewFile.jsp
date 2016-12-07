<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
<style type="text/css">
	#easemobWidgetDrag {
		background-color: #f26f9b;
	}
</style>
<script type="text/javascript">
/*  var socket = new WebSocket('ws://localhost:8080/blog-web/websocket/client');
 socket.onopen = function(event) {
	 socket.send('hello i am client');
	 socket.onmessage = function(event) {
		 console.log('Client received a message', event);
	 };
	 socket.onclose = function(event) {
		 console.log('Client notified socket has closed',event); 
	 }
 } */
var websocket = null;

//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
   websocket = new WebSocket("ws://localhost:8080/blog-web/websocket/client/client");
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
   setMessageInnerHTML("open");
}

//接收到消息的回调方法
websocket.onmessage = function(event){
   setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
websocket.onclose = function(){
   setMessageInnerHTML("close");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
   websocket.close();
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML){
   document.getElementById('message').innerHTML += innerHTML + '<br/>';
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
function postServer() {
	$.post("${pageContext.request.contextPath}/socket.do");
}
</script>
</head>
<body>
<button onclick="postServer()">post</button>
<div id="message"></div>
</body>
 
</html>