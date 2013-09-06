<%@page import="mail.helper.GetLoveSet"%>
<%@page import="mail.global.GetPath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="mail.bean.Register"%>
<%@ page import="mail.contacts.xml.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%--mod by starsasumi --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="user" type="mail.bean.Register" scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="head.txt"%>

<script type="text/javascript">
	function setPasswordSubmit() {
		if (setPassword.mailPassword.value == "") {
			alert("密码不能为空!");
			return false;
		}
		return true;
	}

	function setMailContacts() {
		if (addContactor.name.value == "") {
			alert("备注名不能为空!");
			return false;
		} else if (addContactor.mail.value == "") {
			alert("密码不能为空!");
			return false;
		}
		return true;
	}

	function addLoveSet() {
		return true;
	}
</script>
</head>
<body>
	<%
		String logname = user.getName();
		boolean mailPasswordFlag = user.getfMailPassWord();
	%>

	<div class="basicinf">
		<table id="user" frame="void">
			<tr>
				<td>姓名</td>
				<td><jsp:getProperty name="user" property="name" /></td>
			</tr>
			<tr>
				<td>性别</td>
				<td><jsp:getProperty name="user" property="gender" /></td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><jsp:getProperty name="user" property="mail" /></td>
			</tr>
			<tr>
				<td>手机</td>
				<td><jsp:getProperty name="user" property="phone" /></td>
			</tr>
		</table>
		<input type="submit" id="modifyButton" value="修改信息"
			onclick="javascript:location.href='modify.jsp'" /> <input
			type="submit" id="logoutButton" value="切换用户"
			onclick="javascript:location.href='logIn.jsp'" />
	</div>

	<div id="usemessage">
		<div class="fenge">
			<img src="content/fenge.gif">
		</div>
		<div id="mailPasswordSet">
			<p class="user_title">设置您的密码</p>
			<%
				if (mailPasswordFlag) {//用户已经设置了自己的邮箱密码
			%>
			<p class="user_para">
				您已经设置了 <em><jsp:getProperty name="user" property="mail" /></em> 的密码
			</p>
			<!-- <input type="submit" value="查看修改" class="paraButton"
				onclick="javascript:location.href='modify.jsp'" /> -->
			<%
				}
				else {			//用户没有设置自己的邮箱密码
			%>
			<p class="user_para">
				您尚未设置 <em><jsp:getProperty name="user" property="mail" /></em> 的密码，
				可以在下放输入栏里里面设置。设置密码可以避免您每次发送邮件时临时输入。
			</p>
			<form action="SetPassword" method="post" id="setPassword"
				onsubmit="return setPasswordSubmit();">
				<p class="para_setmes">
					您的密码： <input type="text" id="mailPassword" name="mailPassword" />
					<input type="submit" value="点此设置" />
				</p>
			</form>
			<%
				}
			%>
		</div>
		<div class="fenge">
			<img src="content/fenge.gif">
		</div>
		<div id="love_set">
			<p class="user_title">您的喜好设置</p>
			<%
				Vector<Vector<String>> ve = GetLoveSet.getLoveSet(user.getName());
			%>
			<div class="love_set_two">
				<p class="user_title">你选择的爱好有：</p>
				<form action="DelectLove" method="post">
					<%for(int i=0;i<ve.elementAt(0).size();i++){ %>
					<label><input type="checkbox" name="Choosed"
						value="<%= ve.elementAt(0).elementAt(i) %>" /> <%=ve.elementAt(0).elementAt(i) %>
					</label>
					<%} %>

					<%if(ve.elementAt(0).size() > 0){ %>
					<br /> <input type="submit" value="删除" />
					<%}else{ %>
					<p class="user_para">您没有设置任何喜好</p>
					<%} %>
				</form>
			</div>

			<div class="love_set_two">
				<p class="user_title">您未选的爱好有：</p>
				<form action="AddLove" method="post" id="addLoveSet"
					onSubmit="return addLoveSet();">
					<%for(int i=0;i<ve.elementAt(1).size();i++){ %>
					<label><input type="checkbox" name="unChoosed"
						id="<%="choosed" + i %>"
						value="<%= ve.elementAt(1).elementAt(i) %>" /> <%=ve.elementAt(1).elementAt(i) %>
					</label>
					<%} %>
					<%if(ve.elementAt(1).size() > 0){ %>
					<br /> <input type="submit" value="添加" />
					<%}else{ %>
					<p class="user_para">您选择了所有的喜好</p>
					<%} %>
				</form>
			</div>


		</div>
	</div>


	<div id="cencernmessage">
		<div class="fenge">
			<img src="content/fenge.gif">
		</div>
		<div id="user_friend">
			<p class="user_title">您的好友列表</p>
			<p class="user_para">您可以直接将您的好友添加为您的收信人。</p>
			<%
				String path= GetPath.getPath() + "contactsFolder/"
					+ user.getName() + ".xml";
				File xmlFile = new File(path);
				if (!xmlFile.exists()) {
					xmlFile.createNewFile();
				}
				Vector<Vector<String>> arr = Readxml.toRead(user.getName());
				if(arr == null){
			%>
			<p class="user_para">您尚未添加联系人！</p>
			<%
				}else{
			%>
			<p class="user_para">您的联系人信息：</p>
			<div id="contact_message">
				<table frame="void">
					<%
					for(int i=0;i<arr.elementAt(0).size();i++){
						String name = arr.elementAt(0).elementAt(i);
						String mail = arr.elementAt(1).elementAt(i);
						out.print("<tr>");
						out.print("<td>" + name + "</td>");
						out.print("<td>" + mail + "</td>");
						out.print("</tr>");
					}
				}
			%>
				</table>
			</div>
			<p class="user_para">添加您的联系人：</p>
			<form action="AddContacts" method="post" id="addContactor"
				onSubmit="return setMailContacts();">
				<div id="add_contactor">
					<ul>
						<li>姓名：<input type="text" id="name" name="name" /></li>
						<li>邮箱：<input type="text" id="mail" name="mail" /></li>
					</ul>
					<input type="reset" value="重设" class="add_button" /> <input
						type="submit" value="添加" class="add_button" />
				</div>
			</form>
		</div>
	</div>

</body>
</html>