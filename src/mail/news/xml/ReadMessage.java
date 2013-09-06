package mail.news.xml;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mail.global.GetPath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadMessage {
	static Document	document;
	private boolean	validating;

	public  Vector<Vector<String>> toRead(String user) {
		
		String filename =  GetPath.getPath() + "newsFolder/"
			+ user + ".xml";
		Vector<String> title = new Vector<String>();
		Vector<String> link = new Vector<String>();
		Vector<String> species = new Vector<String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.getDocumentElement().normalize();
			/* 名为contector的节点是只有一个的 */
			Node node = document.getFirstChild();
			if (node == null) {
				return null;
			}
			NodeList list = node.getChildNodes();/* 第一个节点的子节点 */
			//System.out.println(list.getLength());
			for (int i = 0; i < list.getLength(); i++) {
				Node secNode = list.item(i);
				if (secNode.getNodeName().equals("PairNews")) {
					NodeList secNodelist = secNode.getChildNodes();
					for (int j = 0; j < secNodelist.getLength(); j++) {
						Node thirNode = secNodelist.item(j);
						if (thirNode.getNodeName().equals("Title")) {
							//System.out.println(thirNode.getFirstChild().getNodeValue());
							title.addElement(thirNode.getTextContent());
						}
						else if (thirNode.getNodeName().equals("Link")) {
							//System.out.println(thirNode.getFirstChild().getNodeValue());
							link.addElement(thirNode.getTextContent());
						}
						else if (thirNode.getNodeName().equals("Species")) {
							species.addElement(thirNode.getTextContent());
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
		all.add(title);
		all.add(link);
		all.add(species);
		return all;
	}

	public static void main(String[] args) {
		Vector<Vector<String>> A;
		ReadMessage my = new ReadMessage();
		String path = GetPath.getPath() + "newsFolder/"
			+ "mike" + ".xml";
		A = my.toRead(path);
		for (int i = 0; i < A.elementAt(0).size(); i++) {
			System.out.println(A.elementAt(0).elementAt(i));
			System.out.println(A.elementAt(1).elementAt(i));
			System.out.println(A.elementAt(2).elementAt(i));
		}
	}
}
