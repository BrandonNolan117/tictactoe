package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
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

	public static final String HIGHSCORE_PAGE_URL = "/databasescorepage.html";
	public static final String SCORE_PAGE_URL = "/scorepage.html";
	public static final String GAME_PAGE_URL = "/tictactoepage.html";
	public static final String MAIN_MENU_URL = "/gamepage.html";

	private static final long serialVersionUID = 1L;

	String scoreTable;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * //TODO: verify if String studentNumber =
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
			request.getRequestDispatcher(SCORE_PAGE_URL).forward(request, response);

			// TODO: Returns to gamepage.html/main menu
			writer.write("<a href='" + request.getContextPath() + MAIN_MENU_URL + "'>Return to main menu</a>");

			// TODO:add a "play again" button to redirect to GAME_PAGE_URL
		}

		// Temporary, until the null action is changed in assignment 3
		else if ("Score".equals(action)) {
			
			String content = "";
			try {
				String filePath = getServletContext().getRealPath(HIGHSCORE_PAGE_URL);
				BufferedReader in = new BufferedReader(new FileReader(filePath));
				String str;
				while ((str = in.readLine()) != null) {
					content += str;
				}
				
				in.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}

			String updatedPage = content.replace("%InsertTableHere%", scoreTable)
										.replace("<h1>","<h3>")
										.replace("</h1>","</h3>")
										.replace("<table>","<table class='highScore'>")
										.replace("<!doctype html><html><title>Scores!</title><body>", "")
										.replace("</body></html></article>","</article>");
			
			writer.write(updatedPage);
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
			request.getSession().setAttribute("scoreTable",  scoreTable);
			request.getSession().setAttribute("selectedTeacher", selectedTeacher);

			response.sendRedirect(request.getContextPath() + "/scoreservlet?action=Score");

		}

	}

}
