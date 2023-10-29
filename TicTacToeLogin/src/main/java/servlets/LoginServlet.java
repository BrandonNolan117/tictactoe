package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.AppDao;

/*
 * TODO: number validation
 * 
 */

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {

	public static final String GAME_PAGE_URL = "/gamepage.html";

	private static final long serialVersionUID = 1L;

	String errorMessage;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		// This should execute upon initial visit to page
		if (action == null) {
			writer.write(writeLoginForm() + "<br>");
			writer.write("<a href='" + request.getContextPath()
					+ "/loginservlet?action=register'>Don't have an account? Click here to register</a>");
		}

		// TODO: Remove?
		else if ("Login".equals(action)) {
			writer.write(writeLoginForm());
			if (errorMessage != null) {
				writer.write("<br>" + errorMessage);
			}
		}

		// If "Register" was clicked, show user the registration form
		else if ("Register".toLowerCase().equals(action)) {
			writer.write(writeRegisterForm());

		}

		// If user successfully registered, show confirmation message with link
		// to the main page or the games page
		else if ("registered".equals(action)) {
			writer.write("<!doctype html><html><title>Login</title>" + "<body><h1>"
					+ "Thank you for registering</h1><br><br>" + "<a href='" + request.getContextPath() + GAME_PAGE_URL
					+ "'>Click here to proceed to the games page</a>\n" + "</body></html>");
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

			AppDao query = new AppDao();
			int rowsFound = query.authenticate(studentNumber, password);
			if (rowsFound == 1) {
				response.sendRedirect(request.getContextPath() + GAME_PAGE_URL);
			} else {
				response.sendRedirect(request.getContextPath() + "/loginservlet?action=Login");
				errorMessage = "Invalid username or password";
			}

		}

		// Register User and show Registration Confirmation page
		else if ("Complete Registration".equals(action)) {

			// TODO: Implement form validation to ensure user is recoreded in database
			// successfully or an error is shown

			// Create the User object to be inserted to DB
			User user = new User(studentNumber, firstName, lastName, password, teacher);

			// Register user by adding user to database
			AppDao query = new AppDao();
			int rowsAffected = query.registerUser(user);

			if (rowsAffected > 0) {
				response.sendRedirect(request.getContextPath() + "/loginservlet?action=registered");
				errorMessage = null;
			}

			else {
				errorMessage = "An error occured while registering. Please make sure all fields are filled out correctly.";
			}
		}
	}

	// methods to write the login forms
	public String writeLoginForm() {
		String form = "<!doctype html><html><title>Login</title>" + "<body><h1>User Login Page</h1>"
				+ "<form method='post' action='loginservlet'"
				+ "<table><tr><td><label>Student Number </label><input type='number' name='studentNumber' required='required'</td></tr><br><br>"
				+ "<tr><td><label for='password'>Password </label> <input type='password' id='password' name='password' required='required'</td></tr><br><br>"
				+ "<tr><td><input type='submit' name='action' value='Login'</td></tr>" + "</table></form>"

				+ "</body></html>";
		return form;
	}

	public String writeRegisterForm() {
		String form = "<!doctype html><html><title>Login</title>" + "<body><h1>Please Register</h1>"
				+ "<form method='post' action='loginservlet'"
				+ "<table><tr><td><label>Student Number </label><input type='text' name='studentNumber' required='required'</td></tr><br><br>"
				+ "<tr><td><label>First Name </label> <input type='text' name='firstName' required='required'</td></tr><br><br>"
				+ "<tr><td><label>Last Name </label> <input type='text' name='lastName' required='required'</td></tr><br><br>"
				+ "<tr><td><label>Password </label> <input type='password' name='password' required='required'</td></tr><br><br>"
				+ "<tr><td><label for='teacher'>Choose your teacher </label><select name='teacher'><br><br>"
				+ "<option value='Mrs A'>Mrs. A</option>" + "<option value='Mr B'>Mr. B</option>"
				+ "<option value='Ms C'>Ms. C</option>" + "<option value='Mrs D'>Mrs. D</option></select><br><br>"
				+ "<tr><td><input type='submit' name='action' value='Complete Registration'</td></tr>"
				+ "</table></form></body></html>";
		return form;
	}
}
