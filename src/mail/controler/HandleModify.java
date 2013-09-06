package mail.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Register;
import mail.global.Connect;

/**
 * Servlet implementation class handleModify
 */
public class HandleModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		Register register = (Register) request.getSession().getAttribute("user");
		
		String oname = request.getParameter("ologname").trim();
		String name = request.getParameter("logname").trim();
		String opassword = request.getParameter("opassword").trim();
		String password = request.getParameter("npassword").trim();
		String email = request.getParameter("email").trim();
		String phone = request.getParameter("phone").trim();
		String gender = request.getParameter("gender").trim();
		String message = request.getParameter("message").trim();
		String backNews = "";
		if (name.length() == 0 || password.length() == 0 || opassword.length() == 0 || email.length() == 0 || phone.length() == 0) {
			PrintWriter out = response.getWriter();
			out.println("<p>信息填写不完整</p>");
			response.setHeader("Refresh", "1; URL=modify.jsp");
		} else if(!password.equals(request.getParameter("rpassword").trim())) {
			PrintWriter out = response.getWriter();
			out.println("<p>您两次输入的新密码不一致</p>");
			response.setHeader("Refresh", "1; URL=modify.jsp");
		} else {
			try {
				Connection con = Connect.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = null;
				String sql = "select * from user where name='" + oname
					+ "' and password = '" + opassword + "'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					sql = "UPDATE user SET name = '" + name + "', password = '" + password + "', gender = '" +gender + "', mail = '" + email + "', phone = '" + phone + "', message = '" + message + "' " 
						+ "WHERE name='" + oname + "' and password = '" + opassword + "'";
					int m = stmt.executeUpdate(sql);
					if(m != 0){
						register.setName(name);
						register.setGender(gender);
						register.setPhone(phone);
						register.setMail(email);
						register.setMessage(message);
						register.setPassWord(password);
						
						request.getSession().setAttribute("userName", name);
						
					} else {
						backNews = "用户信息修改不成功";
						register.setBackNews(backNews);
					}
				} else {
					PrintWriter out = response.getWriter();
					out.println("<p>您输入的原密码有误，请检查。</p>");
					response.setHeader("Refresh", "1; URL=modify.jsp");
				}
				rs.close();
				stmt.close();
				con.close();
				
			} catch (SQLException exp) {
				backNews = "您的新用户名已经被注册" + exp;
				register.setBackNews(backNews);
			} catch (ClassNotFoundException exp) {
				backNews = "数据库连接失败" + exp;
				exp.printStackTrace();
			}
			/*跳转*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("LeadtoUsercenter");
			dispatcher.forward(request, response);
		}
	}
}