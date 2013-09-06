<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
<script type="text/javascript">
	function LogIn_Submit() {
		if (regConfig.logname.value == "") {
			alert("用户名不能为空!");
			return false;
		} else if (regConfig.password.value == "") {
			alert("密码不能为空!");
			return false;
		}
		return true;
	}
	window.onload = function() {
		document.getElementById("logname").onblur = function(evt) {
			checklogname(this, document.getElementById('logname_help'));
		}
		document.getElementById("password").onblur = function(evt) {
			checkpassword(this, document.getElementById('password_help'));
		}
		//结束标记
		initSeats();
	}
	//非空检测
	function validateNonEmpty(inputField, helpText) {
		if (inputField.value.length == 0) {
			if (helpText != null)
				helpText.innerHTML = "Please entr a message.";
			return false;
		}
		if (helpText != null)
			helpText.innerHTML = "OK";
		return true;
	}
	//验证表单数据
	function checklogname(inputField, helpText) {
		if (!validateNonEmpty(inputField, helpText))
			return false;
		return true;
	}
	function checkpassword(inputField, helpText) {
		if (!validateNonEmpty(inputField, helpText))
			return false;
		return true;
	}
</script>
</head>

<body>
	<div id="logIn">
		<form action="HandleLogin" method="post" id="regConfig"
			name="regConfig" onsubmit="return LogIn_Submit();">
			<p class="tis">请输入您的用户名和密码</p>

			<ul>
				<li>姓名： <input type="text" id="logname" name="logname" />* <span
					id="logname_help" class="help"></span>
				</li>
				<li>密码： <input type="password" id="password" name="password" />*
					<span id="password_help" class="help"></span>
				</li>
			</ul>
			<table>
				<tr>
					<td><input type="reset" value="重置"></td>
					<td><input type="submit" value="登录"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>