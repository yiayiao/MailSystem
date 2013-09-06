package mail.news.xml;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mail.global.GetPath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class AddMessage {
	public static void addmessage(String user, String title, String link, String species) {
		
		String path = GetPath.getPath() + "newsFolder/"
			+ user + ".xml";
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(path);
			//创建节点,同时添加父子关系
			
			Element root = (Element) document.getElementsByTagName("NewsChoosed").item(0);
			
			Element pairNews = document.createElement("PairNews");
			root.appendChild(pairNews);
			Element Title = document.createElement("Title");
			Title.setTextContent(title);
			Element Link = document.createElement("Link");
			Link.setTextContent(link);
			Element Species = document.createElement("Species");
			Species.setTextContent(species);
			pairNews.appendChild(Title);
			pairNews.appendChild(Link);
			pairNews.appendChild(Species);
			//保存xml文件
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			//设置编码类型
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult result = new StreamResult(new FileOutputStream(path));
			//把dom树转换为xml文件
			transformer.transform(domSource, result);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

	public static void main(String args[]) {
		String path = GetPath.getPath() + "newsFolder/"
			+ "mike" + ".xml";
		addmessage(path, "mike", "mike","mike");

	}
}
