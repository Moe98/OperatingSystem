import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static Folder desktop;
	public static Memory memory;
	public static Disk disk;

	public static void main(String[] args) {
		memory = new Memory();
		disk = new Disk();
		desktop = new Folder("desktop");
		desktop.path = "desktop";
		Terminal terminal = new Terminal();
		Scanner sc = new Scanner(System.in);
		while (true) {
			String cmd = sc.nextLine();
			terminal.executeCommand(cmd);
			System.out.println(Arrays.toString(disk.memory));
		}

	}
}
