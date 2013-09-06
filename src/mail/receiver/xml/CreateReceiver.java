package mail.receiver.xml;

import mail.global.GetPath;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class CreateReceiver {
	private Document	document;
	private String		filename;

	public CreateReceiver(String name) throws ParserConfigurationException {
		filename = name;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
	}

	public void toWrite() {
		Element root = document.createElement("Contactor");
		document.appendChild(root);
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
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			
			CreateReceiver newxml = new CreateReceiver(path + "receiverFolder/" + name + ".xml");
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
