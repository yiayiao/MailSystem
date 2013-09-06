package mail.contacts.xml;

import mail.global.GetPath;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class Writexml {
	private Document	document;
	private String		filename;

	public Writexml(String name) throws ParserConfigurationException {
		filename = name;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
	}

	public void toWrite() {
		Element root = document.createElement("Contactor");
		document.appendChild(root);
//		Element person = document.createElement("Person");
//		root.appendChild(person);
//		Element title = document.createElement("Name");
//		title.appendChild(document.createTextNode(name));
//		person.appendChild(title);
//		Element content = document.createElement("Mail");
//		content.appendChild(document.createTextNode(mail));
//		person.appendChild(content);
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
		catch (TransformerException mye) {
			mye.printStackTrace();
		}
		catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	public static void init(String name) {
		try {
			String path = GetPath.getPath();
			Writexml newxml = new Writexml(path + "contactsFolder/" + name + ".xml");
			newxml.toWrite();
			newxml.toSave();
		}
		catch (ParserConfigurationException exp) {
			exp.printStackTrace();
			System.out.print("Your writing is failed.");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		init("mike");
	}

}
