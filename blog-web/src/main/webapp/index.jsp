<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="gb2312">
<title>Dong个人博客</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link
	href="${pageContext.request.contextPath}/static/images/mini_logo.ico"
	rel="SHORTCUT ICON">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/blog.css" rel="stylesheet">
<link
	href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/static/mystyle/css/base.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/static/mystyle/css/index.css"
	rel="stylesheet">
<script type="text/javascript"
	src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/mystyle/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/mystyle/js/sliders.js"></script>
<!--[if lt IE 9]>
<script src="js/modernizr.js"></script>
<![endif]-->
<!-- 返回顶部调用 begin -->
<script type="text/javascript" src="js/up/jquery.js"></script>
<script type="text/javascript" src="js/up/js.js"></script>
<!-- 返回顶部调用 end-->
</head>
<body>
	<header>
		<script type="text/javascript">
			function checkData() {
				var q = document.getElementById("q").value.trim();
				if (q == null || q == "") {
					alert("请输入您要查询的关键字！");
					return false;
				} else {
					return true;
				}
			}
			function backToTop() {
				$('body,html').animate({scrollTop:0},500);
			}
		</script>
		<div class="topbg">
			<ul class="topnav">
				<a href="http://www.jiyouh.com/ " target="_blank">Home</a>
				<a href="http://weibo.com/jiyouh" target="_blank">官方微博</a>
				<a href="http://www.jiyouh.com/about.html " target="_blank">关于我们</a>
			</ul>
		</div>
		<div class="topbgline"></div>
		<div class="logo">
			<div class="logo_l f_l">
				<a href="${pageContext.request.contextPath}/index.html"><img
					src="${pageContext.request.contextPath}/static/mystyle/logo.png"></a>
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
				<a href="${pageContext.request.contextPath}/index.html">首页</a>
				<a href="p.html" target="_blank">文章</a>
				<a href="a.html" target="_blank">相册</a>
				<a href="c.html" target="_blank">资源</a>
				<a href="b.html" target="_blank">留言</a>
				<a href="news.html" target="_blank">关于我</a>
			</ul>
			<script src="js/nav.js"></script>
		</nav>
	</header>
	<article>
		<div class="l_box f_l">
			<jsp:include page="${mainPage }"></jsp:include>
		</div>
		<div class="r_box f_r">
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
			<div class="ad300x100">
				<img
					src="${pageContext.request.contextPath}/static/mystyle/images/ad300x100.jpg">
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
								oUl[this.index].style.display = "block"
							}
						}
					}
				</script>
				<div class="ms-top">
					<ul class="hd" id="tab">
						<li class="cur"><a href="/">点击排行</a></li>
						<li><a href="/">最新文章</a></li>
						<li><a href="/">站长推荐</a></li>
					</ul>
				</div>
				<div class="ms-main" id="ms-main">
					<div style="display: block;" class="bd bd-news">
						<ul>
							<c:forEach var="blog" items="${clickBlogs }">
								<li><a href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div class="bd bd-news">
						<ul>
							<c:forEach var="blog" items="${dateBlogs }">
								<li><a href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">${blog.title }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div class="bd bd-news">
						<ul>
							<li><a href="/" target="_blank">手机的16个惊人小秘密，据说99.999%的人都不知</a></li>
							<li><a href="/" target="_blank">你面对的是生活而不是手机</a></li>
							<li><a href="/" target="_blank">住在手机里的朋友</a></li>
							<li><a href="/" target="_blank">豪雅手机正式发布! 在法国全手工打造的奢侈品</a></li>
							<li><a href="/" target="_blank">教你怎样用欠费手机拨打电话</a></li>
							<li><a href="/" target="_blank">原来以为，一个人的勇敢是，删掉他的手机号码...</a></li>
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
						<li><a href="${pageContext.request.contextPath}/index.html?typeId=${blogTypeCount.id }">${blogTypeCount.typeName }</a></li>
					</c:forEach>
					<!-- <li><a href="/">个人博客</a></li>
					<li><a href="/">web开发</a></li>
					<li><a href="/">前端设计</a></li>
					<li><a href="/">Html</a></li>
					<li><a href="/">CSS3</a></li>
					<li><a href="/">Html5+css3</a></li>
					<li><a href="/">百度</a></li>
					<li><a href="/">Javasript</a></li>
					<li><a href="/">web开发</a></li>
					<li><a href="/">前端设计</a></li>
					<li><a href="/">Html</a></li>
					<li><a href="/">CSS3</a></li>
					<li><a href="/">Html5+css3</a></li>
					<li><a href="/">百度</a></li> -->
				</ul>
			</div>
			<div class="tuwen">
				<h3>图文推荐</h3>
				<ul>
					<li><a href="/"><img
							src="${pageContext.request.contextPath}/resources/images/01.jpg"><b>住在手机里的朋友</b></a>
						<p>
							<span class="tulanmu"><a href="/">手机配件</a></span><span
								class="tutime">2015-02-15</span>
						</p></li>
					<li><a href="/"><img
							src="${pageContext.request.contextPath}/resources/images/02.jpg"><b>教你怎样用欠费手机拨打电话</b></a>
						<p>
							<span class="tulanmu"><a href="/">手机配件</a></span><span
								class="tutime">2015-02-15</span>
						</p></li>
					<li><a href="/" title="手机的16个惊人小秘密，据说99.999%的人都不知"><img
							src="${pageContext.request.contextPath}/resources/images/03.jpg"><b>手机的16个惊人小秘密，据说...</b></a>
						<p>
							<span class="tulanmu"><a href="/">手机配件</a></span><span
								class="tutime">2015-02-15</span>
						</p></li>
					<li><a href="/"><img
							src="${pageContext.request.contextPath}/resources/images/06.jpg"><b>住在手机里的朋友</b></a>
						<p>
							<span class="tulanmu"><a href="/">手机配件</a></span><span
								class="tutime">2015-02-15</span>
						</p></li>
					<li><a href="/"><img
							src="${pageContext.request.contextPath}/resources/images/04.jpg"><b>教你怎样用欠费手机拨打电话</b></a>
						<p>
							<span class="tulanmu"><a href="/">手机配件</a></span><span
								class="tutime">2015-02-15</span>
						</p></li>
				</ul>
			</div>
			<div class="ad">
				<img
					src="${pageContext.request.contextPath}/resources/images/03.jpg">
			</div>
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
		<p class="ft-copyright">Dong博客 Design by DanceSmile
			蜀ICP备11002373号-1</p>
		<div id="tbox">
			<a id="togbook" href="javascript:void(0)"></a> <a id="gotop" href="javascript:backToTop()"></a>
		</div>
	</footer>
</body>
<%
	if (request.getAttribute("clickBlogs") != null) {
		application.setAttribute("clickBlogs", request.getAttribute("clickBlogs"));
	}
	if (request.getAttribute("dateBlogs") != null) {
		application.setAttribute("dateBlogs", request.getAttribute("dateBlogs"));
	}
%>
</html>
