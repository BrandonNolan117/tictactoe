package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;

public class AppDao {

	
	public void createTable() {
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			
			//If the there is no user table in the database, create it.
		    String createTable = "CREATE TABLE IF NOT EXISTS user( "
		            + " student_number CHAR(15) NOT NULL PRIMARY KEY,"
		            + " first_name VARCHAR(50) NOT NULL,"
		            + " last_name VARCHAR(50) NOT NULL,"
		            + " pass_word VARCHAR(20) NOT NULL,"
		            + " teacher VARCHAR(15) NOT NULL);";

		    PreparedStatement prepState = connection.prepareStatement(createTable);
		    prepState.execute();
		    
		    
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	
	
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
			
			//Added this to create a row in the score table whenever a user is registered
			String insertQuery2 = "insert into score values(?, ?, ?)";
			java.sql.PreparedStatement scoreStatement = connection.prepareStatement(insertQuery2);
			
			scoreStatement.setInt(1, user.getStudentNumber());
			scoreStatement.setInt(2, 0);
			scoreStatement.setString(3,  user.getTeacherName());
			
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
