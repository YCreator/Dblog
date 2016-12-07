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

//�жϵ�ǰ������Ƿ�֧��WebSocket
if('WebSocket' in window){
   websocket = new WebSocket("ws://localhost:8080/blog-web/websocket/client/client");
}
else{
   alert('Not support websocket')
}

//���ӷ�������Ļص�����
websocket.onerror = function(){
   setMessageInnerHTML("error");
};

//���ӳɹ������Ļص�����
websocket.onopen = function(event){
   setMessageInnerHTML("open");
}

//���յ���Ϣ�Ļص�����
websocket.onmessage = function(event){
   setMessageInnerHTML(event.data);
}

//���ӹرյĻص�����
websocket.onclose = function(){
   setMessageInnerHTML("close");
}

//�������ڹر��¼��������ڹر�ʱ������ȥ�ر�websocket���ӣ���ֹ���ӻ�û�Ͽ��͹رմ��ڣ�server�˻����쳣��
window.onbeforeunload = function(){
   websocket.close();
}

//����Ϣ��ʾ����ҳ��
function setMessageInnerHTML(innerHTML){
   document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//�ر�����
function closeWebSocket(){
   websocket.close();
}

//������Ϣ
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