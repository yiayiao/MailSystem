package mail.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import mail.global.GetPath;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class AddXmlSource {

	public static byte[] readInputStream(InputStream inputStream) throws Exception
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		//inputStream.
		while ((len = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
		return outputStream.toByteArray();
	}

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("all", "http://www.guokr.com/rss/");
		map.put("beauty", "http://www.guokr.com/rss/site/beauty/");
		map.put("crime", "http://www.guokr.com/rss/site/crime/");
		map.put("sex", "http://www.guokr.com/rss/site/sex/");
		map.put("logos", "http://www.guokr.com/rss/site/logos/");
		map.put("health", "http://www.guokr.com/rss/site/health/");
		map.put("psybst", "http://www.guokr.com/rss/site/psybst/");
		map.put("diy", "http://www.guokr.com/rss/site/diy/");
		map.put("natural", "http://www.guokr.com/rss/site/natural/");
		map.put("fact", "http://www.guokr.com/rss/site/fact/");

		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			////////////////////////////抓起网页内容///////////
			HttpClient client = new HttpClient();
			String url = val;
			PostMethod post = new PostMethod(url);
			byte[] buff = null;
			String Html = "";
			try {
				client.executeMethod(post);
				buff = readInputStream(post.getResponseBodyAsStream());
				post.releaseConnection();
				Html = new String(buff, "utf-8");
				/////////写入文件中
				PrintWriter pw = new PrintWriter(GetPath.getPath() + "xmlSource/guokr.com/" + key + ".xml");
				//pw.write(Html);
				System.out.println(Html);
				pw.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
