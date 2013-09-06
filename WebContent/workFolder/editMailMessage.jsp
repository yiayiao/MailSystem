<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="mail.news.xml.*"%>
<%@ page import="com.rsslibj.elements.Channel"%>
<%@ page import="com.rsslibj.elements.Item"%>
<%@ page import="com.rsslibj.elements.RSSReader"%>
<%@ page import="mail.global.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="electric.xml.ParseException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="head.txt"%>
<style type="text/css">
.title h1 {
	margin: 25px 0 0 80px;
}
</style>
</head>
<body>
	<div class="editMailMessageParaMessage">
		<p>
			下面列举了您已经添加到邮件的新闻信息和您未添加的新闻信息，您可以管理您邮件中的新闻信息。下发的链接可链接到该新闻的新闻源。确认无误后，请点击
			<a href="SendHtml.jsp">发送邮件</a>
		</p>
	</div>
	<div id="editChoosedNews">
		<p class="user_title">您已选的新闻信息</p>
		<%
		Vector<Vector<String>> Ve;
		ReadMessage my = new ReadMessage();
		String user = (String)session.getAttribute("userName");
		Ve = my.toRead(user);
		for (int i = 0; i < Ve.elementAt(0).size(); i++) {
			String choose = Ve.elementAt(2).elementAt(i);
			String chooseCN = null;
			if(choose.equals("all")){
				chooseCN = "首页";
			}
			else if(choose.equals("beauty")){
				chooseCN = "美丽";
			}
			else if(choose.equals("crime")){
				chooseCN = "犯罪";
			}
			else if(choose.equals("diy")){
				chooseCN = "DIY";
			}
			else if(choose.equals("fact")){
				chooseCN = "事实";
			}
			else if(choose.equals("health")){
				chooseCN = "健康";
			}
			else if(choose.equals("logos")){
				chooseCN = "理性";
			}
			else if(choose.equals("nature")){
				chooseCN = "自然";
			}
			else if(choose.equals("psybst")){
				chooseCN = "心理";
			}
			else if(choose.equals("sex")){
				chooseCN = "性情";
			}
		%>
		<div class="editChoosedNewsFeng">
			<p class="ChoosedNewsTitile">
				标题：<%= Ve.elementAt(0).elementAt(i) %>
			</p>
			<p class="ChoosedNewsTitile">
				选自：<%= chooseCN %>
			</p>
			<p class="ChoosedNewsTitile">
				链接：<a href="<%= Ve.elementAt(1).elementAt(i) %>"><%= Ve.elementAt(1).elementAt(i) %></a>
			</p>
			<form action="RemoveNewsFromMail" method="post">
				<input type="hidden" name="title" value="<%= Ve.elementAt(0).elementAt(i) %>" />
				<input type="hidden" name="species" value="<%= choose %>" /> 
				<input type="submit" name="remove" value="移除" class="removeChoosedNews" />
			</form>
		</div>
		<%
		}
		%>

	</div>
	<div id="ediUnchoosedNews">
		<p class="user_title">您未选的新闻信息</p>
		<%
		Vector<String> ve = new Vector<String>();
		ve.add("beauty");
		ve.add("crime");
		ve.add("diy");
		ve.add("fact");
		ve.add("health");
		ve.add("logos");
		ve.add("nature");
		ve.add("psybst");
		ve.add("sex");
		for(String choose:ve){
			String chooseCN = null;
			if(choose.equals("all")){
				chooseCN = "首页";
			}
			else if(choose.equals("beauty")){
				chooseCN = "美丽";
			}
			else if(choose.equals("crime")){
				chooseCN = "犯罪";
			}
			else if(choose.equals("diy")){
				chooseCN = "DIY";
			}
			else if(choose.equals("fact")){
				chooseCN = "事实";
			}
			else if(choose.equals("health")){
				chooseCN = "健康";
			}
			else if(choose.equals("logos")){
				chooseCN = "理性";
			}
			else if(choose.equals("nature")){
				chooseCN = "自然";
			}
			else if(choose.equals("psybst")){
				chooseCN = "心理";
			}
			else if(choose.equals("sex")){
				chooseCN = "性情";
			}
		%>
		<p class="editChoosedNewsFengTitle"><%= chooseCN %></p>
		<div class="editChoosedNewsFeng">
			<%
		try {
			String path = GetPath.getPath();
			File rss = new File(path + "xmlSource/guokr.com/" + choose + ".xml");
			FileInputStream fin = new FileInputStream(rss);
			BufferedReader fbuf = new BufferedReader(new InputStreamReader(fin,"utf-8"));
			//改成读入rbuf中的数据！！从文件读入
			RSSReader reader = new RSSReader(fbuf);	//相当于获得了xml文件，内容与xml的rss标签的子标签相当
			Channel channel = reader.getChannel();	//内容与channel的子内容相当
			@SuppressWarnings("unchecked")
			List<Item> items = channel.getItems();	//内容与channel内的item相当，一篇报道就是一个item
			int id = 0;
			for(Item i:items) {
				boolean flag = false;
				//System.out.print(Ve.elementAt(0).size());
				for (int j = 0; j < Ve.elementAt(0).size(); j++) {
					String aa = Ve.elementAt(0).elementAt(j).trim();
					String bb = i.getTitle().trim();
					if(aa.equals(bb)){
						//System.out.println("aaaaaaaaaaaaaaaaaaaaa");
						flag = true;
					}
				}
				id ++;
				if(flag) continue;
				String projectId = choose + id;
				out.println("<div class=\"editNewsNotChoosed\">");
				out.println("<p class=\"editNewsNotChoosedPara\">标题：" + i.getTitle() + "</p>");
				out.println("<a href=\" " + i.getLink() + " \">原文链接： " + i.getLink() +" </a>");
		%>
			<form action="AddNewsToMail" method="post">
				<input type="hidden" name="title" value="<%= i.getTitle().trim()%>" />
				<input type="hidden" name="species" value="<%= choose%>" /> 
				<input type="hidden" name="link" value="<%= i.getLink().trim() %>" /> 
				<input type="hidden" name="projectId" value="<%= projectId %>" /> 
				<input type="hidden" name="from" value="editMailMessage" /> 
				<input type="submit" value="添加" class="removeChoosedNews" />
			</form>
			<%
				out.println("</div>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		%>
		</div>
		<%
		}
		%>



	</div>
</body>
</html>