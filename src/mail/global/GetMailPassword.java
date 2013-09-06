package mail.global;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetMailPassword {

	static public String getMailPostor(String logName) {
		String ret = null;
		try {
			Connection con;
			con = Connect.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			String sql = "select * from user where name='" + logName + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ret = rs.getString("mail");
			}
			rs.close();
			stmt.close();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

	static public String getMailPassword(String logName) {
		String ret = null;
		try {
			Connection con;
			con = Connect.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			String sql1 = "select * from mail_password where name='" + logName + "'";
			rs = stmt.executeQuery(sql1);
			if (rs.next()) {
				ret = rs.getString("password");
			}
			rs.close();
			stmt.close();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
