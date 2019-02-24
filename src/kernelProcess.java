
public abstract class kernelProcess extends Process {

	public kernelProcess(int id, int priority, int memory, String state, int memoryStart) {
		super(id, priority, memory, state, memoryStart);
	}

}
