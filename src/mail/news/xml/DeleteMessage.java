package mail.news.xml;

import java.io.File;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeleteMessage {
	static Document	document;
	private static boolean	validating;

	public static Node toRemove(String user, String title) {
		
		String filename =  GetPath.getPath() + "newsFolder/"
			+ user + ".xml";
		try {
			Node findNode = null;
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
					if(Title.trim().equals(title.trim()) ){
						System.out.println("find it");
						findNode = node.removeChild(secNode);
					}
				}
			}
			//保存xml文件
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			//设置编码类型
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			StreamResult result = new StreamResult(new FileOutputStream(filename));
			//把dom树转换为xml文件
			transformer.transform(domSource, result);
			
			return findNode;
		}
		catch (Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String path = GetPath.getPath() + "newsFolder/"
			+ "mike" + ".xml";
		Node findNode = toRemove(path,"mike");
		System.out.println(findNode.getFirstChild().getTextContent());
	}
}
