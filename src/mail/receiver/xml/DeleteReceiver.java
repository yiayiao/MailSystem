package mail.receiver.xml;

import java.io.*;
import javax.xml.parsers.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mail.global.GetPath;

import org.w3c.dom.*;

public class DeleteReceiver {
	static Document			document;
	private static boolean	validating;

	public static Node toRemove(String user, String mail) {

		String filename = GetPath.getPath() + "receiverFolder/" + user + ".xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.getDocumentElement().normalize();
			/* 名为contector的节点是只有一个 */
			Node node = document.getFirstChild();
			if (node == null) {
				return null;
			}

			Node findNode = null;
			String tmpMail = null;
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node secNode = list.item(i);
				if (secNode.getNodeName().equals("Receiver")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Mail")) {
							tmpMail = thirNode.getTextContent();
							System.out.println(tmpMail);
						}
					}
					if (tmpMail.equals(mail.trim())) {
						System.out.println("you will delete the node" + mail);
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
		toRemove("mike", "mike");
	}
}