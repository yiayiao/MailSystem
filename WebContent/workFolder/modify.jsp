<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="mail.bean.Register"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>

<script type="text/javascript">
	function Form_Submit() {
		if (modConfig.logname.value == "") {
			alert("用户名不能为空!");
			return false;
		} else if (modConfig.opassword.value == "") {
			alert("旧密码不能为空!");
			return false;
		} else if (modConfig.npassword.value == "") {
			alert("新密码不能为空!");
			return false;
		} else if (modConfig.npassword.value != modConfig.rpassword.value) {
			alert("两次输入的新密码不一致!");
			return false;
		}
		return true;
	}

	//初始化javascript函数调用
	window.onload = function() {
		document.getElementById("logname").onblur = function(evt) {
			checklogname(this, document.getElementById('logname_help'));
		}
		document.getElementById("opassword").onblur = function(evt) {
			checkpassword(this, document.getElementById('opassword_help'));
		}
		document.getElementById("npassword").onblur = function(evt) {
			checkpassword(this, document.getElementById('npassword_help'));
		}
		document.getElementById("rpassword").onblur = function(evt) {
			checkrpassword(this, document.getElementById('rpassword_help'));
		}
		document.getElementById("email").onblur = function(evt) {
			checkemail(this, document.getElementById('email_help'));
		}
		document.getElementById("phone").onblur = function(evt) {
			checktelephone(this,document.getElementById('phone_help'));
		}
		//结束标记
		initSeats();
	}
	//非空检测
	function validateNonEmpty(inputField, helpText) {
		if(inputField.value.length == 0) {
			if(helpText != null)
				helpText.innerHTML = "Please entr a message.";
			return false;
		}
		if(helpText != null)
			helpText.innerHTML = "OK";
		return true;
	}
	//正则表达式验证
	function validateRegEx(regex, inputStr, helpText, helpMessage) {
		if(!regex.test(inputStr)) {
			if(helpText != null)
				helpText.innerHTML = helpMessage;
			return false;
		}
		else {
			if(helpText != null)
				helpText.innerHTML = "OK";
			return true;
		}
	}
	//验证表单数据
	function checklogname(inputField, helpText) {
		if(!validateNonEmpty(inputField, helpText))
			return false;
		return true;
	}
	function checkpassword(inputField, helpText) {
		if(!validateNonEmpty(inputField, helpText))
			return false;
		return true;
	}
	function checkrpassword(inputField, helpText) {
		if(!validateNonEmpty(inputField, helpText))
			return true;
		return false;
	}
	function checktelephone(inputField, helpText) {
		if(!validateNonEmpty(inputField, helpText))
			return false;
		var reg = /^\d{11}$/;
		var mes = "Please enter a telephone num(11).";
		if(!validateRegEx(reg, inputField.value, helpText, mes))
			return false;
		return ture;
	}
	function checkemail(inputField, helpText) {
		if(!validateNonEmpty(inputField, helpText))
			return false;
		var reg = /^[\w\.-_\+]+@[\w-]+(\.\w{2,4})+$/;
		var mes = "Please enter a email.";
		if(!validateRegEx(reg, inputField.value, helpText, mes))
			return false;
		return ture;
	}
</script>
</head>
<jsp:useBean id="user" type="mail.bean.Register" scope="session" />
<body>
	<div id="modify">
		<p class="tis">请修改您的信息，注意带*的必填</p>
		<form action="helpModify" method="post" id="modConfig"
			name="modConfig" onsubmit="return Form_Submit();">
			<ul>
				<li>姓名： 
					<input type="text" id="logname" name="logname" value='<jsp:getProperty property="name" name="user"/>' />* 
					<span id="logname_help" class="help" ></span>
					<input type="hidden" id="ologname" name="ologname" value='<jsp:getProperty property="name" name="user"/>' />
				</li>
				<li>原密码：
					<input type="password" id="opassword" name="opassword" />*
					<span id="opassword_help" class="help"></span>
				<li>新密码：
					<input type="password" id="npassword" name="npassword" />*
					<span id="npassword_help" class="help"></span>
				</li>
				<li>新密码确认：
					<input type="password" id="rpassword" name="rpassword" />*
					<span id="rpassword_help" class="help"></span>
				</li>
				<li>性别：
					<% 
					String userGender = ((Register) request.getSession().getAttribute("user")).getGender();
					if(userGender.equals("male")){ %>
					<input type="radio" name="gender" checked="checked" value="male">男 
					<input type="radio" name="gender" value="female">女
					<% 
					} else {%> 
					<input type="radio" name="gender" value="male">男 
					<input type="radio" name="gender" checked="checked" value="female">女
					<%} %>
				</li>
				<li>邮箱：
					<input type="text" id="email" name="email" value='<jsp:getProperty property="mail" name="user" />' />* 
					<span id="email_help" class="help"></span>
				</li>
				<li>电话：
					<input type="text" id="phone" name="phone" value='<jsp:getProperty property="phone" name="user" />' />* 
					<span id="phone_help" class="help"></span>
				</li>
				<li>您的个人简介：</li>
				<li><TextArea name="message" rows="6" cols="50"><jsp:getProperty property="message" name="user" /></TextArea></li>
			</ul>
			<table>
				<tr>
					<td><input type="reset" value="重置"></td>
					<td><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>