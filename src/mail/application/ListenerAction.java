package mail.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Map.Entry;

import mail.global.GetPath;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/*
 * @Author StarSasumi
 */
public class ListenerAction extends TimerTask {
	private static boolean	isRunning	= false;
	private static boolean	isAlarmTime	= false;
	private static int		alarmHour;
	private static int		alarmMinute;

	public boolean setAlarmHour(int hour) {
		if (hour >= 0 && hour <= 23) {
			this.alarmHour = hour;
			return true;
		}
		else {
			this.alarmHour = hour % 24;
			return false;
		}
	}

	public int getAlarmHour() {
		return this.alarmHour;
	}

	public boolean setAlarmMinute(int minute) {
		if (minute >= 0 && minute <= 60) {
			this.alarmMinute = minute;
			return true;
		}
		else {
			this.alarmMinute = minute % 60;
			return false;
		}

	}

	public int getAlarmMinute() {
		return this.alarmMinute;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Date date = new Date();	//获取当前的时间
		if (date.getHours() == 11 && date.getMinutes() == 38)	//确定当前是否到了设定的时间
			isAlarmTime = true;
		else
			isAlarmTime = false;

		//假设运行是需要时间的
		if (!isRunning) {
			isRunning = true;
			if (isAlarmTime) {
				System.out.println("It's time to alarm!");
				System.out.println(date);
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
						pw.write(Html);
						//	System.out.println(Html);
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
			else {
				System.out.println("Just pass one minute...");
			}
			isRunning = false;
		}
		else {
			System.out.println("系统正忙，请稍后");
		}
	}

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


}