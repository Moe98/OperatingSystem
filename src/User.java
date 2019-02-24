import java.util.PriorityQueue;

public class User {
	private int id;
	private PriorityQueue<Process> processQueue;

	public User(int id) {
		this.id = id;
		processQueue = new PriorityQueue<>();
	}

	public int getID() {
		return id;
	}

	public PriorityQueue<Process> getPriorityQueue() {
		return processQueue;
	}

	public void pushProcess(Process process) {
		try {
			getPriorityQueue().add(process);
		} catch (Exception e) {

		}
	}
}
