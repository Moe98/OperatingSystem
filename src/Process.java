
public abstract class Process {
	private int id;
	private int priority;
	private int memory;       //size of memory process owns
	private String state;
	private int memoryStart;  //first index in the array representing memory

	public Process(int id, int priority,int memory,String state,int memoryStart) {
		this.id = id;
		this.priority = priority;
		this.memory=memory;
		this.state=state;
		this.memoryStart=memoryStart;
	}

	public int getID() {
		return id;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getMemory() {
		return memory;
	}
	
	
}
