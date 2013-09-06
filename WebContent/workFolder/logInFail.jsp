<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
<meta http-equiv="refresh" content="1;url=logIn.jsp">
<style type="text/css">
.title h1 {
	margin: 25px 0 0 80px;
}
</style>
</head>
<body>
	<%
		String ErrorLogName = (String) request.getAttribute("ErrorLogName");
	%>
	<div class="ShowMessage">
		<p class="message">对不起，登录失败！</p>
		<p class="message"><%=ErrorLogName %></p>
	</div>
</body>
</html>