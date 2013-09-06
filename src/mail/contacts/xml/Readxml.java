package mail.contacts.xml;

import java.io.*;
import java.util.Vector;
import javax.xml.parsers.*;

import mail.global.GetPath;

import org.w3c.dom.*;

public class Readxml {
	static Document			document;
	private static boolean	validating;

	public static Vector<Vector<String>> toRead(String user) {
		String filename = GetPath.getPath() + "contactsFolder/" + user + ".xml";
		Vector<String> name = new Vector<String>();
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
				if (secNode.getNodeName().equals("Person")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Name")) {
							name.addElement(thirNode.getTextContent());
						}
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
		Vector<Vector<String>> all = new Vector<Vector<String>>();
		all.add(name);
		all.add(mail);
		return all;
	}

	public static void main(String[] args) {
		Vector<Vector<String>> A;
		String path = GetPath.getPath() + "contactsFolder/"
			+ "mike" + ".xml";
		A = toRead(path);
		for (int i = 0; i < A.elementAt(0).size(); i++) {
			System.out.println(A.elementAt(0).elementAt(i));
			System.out.println(A.elementAt(1).elementAt(i));
		}
	}

}