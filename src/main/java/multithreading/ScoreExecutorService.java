package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.TicTacToeDao;

public class ScoreExecutorService {
	private final ExecutorService executor;

	public ScoreExecutorService() {
		// creating a pool of 5 threads
		this.executor = Executors.newFixedThreadPool(5);
	}

	public void enqueue(TicTacToeDao student, String studentNumber) {
		Runnable worker = new ScoreWorker(student, studentNumber);
		// calling execute method of ExecutorService
		executor.execute(worker);
	}

	public void shutdown() {
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("All Scores Uploaded");
	}
}
