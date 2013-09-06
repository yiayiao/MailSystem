package mail.controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.receiver.xml.AddReceiver;
import mail.receiver.xml.IsMailinReceiver;

/**
 * Servlet implementation class AddMailContacts
 */
public class AddMailContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public AddMailContacts() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String backInfo = "";
		String user = (String) request.getSession().getAttribute("userName");
		String mail = request.getParameter("mail").trim();
		if(mail.length() == 0 || IsMailinReceiver.isMailinReceiver(user,mail)){
			backInfo = "请输入您的联系人邮箱";
			System.out.println("dddddd");
			response.sendRedirect("SendHtmlContacts.jsp");
			return ;
		}
		
		AddReceiver.addmessage(user,mail);
		response.sendRedirect("SendHtmlContacts.jsp");
	}

}
