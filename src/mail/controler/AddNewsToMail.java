package mail.controler;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.global.GetPath;
import mail.news.xml.AddMessage;
import mail.news.xml.CreateXml;
/**
 * Servlet implementation class AddNewsToMail
 */
public class AddNewsToMail extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewsToMail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String user = (String) request.getSession().getAttribute("userName");
		String title = request.getParameter("title").trim();
		String link = request.getParameter("link").trim();
		String species = request.getParameter("species").trim();
		String from = request.getParameter("from").trim();
		/* 这个新闻已经添加成功 */

		
		System.out.println(user + " " + title + " " + link + " " + species  + " " + from);
		
		String path = GetPath.getPath() + "newsFolder/" + user + ".xml";
		File xmlFile = new File(path);
		if (!xmlFile.exists()) {
			CreateXml.init(path);
		}
		AddMessage.addmessage(user, title, link, species);

		request.setAttribute("newsChoose",species);
		
		if(from.equals("newsCenter")){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/" + from + ".jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect( from + ".jsp");
		}
		
	}

}
