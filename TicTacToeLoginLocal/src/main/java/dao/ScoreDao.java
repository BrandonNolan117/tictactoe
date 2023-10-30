package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class ScoreDao {
	
	public void createTable() {
	try {
		Connection connection = DBConnection.getConnectionToDatabase();
		
		//If the there is no score table in the database, create it.  This will need to be moved to be created when a student logs in.
	    String createTable = "CREATE TABLE IF NOT EXISTS score( "
	            + " student_number CHAR(15) NOT NULL PRIMARY KEY,"
	            + " score INT(2) NOT NULL, teacher VARCHAR(15) NOT NULL);";

	    PreparedStatement prepState = connection.prepareStatement(createTable);
	    prepState.execute();
	    
	    
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public int enterWinner(String studentNumber) {
		
		//TODO: Create temporary box in tictactoepage where you enter the user's student number
		int userExists = 0;
		
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			
			String searchQuery = "select student_number from user where student_number = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			statement.setString(1, studentNumber);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				userExists = 1;
				//Proceed to scorepage.html
			} else {
				userExists = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userExists;
	}
	
	public String getClassScore(String teacher) {
		
		
		
		String score = null;
		try {
			Connection connection = DBConnection.getConnectionToDatabase();
			
			//Search the database for students in the user's class
			String searchQuery = "select user.first_name, user.last_name, score.score from user inner join score on user.student_number=score.student_number where score.teacher = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			statement.setString(1, teacher);
			
			ResultSet resultSet = statement.executeQuery();

			
			StringBuilder scoreTable = new StringBuilder();
			scoreTable.append("<!doctype html><html><title>Scores!</title><body><h1>" + teacher + "\'s High Scores</h1>"
					+ "<table><tr><th>First Name</th><th>Last Name</th><th>Score</th></tr>");
			
			while(resultSet.next()) {
				scoreTable.append("<tr>");
				scoreTable.append("<td>" +resultSet.getString("first_name") + "</td>");
				scoreTable.append("<td>" +resultSet.getString("last_name") + "</td>");
				scoreTable.append("<td>" +resultSet.getString("score") + "</td>");
				scoreTable.append("</tr>");
			}
			
			scoreTable.append("</table></body></html>");
			score = scoreTable.toString();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;	
	}

}

