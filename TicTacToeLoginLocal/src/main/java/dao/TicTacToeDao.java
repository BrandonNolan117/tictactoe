package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class TicTacToeDao {
	
	public Boolean verify(String studentNumber) {

		Boolean userExists = false;

		try {

			Connection connection = DBConnection.getConnectionToDatabase();

			String query = "SELECT student_number, pass_word FROM user WHERE student_number = ?";
			java.sql.PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, studentNumber);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				userExists = true;
			}

			rs.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userExists;
	}
	
	public void addScore(String studentNumber) {
		
		
		
		String score = null;
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			
			//Search the database for students in the user's class
			String addVictory = "update score set score = score + 1 where student_number = ?";
			PreparedStatement statement = connection.prepareStatement(addVictory);
			statement.setString(1, studentNumber);
			
			statement.executeUpdate();
			statement.close();
			connection.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}


