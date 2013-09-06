package mail.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import mail.global.Connect;

public class GetLoveSet {
	public static Vector<Vector<String>> getLoveSet(String user) {
		Vector<String> choosed = new Vector<String>();
		Vector<String> unChoosed = new Vector<String>();

		try {
			Connection con;
			con = Connect.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			String sql = "select * from loveset where name='" + user + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String tmp = rs.getString("beauty");
				if (!tmp.equals("choosed")) {
					unChoosed.add("beauty");
				}
				else {
					choosed.add("beauty");
				}
				
				tmp = rs.getString("crime");
				if (!tmp.equals("choosed")) {
					unChoosed.add("crime");
				}
				else {
					choosed.add("crime");
				}
				
				tmp = rs.getString("diy");
				if (!tmp.equals("choosed")) {
					unChoosed.add("diy");
				}
				else {
					choosed.add("diy");
				}
				
				tmp = rs.getString("fact");
				if (!tmp.equals("choosed")) {
					unChoosed.add("fact");
				}
				else {
					choosed.add("fact");
				}
				
				tmp = rs.getString("health");
				if (!tmp.equals("choosed")) {
					unChoosed.add("health");
				}
				else {
					choosed.add("health");
				}
				
				tmp = rs.getString("logos");
				if (!tmp.equals("choosed")) {
					unChoosed.add("logos");
				}
				else {
					choosed.add("logos");
				}
				
				tmp = rs.getString("natur");
				if (!tmp.equals("choosed")) {
					unChoosed.add("natural");
				}
				else {
					choosed.add("natural");
				}
				
				tmp = rs.getString("psybst");
				if (!tmp.equals("choosed")) {
					unChoosed.add("psybst");
				}
				else {
					choosed.add("psybst");
				}
				
				tmp = rs.getString("sex");
				if (!tmp.equals("choosed")) {
					unChoosed.add("sex");
				}
				else {
					choosed.add("sex");
				}
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

		Vector<Vector<String>> ve = new Vector<Vector<String>>();
		ve.add(choosed);
		ve.add(unChoosed);
		return ve;
	}

}
