<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="topnews">
      <h2><span>${categoryName } </span><b>博客</b>文章</h2>
      <c:forEach var = "blog" items = "${blogList }">
	      <div class="blogs">
	        <figure><img src="${blog.picPath}"></figure>
	        <ul>
	          <h3><a href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></h3>
	          <p class="content">${blog.summary}......</p>
	          <p class="autor">
	          	  <span class="lm f_l"><a href="/">${blog.blogTypeDTO.typeName }</a></span>
		          <span class="dtime f_l">${blog.releaseDate }</span>
		          <span class="viewnum f_r">浏览（<a href="/">${blog.clickHit}</a>）</span>
		          <span class="pingl f_r">评论（<a href="/">${blog.replyHit }</a>）</span>
	          </p>
	        </ul>
	      </div>
      </c:forEach>
    </div>
	  <div>
		<nav>
		  <ul class="pagination pagination-sm">
		    ${pageCode }
		  </ul>
		</nav>
</div>