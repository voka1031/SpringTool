package project.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

	private static final String URL = "jdbc:mysql://localhost:3306/info?autoReconnect=true"
			+ "&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static void main(String[] args) throws SQLException {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver active");

			conn = DriverManager.getConnection(URL, "user", "123456");
			System.out.println("connection url success");

			Statement st = conn.createStatement();

			st.executeQuery("select * from StockDailyData");
			ResultSet rs = st.getResultSet();

			while (rs.next()) {
				System.out.println(rs.getString("securityCode"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
