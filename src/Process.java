
public abstract class Process implements Comparable {
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
	public String getState() {
		return state;
	}
	public int getMemoryStart() {
		return memoryStart;
	}
	public void setMemory(int memory) {
		this.memory=memory;
	}
	public void setState(String state) {
		this.state=state;
	}
	public void setMemoryStart(int memoryStart) {
		this.memoryStart=memoryStart;
	}
	public void setID(int id) {
		this.id=id;
	}
	public void setPriority(int priority) {
		this.priority=priority;
	}
	public boolean compareTo(Process p){
		if(this.priority>p.priority){
			return true;
		}
		return false;
	}
}
