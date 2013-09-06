<%@page import="mail.news.xml.IsNewsinMail"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.File"%>
<%@ page import="com.rsslibj.elements.Channel"%>
<%@ page import="com.rsslibj.elements.Item"%>
<%@ page import="com.rsslibj.elements.RSSReader"%>
<%@ page import="electric.xml.ParseException"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.MalformedURLException"%>
<%@ page import="mail.global.*"%>
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
	<%
		String choose = (String) request.getAttribute("newsChoose");
		String choose_CN;
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("all","全部");
		map.put("beauty","美丽");
		map.put("crime","犯罪");
		map.put("diy","DIY");
		map.put("fact","事实");
		map.put("health","健康");
		map.put("logos","理性");
		map.put("nature","自然");
		map.put("psybst","心理");
		map.put("sex","性情");
		
		String path = GetPath.getPath() + "xmlSource/guokr.com/";
		if(choose == null){
			choose = "all";
			choose_CN = "全部";
			path += "all.xml";
		}
		else{
			path += choose + ".xml";
			choose_CN = map.get(choose);
		}
		/*****判断用户是否登录*****/
		if(session.getAttribute("userName") == null){
	%>
	<div id="unlog">
		<p class="tis">因为您未登陆系统，您只能浏览本页的内容，而无法将它直接添加到您的邮件中</p>
	</div>
	<%}%>


	<div id="newsChoose">
		<p id="choose_CN"><%= choose_CN%></p>
		<form action="HandleNewsChoose" method="post" id="chooseButton">
			<input type="submit" value="全部" name="choose" /> 
			<input type="submit" value="美丽" name="choose" /> 
			<input type="submit" value="犯罪" name="choose" /> 
			<input type="submit" value="事实" name="choose" /> 
			<input type="submit" value="健康" name="choose" /> 
			<input type="submit" value="理性" name="choose" /> 
			<input type="submit" value="自然" name="choose" /> 
			<input type="submit" value="心理" name="choose" /> 
			<input type="submit" value="性情" name="choose" /> 
			<input type="submit" value="DIY" name="choose" />
		</form>
		<%if(session.getAttribute("userName")!=null){ %>
		<div class="mailLink">
			<a href="editMailMessage.jsp">编辑您邮件中的新闻内容</a>
		</div>
		<%} %>
	</div>


	<div id="shownews">
		<%
		try {
		//	URL url = new URL("http://www.ifanr.com/feed");
		//	HttpURLConnection conn = (HttpURLConnection) url.openConnection();		
		//	BufferedReader buf = new BufferedReader(new InputStreamReader(in,"utf-8"));
		
			InputStreamReader input= new InputStreamReader(new FileInputStream(path),"utf-8");
			BufferedReader buf = new BufferedReader(input);
			RSSReader reader = new RSSReader(buf);	//相当于获得了xml文件，内容与xml的rss标签的子标签相当
			Channel channel = reader.getChannel();	//内容与channel的子内容相当
			@SuppressWarnings("unchecked")
			List<Item> items = channel.getItems();	//内容与channel内的item相当，一篇报道就是一个item
			for(Item i:items){
				
				out.println("<div class=\"pairNews\">");
				out.println("<p class=\"newsTitle\">");
				out.println(i.getTitle());
				out.println("</p>");
				out.println("<a href=\"" + i.getLink() +"\" >");
				out.println("原文链接：" + i.getLink());
				out.println("</a>");
				/* Description是报道的正文
				 * 正文的内容经过了URL Encode，但有不是完全的Encode。
				 * 所以需要处理一下里面的「%」与「+」再Decode
				 */
				out.println("<div class=\"newsPra\">");
				String data = i.getDescription();	//Description是报道的正文
				StringBuffer tempBuffer = new StringBuffer();
			    int incrementor = 0;
			    int dataLength = data.length();
			    while (incrementor < dataLength) {
			    	char charecterAt = data.charAt(incrementor);
			        if (charecterAt == '%') {
			           tempBuffer.append("<percentage>");	//Replace '%' with "<percentage>"
			        } else if (charecterAt == '+') {
			           tempBuffer.append("<plus>");			//Replace '+' with "<plus>"
			        } else {
			           tempBuffer.append(charecterAt);
			        }
			        incrementor++;
			     }
			    data = tempBuffer.toString();
				out.println(URLDecoder.decode(data,"utf-8"));
				out.println("</div>");
				
				if(session.getAttribute("userName")!=null){
					String user = (String)session.getAttribute("userName");
					out.println("<div class=\"addNewsToMail\">");
					out.println("<p>");
					out.println("将本条新闻添加入您的邮件：");
					if(IsNewsinMail.isNewsinMail(user, i.getTitle())){
						out.println("您已经添加被信息");
					}
					else{
						%>
						<form action="AddNewsToMail" method="post">
							<input type="hidden" name="title" value="<%=i.getTitle() %>"/>
							<input type="hidden" name="link" value="<%=i.getLink() %>" />
							<input type="hidden" name="species" value="<%=choose %>" />
							<input type="hidden" name="from" value="newsCenter" />
							<input type="submit" value="添加" />
						</form>
						<%
					}
					out.println("</p>");
					out.println("</div>");
				}
				
				out.println("</div>");				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	%>
	</div>
</body>
</html>