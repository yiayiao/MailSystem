package mail.controler;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mail.bean.*;
import mail.global.*;
import mail.contacts.xml.Writexml;

public class HandleRegister extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		Register register = new Register();
		String name = request.getParameter("logname").trim();
		String passWord = request.getParameter("password").trim();
		String email = request.getParameter("email").trim();
		String phone = request.getParameter("phone").trim();
		String gender = request.getParameter("gender").trim();
		String message = request.getParameter("message").trim();
		String backNews = "";

		if (name.length() == 0 || passWord.length() == 0 || email.length() == 0 || phone.length() == 0) {
			backNews = "信息填写不完整";
		}
		else if (!passWord.equals(request.getParameter("rpassword").trim())) {
			backNews = "您两次输入的信息不一致";
		}
		else {
			try {
				Connection con = Connect.getConnection();
				String insertCondition = "insert into user (name,password,gender,phone,mail,message) " +
					"values(?,?,?,?,?,?)";
				PreparedStatement sql = con.prepareStatement(insertCondition);
				sql.setString(1, name);
				sql.setString(2, passWord);
				sql.setString(3, gender);
				sql.setString(4, phone);
				sql.setString(5, email);
				sql.setString(6, message);

				int m = sql.executeUpdate();
				if (m != 0) {
					backNews = "regist successful";		//用户注册成功
					register.setBackNews(backNews);
					register.setName(name);
					register.setGender(gender);
					register.setPhone(phone);
					register.setMail(email);
					register.setMessage(message);
					register.setPassWord(passWord);
				}
				else {
					backNews = "数据库操作错误";
					register.setBackNews(backNews);
				}
				con.close();
			}
			catch (SQLException exp) {
				backNews = "您的用户名已经被注册" ;//+ exp;
				exp.printStackTrace();
			}
			catch (ClassNotFoundException exp) {
				backNews = "数据库连接失败" ;//+ exp;
				exp.printStackTrace();
			}

			/////////////////////******************************/////////////////////////
			if (backNews.equals("regist successful")) {
				///////////////////////在数据库中写入偏好信息
				try {
					Connection con = Connect.getConnection();
					String insertCondition = "insert into loveset (name,beauty,crime,diy,fact,health,logos,natur,psybst,sex) " +
						"values(?,'unchoose','unchoose','unchoose','unchoose','unchoose','unchoose','unchoose','unchoose','unchoose')";
					PreparedStatement sql = con.prepareStatement(insertCondition);
					sql.setString(1, name);
					int m = sql.executeUpdate();
					if (m != 0) {
						backNews = "regist successful";		//用户注册成功
					}
				}
				catch (SQLException exp) {
					backNews = "您的用户名已经被注册" ;//+ exp;
					exp.printStackTrace();
				}
				catch (ClassNotFoundException exp) {
					backNews = "数据库连接失败" ;//+ exp;
					exp.printStackTrace();
				}
				///////////////////////生成联系人xml
				String path = GetPath.getPath() + "contactsFolder/" + register.getName() + ".xml";
				File file = new File(path);
				if (!file.exists()) {
					file.createNewFile();
				}
				Writexml.init(register.getName());
				/////////////////////////////将生产的信息放入request 和session中
				request.setAttribute("user", register);//写入JavaBean，之后用jsp:getProperty来读取
				request.getSession().setAttribute("user", register);
				request.getSession().setAttribute("userName", register.getName());
				/* 注册成功，生成联系人表 */
				Writexml.init(name);
				///////////////////////////分发，是可以传递本jsp域的request
				RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/showRegister.jsp");
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("ErrorLogName",backNews);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/registFail.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
