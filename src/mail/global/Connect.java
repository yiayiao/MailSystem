package mail.global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public static Connection getConnection() throws SQLException, java.lang.ClassNotFoundException
	{
		/* 连接到ProductMangement */
		String url = "jdbc:mysql://localhost:3306/mailSystem";
		Class.forName("com.mysql.jdbc.Driver");
		String userName = "root";
		String password = "mike";
		Connection con = DriverManager.getConnection(url, userName, password);
		return con;
	}
}
