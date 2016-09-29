<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="banner">
      <div id="slide-holder">
        <div id="slide-runner"> 
        <a href="/" target="_blank"><img id="slide-img-1" src="${pageContext.request.contextPath}/static/mystyle/images/a1.jpg"  alt="" /></a> 
        <a href="/" target="_blank"><img id="slide-img-2" src="${pageContext.request.contextPath}/static/mystyle/images/a2.jpg"  alt="" /></a> 
        <a href="/" target="_blank"><img id="slide-img-3" src="${pageContext.request.contextPath}/static/mystyle/images/a3.jpg"  alt="" /></a> 
        <a href="/" target="_blank"><img id="slide-img-4" src="${pageContext.request.contextPath}/static/mystyle/images/a4.jpg"  alt="" /></a>
          <div id="slide-controls">
            <p id="slide-client" class="text"><strong></strong><span></span></p>
            <p id="slide-desc" class="text"></p>
            <p id="slide-nav"></p>
          </div>
        </div>
      </div>
      <script>
	  if(!window.slider) {
		var slider={};
	}

	slider.data= [
    {
        "id":"slide-img-1", // 与slide-runner中的img标签id对应
        "client":"标题1",
        "desc":"这里修改描述 这里修改描述 这里修改描述" //这里修改描述
    },
    {
        "id":"slide-img-2",
        "client":"标题2",
        "desc":"add your description here"
    },
    {
        "id":"slide-img-3",
        "client":"标题3",
        "desc":"add your description here"
    },
    {
        "id":"slide-img-4",
        "client":"标题4",
        "desc":"add your description here"
    } 
	];

	  </script> 
    </div>
    <!-- banner代码 结束 -->
    <jsp:include page="${listPage }"></jsp:include>