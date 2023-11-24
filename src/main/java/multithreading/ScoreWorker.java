package multithreading;

import dao.TicTacToeDao;

public class ScoreWorker implements Runnable {

	private TicTacToeDao student;
	private String studentNumber;

	public ScoreWorker(TicTacToeDao student, String studentNumber) {
		this.student = student;
		this.studentNumber = studentNumber;
	}

	@Override
	public void run() {

		student.addScore(studentNumber);

	}

}
