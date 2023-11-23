package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.UserDao;
import dao.ScoreDao;

@WebServlet("/scoreservlet")
public class ScoreServlet extends HttpServlet {

	public static final String SCORE_PAGE_URL = "/scorepage.html";
	public static final String GAME_PAGE_URL = "/tictactoepage.html";
	public static final String MAIN_MENU_URL = "/gamepage.html";

	private static final long serialVersionUID = 1L;

	String scoreTable;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * //TODO: verigy if String studentNumber =
		 * request.getParameter("studentNumber");
		 * 
		 * ScoreDao query = new ScoreDao(); query.createTable(); int studentExists =
		 * query.enterWinner(studentNumber);
		 * 
		 * if (studentExists == 1) { response.sendRedirect(request.getContextPath() +
		 * SCORE_PAGE_URL); } else { response.sendRedirect(request.getContextPath() +
		 * GAME_PAGE_URL); errorMessage = "Invalid student number"; }
		 * 
		 */

		String action = request.getParameter("action");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		// This should execute upon initial visit to page
		// Temporary, need to change this in 3rd assignment to automatically know the
		// teacher based on user currently logged in
		if (action == null) {
			writer.write(writeTeacher() + "<br>");

			// TODO: Returns to gamepage.html/main menu
			writer.write("<a href='" + request.getContextPath() + MAIN_MENU_URL + "'>Return to main menu</a>");

			// TODO:add a "play again" button to redirect to GAME_PAGE_URL
		}

		// Temporary, until the null action is changed in assignment 3
		else if ("Score".equals(action)) {

			writer.write(scoreTable + "<br>");

			// Return to Main Menu option
			writer.write("<a href='" + request.getContextPath() + MAIN_MENU_URL + "'>Return to main menu</a>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Collect teacher and action info
		String action = request.getParameter("action");
		String selectedTeacher = request.getParameter("teacher");

		// create score table if not exist
		ScoreDao score = new ScoreDao();
		score.createTable();

		if ("Score".equals(action)) {

			ScoreDao getScore = new ScoreDao();

			// Get database info for specified teacher
			scoreTable = getScore.getClassScore(selectedTeacher);

			response.sendRedirect(request.getContextPath() + "/scoreservlet?action=Score");

		}

	}

	// Temporary page to select teacher from a drop down menu
	public String writeTeacher() {
		String form = "<!doctype html><html><title>Scoreboard</title>" + "<body><h1>Select a teacher</h1>"
				+ "<form method = 'post' action='scoreservlet'>"
				+ "<table><tr><td><label for = 'teacher'>Choose your teacher </label><select name='teacher' id='teacher'><br><br>"
				+ "<option value='Mrs A'>Mrs. A</option>" + "<option value='Mr B'>Mr. B</option>"
				+ "<option value='Ms C'>Ms. C</option>" + "<option value='Mrs D'>Mrs. D</option></select><br><br>"
				+ "<tr><td><input type='submit' name='action' value='Score'></td></tr>"
				+ "</table></form></body></html>";
		return form;
	}
}
