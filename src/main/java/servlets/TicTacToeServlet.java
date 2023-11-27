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
import multithreading.ScoreExecutorService;
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

			request.getRequestDispatcher(GAME_PAGE_URL).forward(request, response);
			if (errorMessage != null) {
				writer.write("<br>" + errorMessage);
			}

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


			TicTacToeDao student = new TicTacToeDao();
			Boolean studentFound = student.verify(studentNumber);
			if (studentFound) {

				// Multithreading for submitting scores
				ScoreExecutorService scoreExecutorService = new ScoreExecutorService();
				scoreExecutorService.enqueue(student, studentNumber);
				scoreExecutorService.shutdown();

				response.sendRedirect(request.getContextPath() + SCORE_PAGE_URL);
			} else {
				response.sendRedirect(request.getContextPath() + "/tictactoeservlet");
				errorMessage = "Invalid student number<br><br>";
			}
		}

	}

	// temporary code to create a "Tic Tac Toe" page
	public String writeTicTacToeGame() {
		String form = "<!doctype html><html><title>Tic Tac Toe!</title>" + "<head>\r\n"
				+ "  <meta charset=\"UTF-8\">\r\n"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + "  <style>\r\n"
				+ "    table {\r\n" + "      border-collapse: collapse;\r\n" + "      width: 300px;\r\n"
				+ "      margin: 50px auto;\r\n" + "    }\r\n" + "\r\n" + " th, td {\r\n"
				+ "      border: 1px solid #ddd;\r\n" + "      text-align: center;\r\n"
				+ "      width: 100px; /* Set a fixed width for each cell */\r\n"
				+ "      height:100px; /* Set a fixed height for each cell */\r\n" + "    }" + "    input.game {\r\n"
				+ "      width: 100%;\r\n" + "      height: 100%;\r\n" + "      box-sizing: border-box;"
				+ "		 font-size: 50px;" + " 	text-align: center;" + "    }\r\n" + "  </style>\r\n" + "</head>"
				+ "<body><h1>Placeholder for Tic Tac Toe game</h1>" + "<table>\r\n" + "  <tr>\r\n"
				+ "    <td><input class = \"game\"  type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n" + "  </tr>\r\n"
				+ "  <tr>\r\n" + "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n" + "  </tr>\r\n"
				+ "  <tr>\r\n" + "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n"
				+ "    <td><input class = \"game\" type=\"text\" class=\"editable-cell\"></td>\r\n" + "  </tr>\r\n"
				+ "</table>" + "<form method='post' action='tictactoeservlet'"
				+ "<table><tr><td><label>Please enter student number of the winner</label><input type='number' name='studentNumber' required='required'</td></tr><br><br>"
				+ "<tr><td><input type='submit' name='action' value='Submit'</td></tr>" + "</table></form>"

				+ "</body></html>";
		return form;
	}

}
