<%@page import="mail.contacts.xml.Readxml"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="mail.news.xml.*"%>
<%@ page import="com.rsslibj.elements.Channel"%>
<%@ page import="com.rsslibj.elements.Item"%>
<%@ page import="com.rsslibj.elements.RSSReader"%>
<%@ page import="mail.global.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="electric.xml.ParseException"%>
<%@ page import="mail.global.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
<style type="text/css">
.title h1 {
	margin: 25px 0 0 80px;
}
</style>
<body>
	<%
	String logName = (String) session.getAttribute("userName");
	String mailPostor = GetMailPassword.getMailPostor(logName);
	String mailPassword = GetMailPassword.getMailPassword(logName);
	%>
	<div class="editMailMessageParaMessage">
		<p>
			请填写您的收件人等基本邮件信息，确认无误后，点击提交发送邮件；如果您需要修改您邮件的新闻信息，请点击链接： <a
				href="editMailMessage.jsp">编辑邮件新闻内容</a>
		</p>
	</div>
	<div id="sendHtml">
		<p class="basicMailMessage">基本邮件信息</p>
		<p class="tis">请填写以下信息,收件人用' ; '分隔</p>
		<form action="HandleSendHtml" method="post">
			<table>
				<tr>
					<td>发件人：</td>
					<td><input type="text" name="mailfrom" id="mailfrom"
						value="<%=mailPostor %>" /> <span id="mailfrom_help" class="help"></span></td>
				</tr>
				<tr>
					<td>发件人密码：</td>
					<td><input type="password" id="password" name="password"
						value="<%=mailPassword %>" /> <span id="password_help"
						class="help"></span></td>
				</tr>
				<tr>
					<td>收件人：</td>
					<!-- 
					<td><input type="text" name="mailto" id="mailto" /> <span
						id="mailto_help" class="help"></span></td>
					-->
					<td><input type="button" name="addContactor" value="添加联系人"
						onclick="javascript:window.location.href='SendHtmlContacts.jsp'"></td>
				</tr>
				<tr>
					<td>主题：</td>
					<td><input type="text" id="subject" name="subject" /> <span
						id="subject_help" class="help"></span></td>

				</tr>
			</table>
			<ul>
				<li>邮件内容：</li>
				<li><TextArea id="context" name="context" rows="15" cols="50"></TextArea>
				</li>
			</ul>
			<table>
				<tr>
					<td><input type="reset" value="重置"></td>
					<td><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="editMailMessageNews">
		<p class="basicMailMessage">邮件中的新闻信息</p>
		<%
		Vector<Vector<String>> Ve;
		ReadMessage my = new ReadMessage();
		String user = (String)session.getAttribute("userName");
		Ve = my.toRead(user);
		for (int i = 0; i < Ve.elementAt(0).size(); i++) {
			String choose = Ve.elementAt(2).elementAt(i);
			String chooseCN = null;
			if(choose.equals("all")){
				chooseCN = "首页";
			}
			else if(choose.equals("beauty")){
				chooseCN = "美丽";
			}
			else if(choose.equals("crime")){
				chooseCN = "犯罪";
			}
			else if(choose.equals("diy")){
				chooseCN = "DIY";
			}
			else if(choose.equals("fact")){
				chooseCN = "事实";
			}
			else if(choose.equals("health")){
				chooseCN = "健康";
			}
			else if(choose.equals("logos")){
				chooseCN = "理性";
			}
			else if(choose.equals("nature")){
				chooseCN = "自然";
			}
			else if(choose.equals("psybst")){
				chooseCN = "心理";
			}
			else if(choose.equals("sex")){
				chooseCN = "性情";
			}
		%>
		<div class="editChoosedNewsFeng">
			<p class="ChoosedNewsTitile">
				标题：<%= Ve.elementAt(0).elementAt(i) %>
			</p>
			<p class="ChoosedNewsTitile">
				选自：<%= chooseCN %>
			</p>
			<p class="ChoosedNewsTitile">
				链接：<a href="<%= Ve.elementAt(1).elementAt(i) %>"><%= Ve.elementAt(1).elementAt(i) %></a>
			</p>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>