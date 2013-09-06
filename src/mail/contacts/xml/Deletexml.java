package mail.contacts.xml;

import java.io.*;
import javax.xml.parsers.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mail.global.GetPath;

import org.w3c.dom.*;

public class Deletexml {
	static Document			document;
	private static boolean	validating;

	public static Node toRemove(String user, String name) {

		String filename = GetPath.getPath() + "contactsFolder/" + user + ".xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.getDocumentElement().normalize();
			/* 名为contector的节点是只有�?���?*/
			Node node = document.getFirstChild();
			if (node == null) {
				return null;
			}

			Node findNode = null;
			String tmpName = null;
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node secNode = list.item(i);
				if (secNode.getNodeName().equals("Person")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Name")) {
							tmpName = thirNode.getTextContent();
						}
					}
					if (tmpName.equals(name)) {
						System.out.println("you will delete the node" + name);
						findNode = node.removeChild(secNode);
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);

			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult result = new StreamResult(new FileOutputStream(filename));
			transformer.transform(domSource, result);

			return findNode;
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		
		String path = GetPath.getPath() + "contactsFolder/"
			+ "mike" + ".xml";
		Node findNode = toRemove(path,"mike");
		System.out.println(findNode.getFirstChild().getTextContent());
		
	}

}