package mail.news.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mail.global.GetPath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IsNewsinMail {

	static Document			document;
	private static boolean	validating;

	public static boolean isNewsinMail(String user, String title) {
		String filename = GetPath.getPath() + "newsFolder/"
			+ user + ".xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.getDocumentElement().normalize();
			/* 名为contector的节点是只有一个的 */
			Node node = document.getFirstChild();
			if (node == null) {
				return false;
			}
			String Title = null;
			NodeList list = node.getChildNodes();/* 第一个节点的子节点 */
			for (int i = 0; i < list.getLength(); i++) {
				Node secNode = list.item(i);
				if (secNode.getNodeName().equals("PairNews")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Title")) {
							Title = thirNode.getTextContent();
						}
					}
					if (Title.trim().equals(title.trim())) {
						System.out.println("find it");
						return true;
					}
				}
			}
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}
		return false;
	}
}
