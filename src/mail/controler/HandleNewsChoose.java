package mail.controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HandleNews
 */
public class HandleNewsChoose extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HandleNewsChoose() {
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
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String newsChoose = request.getParameter("choose");
		
		if(newsChoose == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/newsCenter.jsp");
			dispatcher.forward(request, response);
			return ;
		}
		
		if(newsChoose.equals("首页")){
			request.setAttribute("newsChoose","all");
		}
		else if(newsChoose.equals("美丽")){
			request.setAttribute("newsChoose","beauty");
		}
		else if(newsChoose.equals("犯罪")){
			request.setAttribute("newsChoose","crime");
		}
		else if(newsChoose.equals("健康")){
			request.setAttribute("newsChoose","health");
		}
		else if(newsChoose.equals("理性")){
			request.setAttribute("newsChoose","logos");
		}
		else if(newsChoose.equals("自然")){
			request.setAttribute("newsChoose","nature");
		}
		else if(newsChoose.equals("心理")){
			request.setAttribute("newsChoose","psybst");
		}
		else if(newsChoose.equals("性情")){
			request.setAttribute("newsChoose","sex");
		}
		else if(newsChoose.equals("DIY")){
			request.setAttribute("newsChoose","diy");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/workFolder/newsCenter.jsp");
		dispatcher.forward(request, response);
	}
}
