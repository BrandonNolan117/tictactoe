package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.UserService;
import beans.User;
import dao.UserDao;
import dao.DatabaseSetup;

/*
 * TODO: number validation
 * 
 */

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {

	public static final String GAME_PAGE_URL = "/gamepage.html";
	public static final String LOGIN_PAGE_URL = "/loginpage.html";
	public static final String REGISTER_PAGE_URL = "/registerpage.html";
	public static final String REGISTERED_PAGE_URL = "/registeredpage.html";

	private static final long serialVersionUID = 1L;

	private UserService userService;

	String errorMessage;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("init method called");
		DatabaseSetup.setupDatabase();
		this.userService = new UserService(new UserDao());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		// This should execute upon initial visit to page
		if (action == null) {
			request.getRequestDispatcher(LOGIN_PAGE_URL).forward(request, response);
		}

		// TODO: Remove?
		else if ("Login".equals(action)) {
			request.getRequestDispatcher(LOGIN_PAGE_URL).forward(request, response);
			
			if (errorMessage != null) {
				writer.write("<br>" + errorMessage);
			}
		}

		// If "Register" was clicked, show user the registration form
		else if ("Register".toLowerCase().equals(action)) {
			request.getRequestDispatcher(REGISTER_PAGE_URL).forward(request, response);

		}

		// If user successfully registered, show confirmation message with link
		// to the main page or the games page
		else if ("registered".equals(action)) {
			request.getRequestDispatcher(REGISTERED_PAGE_URL).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Collect form data

		String action = request.getParameter("action");
		int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String teacher = request.getParameter("teacher");

		// Send user to register form
		if ("Register".equals(action)) {
			response.sendRedirect(request.getContextPath() + "/loginservlet?action=register");
		}

		// LOGIN AUTHENTICATION
		else if ("Login".equals(action)) {
			boolean rowsFound = userService.authenticate(studentNumber, password);
			if (rowsFound) {
				response.sendRedirect(request.getContextPath() + GAME_PAGE_URL);
			} else {
				response.sendRedirect(request.getContextPath() + "/loginservlet?action=Login");
				errorMessage = "Invalid username or password";
			}
		}

		// Register User and show Registration Confirmation page
		else if ("Complete Registration".equals(action)) {

			// TODO: Implement form validation to ensure user is recorded in database
			// successfully or an error is shown

			// Create the User object to be inserted to DB
			User user = new User(studentNumber, firstName, lastName, password, teacher);

			// Register user by adding user to database

			int rowsAffected = userService.registerUser(user);

			if (rowsAffected > 0) {
				response.sendRedirect(request.getContextPath() + "/loginservlet?action=registered");
				errorMessage = null;
			}

			else {
				errorMessage = "An error occured while registering. Please make sure all fields are filled out correctly.";
				System.out.println("Error registering");
			}
		}
	}

}
