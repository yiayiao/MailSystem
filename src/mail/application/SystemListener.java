package mail.application;

import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemListener implements ServletContextListener {
	private java.util.Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Date taskrun = new Date();
		System.out.println("taskrun:" + taskrun);
		timer = new Timer(true);
		event.getServletContext().log("定时器已启动");
		timer.schedule(new ListenerAction(), 0, 60 * 1000); // 每分钟执行一次ListenerAction中的任务
		event.getServletContext().log("已经添加任务调度表");
	}
}