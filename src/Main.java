//package application;

import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	// ==========GUI=================
	static Stage window;
	static TextArea cmdTextArea;
	// ==========GUI=================

	public static Folder desktop;
	public static Memory memory;
	public static Disk disk;
	public static User user;
	public static Terminal terminal;
	public static Stack musicProcess = new Stack();
	public static userProcess lastOpenFile;

	@Override
	public void start(Stage primaryStage) {

		// ==================================================GUI===================================================================
		window = primaryStage;
		window.setTitle("Command Prompt");
		cmdTextArea = new TextArea();
		cmdTextArea.setPrefHeight(400);
		cmdTextArea.setPrefWidth(900);
		cmdTextArea.setStyle("-fx-control-inner-background: #000000;  -fx-font-size: 16px;-fx-font-weight:bold;");
		cmdTextArea.appendText(
				"Microsoft Windows [Version 10.0.17134.648]" + "\n" + "(c) 2018 Microsoft Corporation." + "\n" + "\n");
		Scene cmdScene = new Scene(cmdTextArea);
		window.setScene(cmdScene);
		window.show();
		// ==================================================GUI===================================================================

		user = new User();
		schedulingAlgorithm(2500);
		memory = new Memory();
		disk = new Disk();
		desktop = new Folder("desktop");
		desktop.path = "desktop";
		terminal = new Terminal();
		Scanner sc = new Scanner(System.in);

		cmdTextArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					String[] arr = cmdTextArea.getText().split("\n");

				//	cmdTextArea.appendText("\n");
					String cmd = arr[arr.length - 1];

					if(cmd.equals("hacker mode")) {
						cmd="clear";
						cmdTextArea.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: green; -fx-font-size: 16px;-fx-font-weight:bold;");
						cmdTextArea.appendText(
								"Microsoft Windows [Version 10.0.17134.648]" + "\n" + "(c) 2018 Microsoft Corporation." + "\n" + "\n");
					}
					
					if (cmd.equals("clear")) {
						cmdTextArea.setText("");
						cmdTextArea.appendText(
								"Microsoft Windows [Version 10.0.17134.648]" + "\n" + "(c) 2018 Microsoft Corporation." + "\n" + "\n");
					}

					String firstPart = cmd.split(" ")[0];
					// System.out.println(firstPart);
					Process process = null;
					if (!cmd.equals("clear"))
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
				}
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
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
