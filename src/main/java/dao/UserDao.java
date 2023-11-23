package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import beans.User;

public class UserDao {

	public int registerUser(User user) {

		int rowsAffected = 0;

		try {

			Connection connection = DBConnection.getConnectionToDatabase();

			String insertQuery = "insert into user values(?, ?, ?, ?, ?)";
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);

			statement.setInt(1, user.getStudentNumber());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

			statement.setString(5, user.getTeacherName());

			rowsAffected = statement.executeUpdate();

			// Added this to create a row in the score table whenever a user is registered
			String insertQuery2 = "insert into score values(?, ?, ?)";
			java.sql.PreparedStatement scoreStatement = connection.prepareStatement(insertQuery2);

			scoreStatement.setInt(1, user.getStudentNumber());
			scoreStatement.setInt(2, 0);
			scoreStatement.setString(3, user.getTeacherName());

			scoreStatement.executeUpdate();

			statement.close();
			scoreStatement.close();
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

			String query = "SELECT student_number, pass_word FROM user WHERE student_number = ?";
			java.sql.PreparedStatement statement = connection.prepareStatement(query);

			statement.setInt(1, studentNumber);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				String hashedPassword = rs.getString("pass_word");
				if (BCrypt.checkpw(password, hashedPassword)) {
					rowsFound = 1;
				}
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
