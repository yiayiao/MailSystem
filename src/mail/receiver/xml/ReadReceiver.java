package mail.receiver.xml;

import java.io.*;
import java.util.Vector;
import javax.xml.parsers.*;

import mail.global.GetPath;

import org.w3c.dom.*;

public class ReadReceiver {
	static Document			document;
	private static boolean	validating;

	public static Vector<String> toRead(String user) {
		String filename = GetPath.getPath() + "receiverFolder/" + user + ".xml";
		Vector<String> mail = new Vector<String>();
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
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node secNode = list.item(i);
				if (secNode.getNodeName().equals("Receiver")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Mail")) {
							mail.addElement(thirNode.getTextContent());
						}
					}
				}
			}
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
		return mail;
	}

	public static void main(String[] args) {

		Vector<String> my = toRead("mike");
		for(int i=0;i<my.size();i++){
			System.out.println(my.elementAt(i));
		}
	}

}