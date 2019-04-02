import java.util.Arrays;

public class Memory {
	int size;
	int[] memory;
	final static int FREE = -1;

	Memory() {
		size = 2048;
		memory = new int[size];
		Arrays.fill(memory, FREE);
	}

	public boolean assignProcess(Process process) {
		if (getAvailable() < process.getMemory())
			return false;
		for (int i = 0, assigned = 0; assigned < process.getMemory(); i++)
			if (memory[i] == FREE) {
				memory[i] = process.getID();
				assigned++;
			}
		return true;
	}
	public boolean removeProcess(Process process) {
		int assigned=0;
		for(int x:memory)
			if(x==process.getID())
				assigned++;
		if(assigned!=process.getMemory()) 
			return false;
		for(int i=0;i<memory.length;i++)
			if(memory[i]==process.getID())
				memory[i]=FREE;
		return true;
	}
	

	public int getAvailable() {
		int ans = 0;
		for (int x : memory)
			if (x == FREE)
				ans++;
		return ans;
	}

}
