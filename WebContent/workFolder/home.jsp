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
	<%
		/*没有登录时的界面*/
		if (session.getAttribute("userName") == null) {
	%>
	<div class="homeMessage">
		<p>您好！欢迎使用个性信息定制发布系统。您可以在新闻库中找到实时的热点新闻,通过邮件发送给自己或者您朋友。
			您也可以在个人中心里设置自己的喜好，系统将根据您的喜好向您发送实时的热点新闻。</p>
		<ul>
			<li>您还没有账号，赶紧<a href="register.jsp">注册</a>吧
			</li>
			<li>您已经有账号，赶紧<a href="logIn.jsp">登陆</a>吧
			</li>
			<li>或者，您想要<a href="contract.jsp">联系我们</a>
			</li>
		</ul>
	</div>

	<%
		}
		else {/*已经登录过后的界面*/
	%>
	<div class="homeMessage">
		<p><%=(String) session.getAttribute("userName")%>您好！欢迎使用个性信息定制发布系统。您可以在新闻库中找到实时的热点新闻,通过邮件发送给自己或者您朋友。
			您也可以在个人中心里设置自己的喜好，系统将根据您的喜好向您发送实时的热点新闻。
		</p>
		<ul>
			<li>您想查看热点新闻，点此进入<a href="newsCenter.jsp">新闻库</a>
			</li>
			<li>您想修改个人信息，点此进入<a href="LeadtoUsercenter">个人中心</a>
			</li>
			<li>或者，您想要<a href="contactUs.jsp">联系我们</a>
			</li>
		</ul>
	</div>


	<%
		}
	%>

</body>
</html>