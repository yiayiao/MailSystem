package mail.controler;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.news.xml.DeleteMessage;

public class RemoveNewsFromMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveNewsFromMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String title = request.getParameter("title");
		String user = (String)request.getSession().getAttribute("userName");
		DeleteMessage.toRemove(user,title);
		
		response.sendRedirect("editMailMessage.jsp");
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/editMailMessage.jsp");
//		dispatcher.forward(request, response);
	}

}
