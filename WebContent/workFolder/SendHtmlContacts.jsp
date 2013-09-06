<%@page import="mail.receiver.xml.ReadReceiver"%>
<%@page import="mail.receiver.xml.IsMailinReceiver"%>
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
<script type="text/javascript">
	function setMail() {
		if (addMailReceiver.mail.value == "") {
			alert("请认真填写邮箱");
			return false;
		} 
		return true;
	}
</script>
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
				<!-- 
				<tr>
					<td>收件人：</td>
					<td><input type="text" name="mailto" id="mailto" /> <span
						id="mailto_help" class="help"></span></td>
				</tr>
				 -->
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
		<p class="basicMailMessage">编辑你的收件人</p>
		<%
		Vector<Vector<String>> arr;
		String user = (String)session.getAttribute("userName");
		arr = Readxml.toRead(user);
		%>
		<div class="editMailMessageNews">
			<table frame="box">
				<tr style="border: 1px solid #88ffff">
					<td>姓名</td>
					<td>邮箱</td>
					<td>添加/移除</td>
				</tr>
				
				<%
				Vector<String> ve = ReadReceiver.toRead(user);
				for(int i=0;i<ve.size();i++){
					String mail = ve.elementAt(i);
					String name = "临时";
					for(int j=0;j<arr.elementAt(0).size();j++){
						String name1 = arr.elementAt(0).elementAt(j);
						String mail1 = arr.elementAt(1).elementAt(j);
						if(mail.trim().equals(mail1.trim())){
							name = name1;
						}
					}
					
					if(IsMailinReceiver.isMailinReceiver(user, mail)){
				%>
				<tr>
					<td><%=name %></td>
					<td><%=mail %></td>
					<td>
						<form action="RemoveContacts" method="post">
							<input type="hidden" name="mail" value="<%=mail %>" /> <input
								type="submit" value="移除" />
						</form>
					</td>
				</tr>
				<%}} %>
				
				<%--
				<%
				for(int i=0;i<arr.elementAt(0).size();i++){
					String name = arr.elementAt(0).elementAt(i);
					String mail = arr.elementAt(1).elementAt(i);
					if(IsMailinReceiver.isMailinReceiver(user, mail)){
				%>
				<tr>
					<td><%=name %></td>
					<td><%=mail %></td>
					<td>
						<form action="RemoveContacts" method="post">
							<input type="hidden" name="mail" value="<%=mail %>" /> <input
								type="submit" value="移除" />
						</form>
					</td>
				</tr>
				<%}} %>
				 --%>
				<%
				arr = Readxml.toRead(user);
				for(int i=0;i<arr.elementAt(0).size();i++){
					String name = arr.elementAt(0).elementAt(i);
					String mail = arr.elementAt(1).elementAt(i);
					if(!IsMailinReceiver.isMailinReceiver(user, mail)){
						
				%>
				<tr>
					<td><%=name %></td>
					<td><%=mail %></td>
					<td>
						<form action="AddMailContacts" method="post">
							<input type="hidden" name="mail" value="<%=mail %>" /> <input
								type="submit" value="添加" />
						</form>
					</td>
				</tr>
				<%}} %>
			</table>
		</div>
		<p class="basicMailMessage">添加其他收信人</p>
		<div class="addOtherReceiver">
			<form action="AddMailContacts" method="post" id="addMailReceiver"
				onsubmit="return setMail();">
				<ul>
					<!-- <li>姓名： <input type="text" name="name" value="可填" /></li> -->
					<li>邮箱： <input type="text" name="mail" value="" id="mail" /></li>
					<li><input type="reset" value="重置">&nbsp;&nbsp;&nbsp;<input
						type="submit" value="添加" /></li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>