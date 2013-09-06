package mail.receiver.xml;

import java.io.*;
import javax.xml.parsers.*;

import mail.global.GetPath;

import org.w3c.dom.*;

public class IsMailinReceiver {
	static Document			document;
	private static boolean	validating;
	/*****************联系人是否在联系人列表中***************************/
	public static boolean isMailinReceiver(String user, String mail) {

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
				return false;
			}

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
				//		System.out.println("you will delete the node" + mail);
						return true;
					}
				}
			}
			return false;
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		isMailinReceiver("mike", "mike");
	}
}