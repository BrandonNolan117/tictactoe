package Service;

import beans.User;
import dao.UserDao;

public class UserService {

	private UserDao userDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public int registerUser(User user) {
		return userDao.registerUser(user);
	}

	public boolean authenticate(int studentNumber, String password) {
		return userDao.authenticate(studentNumber, password) == 1;
	}
}
