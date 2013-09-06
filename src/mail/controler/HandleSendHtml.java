package mail.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.news.xml.ReadMessage;
import mail.receiver.xml.ReadReceiver;

public class HandleSendHtml extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public HandleSendHtml() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String user = (String) request.getSession().getAttribute("userName");
		String mailfrom = request.getParameter("mailfrom");
		//发件人从发件人列表中选择///String mailto = request.getParameter("mailto");
		String subject = request.getParameter("subject");
		String context = request.getParameter("context");
		//////////////////////////将信息信息添加到有邮件中////////////////////////////////////////
		StringBuffer temp = new StringBuffer(context);
		Vector<Vector<String>> Ve;
		ReadMessage my = new ReadMessage();
		Ve = my.toRead(user);
		for (int i = 0; i < Ve.elementAt(0).size(); i++) {
			String choose = Ve.elementAt(2).elementAt(i);
			String chooseCN = null;
			if (choose.equals("all")) {
				chooseCN = "首页";
			}
			else if (choose.equals("beauty")) {
				chooseCN = "美丽";
			}
			else if (choose.equals("crime")) {
				chooseCN = "犯罪";
			}
			else if (choose.equals("diy")) {
				chooseCN = "DIY";
			}
			else if (choose.equals("fact")) {
				chooseCN = "事实";
			}
			else if (choose.equals("health")) {
				chooseCN = "健康";
			}
			else if (choose.equals("logos")) {
				chooseCN = "理性";
			}
			else if (choose.equals("nature")) {
				chooseCN = "自然";
			}
			else if (choose.equals("psybst")) {
				chooseCN = "心理";
			}
			else if (choose.equals("sex")) {
				chooseCN = "性情";
			}
			temp.append("<div class=\"editChoosedNewsFeng\"><p class=\"ChoosedNewsTitile\">标题："
				+ Ve.elementAt(0).elementAt(i) +
				"</p><p class=\"ChoosedNewsTitile\">选自：" + chooseCN
				+ "</p><p class=\"ChoosedNewsTitile\">链接：<a href=\" "
				+ Ve.elementAt(1).elementAt(i) + " \"> " + Ve.elementAt(1).elementAt(i) + "</a></p></div>");
		}
		context = new String(temp);
		/////////////////////////////////添加完成///////////////////////////////////////
		
		String mailpassword = request.getParameter("password");
		String mailserver = "smtp." + mailfrom.substring(mailfrom.indexOf('@') + 1, mailfrom.length());
		PrintWriter out = response.getWriter();


		try {
			Properties prop = System.getProperties();
			prop.put("mail.stmp.host", mailserver);
			prop.put("mail.stmp.auth", "true");
			Session session = Session.getInstance(prop);
			session.setDebug(true);
			//新建一个消息对象
			MimeMessage message = new MimeMessage(session);
			//设置发件人
			message.setFrom(new InternetAddress(mailfrom));
			//设置收件人
			Vector<String> receiver = ReadReceiver.toRead(user);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver.elementAt(0)));
			for (int i = 1; i < receiver.size(); i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver.elementAt(i)));
			}
			//设置主题
			message.setSubject(subject);
			//设置发件内容 ，创建MimeMultipart存放BodyPart对象
			Multipart mul = new MimeMultipart();
			BodyPart bpart = new MimeBodyPart();
			bpart.setContent(context, "text/html;charset=utf-8");
			mul.addBodyPart(bpart);
			message.setContent(mul);
			//设置发送时间
			message.setSentDate(new Date());
			//发送邮件
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(mailserver, mailfrom, mailpassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			out.print("<script language='javascript'>alert('邮件已经发送');</script>");
		}
		catch (Exception e)
		{
			out.print("<script language='javascript'>alert('邮件发送失败');</script>");
			e.printStackTrace();
		}
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
