import java.util.LinkedList;
import java.util.Queue;

public class User {
	private int id;
	private Queue<Process> processQueue;

	public User(int id) {
		this.id = id;
		processQueue = new LinkedList<>();
	}

	public int getID() {
		return id;
	}

	public Queue<Process> getQueue() {
		return processQueue;
	}

	public void pushProcess(Process process) {
		try {
			getQueue().add(process);
		} catch (Exception e) {

		}
	}
}
