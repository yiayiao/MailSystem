<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
<style type="text/css">
.title h1 {
	margin: 25px 0 0 80px;
}
</style>
</head>
<body>
	<div class="homeMessage">
		<p><%=(String)session.getAttribute("userName") %>您好！欢迎使用个性信息定制发布系统。本系统为南京理工大学计算机学院10学生梁懿、石夏星、
		黄楠轩的科研训练作品，如果您在使用中遇到任何问题，可以通过下面的联系方式联系我们</p>
		<ul>
			<li>电话号码： 15062226891</li>
			<li>邮箱： 13438749539@163.com</li>
		</ul>
	</div>
	
</body>
</html>