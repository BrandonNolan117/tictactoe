package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Game;
import beans.User;
import dao.UserDao;
import dao.ScoreDao;
import dao.TicTacToeDao;

@WebServlet("/tictactoeservlet")
public class TicTacToeServlet extends HttpServlet {

	public static final String SCORE_PAGE_URL = "/scorepage.html";
	public static final String GAME_PAGE_URL = "/tictactoepage.html";
	public static final String MAIN_MENU_URL = "/gamepage.html";

	private static final long serialVersionUID = 1L;

	String errorMessage;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		// This should execute upon initial visit to page
		// Temporary, need to change this in 3rd assignment to automatically know the
		// teacher based on user currently logged in
		if (action == null) {
			Game newGame = new Game();
			writer.write(writeTicTacToeGame() + "<br>");

			if (errorMessage != null) {
				writer.write("<br>" + errorMessage);
			}

			// TODO: Returns to gamepage.html/main menu
			writer.write("<a href='" + request.getContextPath() + MAIN_MENU_URL + "'>Return to main menu</a>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Collect teacher info
		String action = request.getParameter("action");
		String studentNumber = request.getParameter("studentNumber");

		if ("Submit".equals(action))
			;
		{
			// create score table if not exist
			ScoreDao score = new ScoreDao();
			score.createTable();

			TicTacToeDao student = new TicTacToeDao();
			Boolean studentFound = student.verify(studentNumber);
			if (studentFound) {
				student.addScore(studentNumber);
				response.sendRedirect(request.getContextPath() + SCORE_PAGE_URL);
			} else {
				response.sendRedirect(request.getContextPath() + "/tictactoeservlet");
				errorMessage = "Invalid student number<br><br>";
			}
		}

	}

	// temporary code to create a "Tic Tac Toe" page
	public String writeTicTacToeGame() {
		String form = "<!doctype html><html><title>Tic Tac Toe!</title>"
				+ "<body><h1>Placeholder for Tic Tac Toe game</h1>"

				+ "<form method='post' action='tictactoeservlet'"
				+ "<table><tr><td><label>Please enter student number of the winner</label><input type='number' name='studentNumber' required='required'</td></tr><br><br>"
				+ "<tr><td><input type='submit' name='action' value='Submit'</td></tr>" + "</table></form>"

				+ "</body></html>";
		return form;
	}

}
