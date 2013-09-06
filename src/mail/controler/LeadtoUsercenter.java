package mail.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Register;
import mail.global.Connect;


public class LeadtoUsercenter extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public LeadtoUsercenter() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String logName = (String) request.getSession().getAttribute("userName"); 
		if(logName == null) {
			response.sendRedirect("logIn.jsp");
		}
		
		Register register = new Register();
		request.getSession().setAttribute("user", register);	//mod by starsasumi
		try{
			/////////////////////////////////////////////////////
			Connection con = Connect.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			String sql = "select * from user where name='" + logName +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				register.setName(rs.getString("name"));
				register.setMail(rs.getString("mail"));
				register.setGender(rs.getString("gender"));
				register.setPhone(rs.getString("phone"));
				register.setMessage(rs.getString("message"));	//mod by starsasumi
			}
			/////////////////////////////////////////////////////
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
		
		response.sendRedirect("userCenter.jsp");
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/userCenter.jsp");
//		dispatcher.forward(request, response);
		return ;
	}
}
