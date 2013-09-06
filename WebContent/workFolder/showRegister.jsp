<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="mail.bean.Register"%>
<jsp:useBean id="user" type="mail.bean.Register" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
</head>
<body>
	<div id="register">
		<ul>
			<li>你的注册姓名是： <jsp:getProperty name="user" property="name" />
			</li>
			<li>你的注册性别是： <jsp:getProperty name="user" property="gender" />
			</li>
			<li>您的注册邮箱是： <jsp:getProperty name="user" property="mail" />
			</li>
			<li>您的注册电话是： <jsp:getProperty name="user" property="phone" />
			</li>
			<li>您的个人简介：</li>
			<li><TextArea name="message" rows="6" cols="30"><jsp:getProperty
						name="user" property="message" /></TextArea></li>
		</ul>
		<table>
			<tr>
				<td><input type="button" value="确认"
					onclick="location='home.jsp'" /></td>
			</tr>
		</table>
	</div>
</body>
</html>