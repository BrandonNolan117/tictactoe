package beans;

public class User {
	private int studentNumber;
	private String firstName;
	private String lastName;
	private String password;
	private String teacherName;

	public User(int studentNumber, String firstName, String lastName, String password, String teacherName) {
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.teacherName = teacherName;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
