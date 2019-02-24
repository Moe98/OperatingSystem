
public abstract class userProcess extends Process {

	public userProcess(int id, int priority, int memory, String state, int memoryStart) {
		super(id, priority, memory, state, memoryStart);
	}
}
