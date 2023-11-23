package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnectionToDatabase() {
		Connection connection = null;

		try {
			String url = "jdbc:mysql://localhost:3306/tictactoe";
			String username = "root";
			String pw = "root";

			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(url, username, pw);

			System.out.println("Connected");

		} catch (SQLException e) {

			System.out.println("Unable to connect");
			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return connection;
	}
}