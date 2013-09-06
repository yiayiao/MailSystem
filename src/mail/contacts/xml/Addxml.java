package mail.contacts.xml;

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


public class Addxml {
	public static void addmessage(String user, String name, String mail) {
		
		System.out.println(user + " " + name + " " +mail);
		
		String xml = GetPath.getPath() + "contactsFolder/" + user + ".xml";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			//创建节点
			Element person = doc.createElement("Person");
			Element nameElement = doc.createElement("Name");
			nameElement.setTextContent(name);
			Element mailElement = doc.createElement("Mail");
			mailElement.setTextContent(mail);
			//添加父子关系
			Element contactorElement = (Element) doc.getElementsByTagName("Contactor").item(0);
			contactorElement.appendChild(person);
			person.appendChild(nameElement);
			person.appendChild(mailElement);
			//保存xml文件
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			//设置编码类型
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult result = new StreamResult(new FileOutputStream(xml));
			//把dom树转换为xml文件
			transformer.transform(domSource, result);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		addmessage("ydy", "mike", "mike");
	}
}
