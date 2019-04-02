import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Main {

	public static Folder desktop;
	public static Memory memory;
	public static Disk disk;
	public static User user;
	public static Terminal terminal;
	public static Stack musicProcess = new Stack();
	public static userProcess lastOpenFile;

	public static void main(String[] args) {
		user = new User();
		schedulingAlgorithm(2500);
		memory = new Memory();
		disk = new Disk();
		desktop = new Folder("desktop");
		desktop.path = "desktop";
		terminal = new Terminal();
		Scanner sc = new Scanner(System.in);
		while (true) {
			String cmd = sc.nextLine();
			String firstPart = cmd.split(" ")[0];
			// System.out.println(firstPart);
			Process process = null;
			switch (firstPart) {
			case "ls":
				process = new userProcess(user.getID(), 3, 15, cmd);
				break;
			case "cd":
				process = new userProcess(user.getID(), 5, 4, cmd);
				break;
			case "touch":
				process = new userProcess(user.getID(), 4, 4, cmd);
				break;
			case "create":
				process = new userProcess(user.getID(), 6, 4, cmd);
				break;
			case "pwd":
				process = new userProcess(user.getID(), 3, 4, cmd);
				break;
			case "deleteFolder":
				process = new userProcess(user.getID(), 4, 4, cmd);
				break;
			case "deleteFile":
				process = new userProcess(user.getID(), 7, 4, cmd);
				break;
			case "play": {
				process = new userProcess(user.getID(), 3, 7, cmd);
				musicProcess.push(process);
				break;
			}
			case "stop":
				process = new userProcess(user.getID(), 1, 5, cmd);
				break;
			case "openFile":
				process = new userProcess(user.getID(), 4, 5, cmd);
				lastOpenFile = new userProcess(process.getID(), 4, 5, cmd);
				break;
			case "closeFile":
				process = new userProcess(user.getID(), 1, 5, cmd);
				break;
			case "editFile":
				process = new userProcess(user.getID(), 2, 10, cmd);
				break;
			}
			if (process != null) {
				user.pushProcess(process);

			}
			// if (user.getPriorityQueue().size() > 0)
			// for (Process p : user.getPriorityQueue())
			// System.out.println(p.getID() + " " + p.getMemory() + " " + p.getPcb() + " " +
			// p.getPriority());
			// terminal.executeCommand(cmd);
		}

	}

	public static void schedulingAlgorithm(int delay) {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					System.out.print("");
					try {
						if (!user.getPriorityQueue().isEmpty()) {
							// System.out.println(Arrays.toString(memory.memory));
							Process process = user.getPriorityQueue().peek();
							boolean assigned = memory.assignProcess(process);
							if (assigned) {
								terminal.executeCommand(user.getPriorityQueue().poll().getPcb());
								// System.out.println("here");
							}
							if (assigned && !process.getPcb().split(" ")[0].equals("play")
									&& !process.getPcb().split(" ")[0].equals("openFile")) {
								System.out.println(process.getPcb().split(" ")[0]);
								if (process.getPcb().split(" ")[0].equals("stop")) {
									// System.out.println("in here");
									memory.removeProcess((Process) musicProcess.pop());
								}
								if (process.getPcb().split(" ")[0].equals("closeFile")) {

								}
								// System.out.println(process.getID());
								memory.removeProcess(process);
								user.getPriorityQueue().poll();
							}
						}
					} catch (Exception e) {
						// System.out.println("exception");
						System.out.println(e.getMessage());
					}
				}
			}
		}.start();
	}

}
