package mail.news.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mail.global.GetPath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXml {
	private Document	document;
	private String		filename;

	public CreateXml(String name) throws ParserConfigurationException {
		filename = name;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
	}

	public void toWrite() {
		Element root = document.createElement("NewsChoosed");
		document.appendChild(root);
		/*
		 * Element pairNews = document.createElement("PairNews");
		 * root.appendChild(pairNews);
		 * Element title = document.createElement("Title");
		 * Element link = document.createElement("Link");
		 * Element species = document.createElement("Species");
		 * pairNews.appendChild(title);
		 * pairNews.appendChild(link);
		 * pairNews.appendChild(species);
		 */
	}

	public void toSave() {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void init(String name) {
		try {
			String path = GetPath.getPath();
			CreateXml newxml = new CreateXml(path + "newsFolder/" + name + ".xml");
			newxml.toWrite();
			newxml.toSave();
		}
		catch (ParserConfigurationException exp) {
			exp.printStackTrace();
		}
	}

	/* main方法作为测试 */
	public static void main(String[] args) {
		init("mike");
	}

}
