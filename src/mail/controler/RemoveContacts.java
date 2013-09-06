package mail.controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.receiver.xml.AddReceiver;
import mail.receiver.xml.DeleteReceiver;

public class RemoveContacts extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public RemoveContacts() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		String backInfo = "";
		String mail = request.getParameter("mail").trim();
		if(mail.length() == 0){
			backInfo = "请输入您的联系人邮箱";
			response.sendRedirect("SendHtmlContacts.jsp");
			return ;
		}
		String user = (String) request.getSession().getAttribute("userName");
		DeleteReceiver.toRemove(user,mail);
		response.sendRedirect("SendHtmlContacts.jsp");
	}

}
