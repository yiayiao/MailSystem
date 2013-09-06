package mail.controler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Register;
import mail.global.Connect;
import mail.global.GetPath;
import mail.news.xml.CreateXml;
import mail.receiver.xml.CreateReceiver;

public class HandleLogin extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

/*
 * get it -->页面跳转的时候，ie浏览器存在问题，暂时还没有解决，，，看来很有必要搞清楚response.sendRedirect
 * response.setHeader dispatcher
 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String result = "";
		String logName = null;
		try {
			logName = request.getParameter("logname").trim();
		}
		catch (NullPointerException e) {
			result = "请输入用户名";
			request.setAttribute("ErrorLogName", result);
		}
		if (logName == "" || logName == null || logName.length() == 0) {
			try {
				result = "请输入用户名（不超过20个字符）";
				request.setAttribute("ErrorLogName", result);
			}
			catch (Exception e) {
			}
		}
		String password = request.getParameter("password").trim();
		if (password == "" || password == null || password.length() == 0) {
			try {
				result = "请输入密码（不超过20个字符）";
				request.setAttribute("ErrorLogName", result);
			}
			catch (Exception e) {
			}
		}


		Register register = new Register();
		try {
			Connection con = Connect.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			///////////////////////用户的基本信息
			String sql = "select * from user where name='" + logName
				+ "' and password = '" + password + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				register.setName(rs.getString("name"));
				register.setMail(rs.getString("mail"));
				register.setGender(rs.getString("gender"));
				register.setPhone(rs.getString("phone"));
				register.setMessage(rs.getString("message"));
				result = "Login successful";
			}
			else {
				result = "没有找到您的用户信息";
				request.setAttribute("ErrorLogName", result);
				//response.sendRedirect("logInFail.jsp");
			}
			/////////////////////////////用户的邮箱密码信息
			sql = "select * from mail_password where name='" + logName +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				register.setfMailPassWord(true);
				register.setMailPassword(rs.getString("password"));
			}
			
			rs.close();
			stmt.close();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (result.equals("Login successful")) {
			request.getSession().setAttribute("user", register);
			request.getSession().setAttribute("userName", logName);
			////////////////////生成新闻信息文件夹
			String path = GetPath.getPath() + "newsFolder/" + register.getName() + ".xml";
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			CreateXml.init(register.getName());
			///////////////////生成收信人文件夹
			path = GetPath.getPath() + "receiverFolder/" + register.getName() + ".xml";
			file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			CreateReceiver.init(register.getName());
			/////////跳转
			response.sendRedirect("home.jsp");
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/logInFail.jsp");
			dispatcher.forward(request, response);
		}
	}
}
