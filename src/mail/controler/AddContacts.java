package mail.controler;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.contacts.xml.*;
import mail.receiver.xml.IsMailinReceiver;

public class AddContacts extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public AddContacts() {
		super();
	}

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
		
		System.out.println("begin");
		
		String logName = (String) request.getSession().getAttribute("userName");
		try {
			String name = request.getParameter("name").trim();
			String mail = request.getParameter("mail").trim();
			
			System.out.println(name);
			System.out.println(mail);
			
			if (name.length() == 0 || mail.length() == 0 ) {
				System.out.println("add recerver fail");
				response.sendRedirect("userCenter.jsp");
				return;
			}
			else {
				Addxml.addmessage(logName, name, mail);
			}
		}
		catch (NullPointerException e) {
			response.sendRedirect("userCenter.jsp");
			return;
		}
		
		/*跳转*/
		RequestDispatcher dispatcher = request.getRequestDispatcher("userCenter.jsp");
		dispatcher.forward(request, response);
		
	//	response.sendRedirect("userCenter.jsp");
		return ;

	}

}
