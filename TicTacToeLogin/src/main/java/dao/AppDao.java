package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;

public class AppDao {

	public int registerUser(User user) {

		int rowsAffected = 0;

		try {

			Connection connection = DBConnection.getConnectionToDatabase();

			String insertQuery = "insert into user values(?, ?, ?, ?, ?)";
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);

			statement.setInt(1, user.getStudentNumber());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getTeacherName());

			rowsAffected = statement.executeUpdate();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	public int authenticate(int studentNumber, String password) {

		int rowsFound = 0;

		try {

			Connection connection = DBConnection.getConnectionToDatabase();

			String query = "SELECT student_number, pass_word FROM user WHERE student_number = ? AND pass_word = ?";
			java.sql.PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, studentNumber);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				rowsFound = 1;
			}

			rs.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsFound;
	}
}
