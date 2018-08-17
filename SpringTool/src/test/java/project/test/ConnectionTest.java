package project.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

	public static void main(String[] args) throws SQLException {

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver active");

			String url = "jdbc:mysql://localhost:3306/info?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

			conn = DriverManager.getConnection(url, "user", "123456");
			System.out.println("connection url success");

			Statement st = conn.createStatement();

			st.executeQuery("select * from practice");
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
