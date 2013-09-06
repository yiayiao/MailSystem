package mail.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Register;
import mail.global.Connect;

/**
 * Servlet implementation class SetPassword
 */
public class SetPassword extends HttpServlet {
	private static final long	serialVersionUID	= 1L;


	public SetPassword() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String logName = (String) request.getSession().getAttribute("userName");///////
		Register register = (Register) request.getSession().getAttribute("user");/////
		if (logName == null) {
			response.sendRedirect("logIn.jsp");
		}
		String password = request.getParameter("mailPassword").trim();/////////////////
		///////////////////////////////////
		if (password.length() == 0) {
			response.sendRedirect("userCenter.jsp");
			return;
		}
		try {
			Connection con = Connect.getConnection();
			String insertCondition = "insert into mail_password (name,password) " + "values(?,?)";
			PreparedStatement sql = con.prepareStatement(insertCondition);
			sql.setString(1, logName);
			sql.setString(2, password);

			int m = sql.executeUpdate();
			if (m != 0) {
				register.setfMailPassWord(true);
				register.setMailPassword(password);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		response.sendRedirect("userCenter.jsp");
	}
}