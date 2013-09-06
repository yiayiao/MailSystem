package mail.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.global.Connect;

public class DelectLove extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public DelectLove() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String backNews = null;
		String user = (String) request.getSession().getAttribute("userName");
		String []temp = request.getParameterValues("Choosed");
		
		if(temp == null){
			backNews = "您没有任何选择";
			response.sendRedirect("userCenter.jsp");
			return;
		}
		
		try{
			Connection con = Connect.getConnection();
			Statement stmt = con.createStatement();
			for(int i=0;i<temp.length;i++){
				String sql = "UPDATE loveset SET `" + temp[i] + "`='unChoose' WHERE `name`='" + user + "' ";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			con.close();
		}
		catch(SQLException e ){	
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("userCenter.jsp");
		
	}
}
