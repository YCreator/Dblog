<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="referrer" content="no-referrer" />
<meta charset="gb2312">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- 启用360浏览器的极速模式(webkit) -->
<meta name="renderer" content="webkit">
<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Dong个人博客</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="${pageContext.request.contextPath}/css/contance.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/images/mini_logo.ico"
	rel="SHORTCUT ICON">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/lib/bootstrap3/css/bootstrap.min.css">
<link
	href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/blog.css"
	rel="stylesheet">
<link
	href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/base.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/index.css"
	rel="stylesheet">
<script type="text/javascript"
	src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/lib/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/sliders.js"></script>

</head>
<body>
	<!-- 一个很赞的网页背景效果 canvas-nest.js
	 ## 配置和配置项
	 - **`color`**: 线条颜色, 默认: `'0,0,0'` ；三个数字分别为(R,G,B)，注意用,分割
	 - **`opacity`**: 线条透明度（0~1）, 默认: `0.5`
	 - **`count`**: 线条的总数量, 默认: `150`
	 - **`zIndex`**: 背景的z-index属性，css属性用于控制所在层的位置, 默认: `-1`
	 -->
	<!-- <script type="text/javascript" color="0,0,255" opacity='0.7'
		zIndex="-2" count="99"
		src="//cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script> -->

	<header>
		<script type="text/javascript">
			var checkData = function() {
				var q = document.getElementById("q").value.trim();
				if (q == null || q == "") {
					alert("请输入您要查询的关键字！");
					return false;
				} else {
					return true;
				}
			}
			var backToTop = function() {
				$('body,html').animate({
					scrollTop : 0
				}, 500);
			}
			var clearCookie = function() {
				$.cookie('网站客服21224', null, {
					path : '/'
				});
				$.cookie('网站客服21224', null, {
					path : '/'
				});
			}
		</script>

		<div class="topbg">
			<ul class="topnav">
				<li class="tag"><a
					href="${pageContext.request.contextPath}/index.html">Home</a></li>
				<li class="tag"><a href="http://weibo.com/jiyouh"
					target="_blank"><font color="red">热门文章</font></a></li>
				<li class="tag"><a href="http://www.jiyouh.com/about.html "
					target="_blank">关于我们</a></li>
			</ul>
		</div>

		<div class="topbgline"></div>

		<div class="logo">
			<div class="logo_l f_l">
				<a href="${pageContext.request.contextPath}/index.html"><img
					src="${pageContext.request.contextPath}/images/logo.png"></a>
			</div>
			<div id="search" class="logo_r f_r">
				<form action="${pageContext.request.contextPath}/blog/q.html"
					class="bs-example bs-example-form" role="search" method="post"
					onsubmit="return checkData()">
					<div class="form-group">
						<input type="text" id="q" name="q" value="${q }"
							class="form-control f_l" placeholder="请输入要查询的关键字..."> <span
							class="input-group-btn f_l"><button type="submit"
								class="btn btn-primary">搜索</button></span>
					</div>
				</form>
			</div>
		</div>

		<nav id="topnav" class="f_r">
			<ul>
				<li><a href="${pageContext.request.contextPath}/index.html">文章首页</a></li>
				<c:forEach var="category" items="${categorys }">
					<li><a
						href="${pageContext.request.contextPath}/index/${category.id }.html">${category.categoryName }</a></li>
				</c:forEach>
			</ul>
			<script src="${pageContext.request.contextPath}/js/nav.js"></script>
		</nav>
	</header>

	<article>
		<div class="l_box f_l">
			<jsp:include page="${mainPage }"></jsp:include>
		</div>

		<div class="r_box f_r">
			<div class="ad300x100">
				<img src="${pageContext.request.contextPath}/images/ad300x100.jpg">
			</div>
			<div class="moreSelect" id="lp_right_select">
				<script>
					window.onload = function() {
						var oLi = document.getElementById("tab")
								.getElementsByTagName("li");
						var oUl = document.getElementById("ms-main")
								.getElementsByTagName("div");

						for (var i = 0; i < oLi.length; i++) {
							oLi[i].index = i;
							oLi[i].onmouseover = function() {
								for (var n = 0; n < oLi.length; n++)
									oLi[n].className = "";
								this.className = "cur";
								for (var n = 0; n < oUl.length; n++)
									oUl[n].style.display = "none";
								oUl[this.index].style.display = "block";
							}
						}
					}
				</script>
				<div class="ms-top">
					<ul class="hd" id="tab">
						<li class="cur"><a href="/">热门文章</a></li>
						<li><a href="/">最新文章</a></li>
						<li><a href="/">点击排行</a></li>
					</ul>
				</div>
				<div class="ms-main" id="ms-main">
					<div style="display: block;" class="bd bd-news">
						<ul>
							<c:forEach var="blog" items="${replyBlogs }">
								<li><a
									href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div class="bd bd-news">
						<ul>
							<c:forEach var="blog" items="${dateBlogs }">
								<li><a
									href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div class="bd bd-news">
						<ul>
							<c:forEach var="blog" items="${clickBlogs }">
								<li><a
									href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!--ms-main end -->
			</div>
			<!--切换卡 moreSelect end -->

			<div class="cloud">
				<h3>标签云</h3>
				<ul>
					<c:forEach var="blogTypeCount" items="${blogTypeCountList }">
						<li><a
							href="${pageContext.request.contextPath}/index.html?typeId=${blogTypeCount.id }">${blogTypeCount.typeName }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="tuwen">
				<h3>图文推荐</h3>
				<ul>
					<c:forEach var="blog" items="${dateBlogs }">
						<li><a
							href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">
								<img src="${blog.picPath}"><b>${blog.title }</b>
						</a>
							<p>
								<span class="tulanmu"><a href="/">${blog.blogTypeDTO.typeName }</a></span>
								<span class="tutime">${blog.releaseDate }</span>
							</p></li>
					</c:forEach>

				</ul>
			</div>
			<div class="ad">
				<img src="http://39.106.161.242:8089/blog/images/03.jpg">
			</div>

			<div class="tit01">
				<h3>关注我</h3>
				<div class="gzwm">
					<ul>
						<li><a class="xlwb" href="#" target="_blank">新浪微博</a></li>
						<li><a class="txwb" href="#" target="_blank">腾讯微博</a></li>
						<li><a class="rss" href="portal.php?mod=rss" target="_blank">RSS</a></li>
						<li><a class="wx" href="mailto:admin@admin.com">邮箱</a></li>
					</ul>
				</div>
			</div>
			<!--tit01 end-->

			<div class="links">
				<h3>
					<span>[<a href="/">申请友情链接</a>]
					</span>友情链接
				</h3>
				<ul>
					<c:forEach var="link" items="${linkList }">
						<li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!--r_box end -->
	</article>

	<footer>
		<p class="ft-copyright">
			Dong博客 Design by Dong <a href="http://www.miitbeian.gov.cn/"><font
				color=white>粤ICP备18001000号-1</font></a>
		</p>
		<div id="tbox">
			<a id="togbook" href="javascript:bind();"></a>
			<!-- onclick='easemobim.bind({tenantId: 21064, emgroup: "订单客服"})' -->
			<a id="gotop" href="javascript:backToTop()"></a>
		</div>
	</footer>

</body>
<%
	if (request.getAttribute("clickBlogs") != null) {
		application.setAttribute("clickBlogs",
				request.getAttribute("clickBlogs"));
	}
	if (request.getAttribute("dateBlogs") != null) {
		application.setAttribute("dateBlogs",
				request.getAttribute("dateBlogs"));
	}
	if (request.getAttribute("replyBlogs") != null) {
		application.setAttribute("replyBlogs",
				request.getAttribute("replyBlogs"));
	}
%>
</html>
