package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {

	public static void setupDatabase() {

		createUserTable();
		createScoreTable();
	}

	private static void createUserTable() {
		String createTable = "CREATE TABLE IF NOT EXISTS user( " + " student_number CHAR(15) NOT NULL PRIMARY KEY,"
				+ " first_name VARCHAR(50) NOT NULL," + " last_name VARCHAR(50) NOT NULL,"
				+ " pass_word VARCHAR(20) NOT NULL," + " teacher VARCHAR(15) NOT NULL);";
		try (Connection connection = DBConnection.getConnectionToDatabase();
				PreparedStatement preparedStatement = connection.prepareStatement(createTable);) {
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createScoreTable() {
		String createTable = "CREATE TABLE IF NOT EXISTS score( " + " student_number CHAR(15) NOT NULL PRIMARY KEY,"
				+ " score INT(2) NOT NULL, teacher VARCHAR(15) NOT NULL);";
		try (Connection connection = DBConnection.getConnectionToDatabase();
				PreparedStatement preparedStatement = connection.prepareStatement(createTable);) {
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
