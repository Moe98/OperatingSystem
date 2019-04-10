
//package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import com.sun.glass.events.WindowEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	// ==========GUI=================
	static Stage window;
	static Stage fileTextWindow;
	static TextArea cmdTextArea;
	static TextArea fileText;
	// ==========GUI=================

	public static Folder desktop;
	public static Memory memory;
	public static Disk disk;
	public static User user;
	public static Terminal terminal;
	public static Stack musicProcess = new Stack();
	public static userProcess lastOpenFile;
	public static GridPane grid;
	public static Stack pathStack = new Stack();

	@Override
	public void start(Stage primaryStage) throws IOException {
		pathStack.push("desktop");
		user = new User();
		schedulingAlgorithm(2500);
		memory = new Memory();
		disk = new Disk();
		desktop = new Folder("desktop");
		desktop.path = "desktop";
		terminal = new Terminal();
		terminal.getCurrentDirectory().arrayFolder.add(new Folder("joe"));
		terminal.getCurrentDirectory().arrayFolder.add(new Folder("moe"));
		terminal.getCurrentDirectory().arrayFolder.add(new Folder("zizo"));
		terminal.getCurrentDirectory().arrayFolder.get(0).arrayFolder.add(new Folder("minimoe"));
		terminal.getCurrentDirectory().arrayFolder.get(0).arrayFolder.get(0).arrayFolder.add((new Folder("smallmo")));
		terminal.getCurrentDirectory().arrayFile.add(new File("bye"));
		terminal.getCurrentDirectory().arrayFile.get(0).addText("Hey dude");
		terminal.getCurrentDirectory().arrayFolder.get(0).arrayFile.add(new File("HELLO THERE"));
		terminal.getCurrentDirectory().arrayFolder.get(0).arrayFolder.get(0).arrayFile.add(new File("HELLO MAN"));
		// ==================================================GUI===================================================================
		window = new Stage();
		window.setTitle("Command Prompt");
		cmdTextArea = new TextArea();
		cmdTextArea.setPrefHeight(400);
		cmdTextArea.setPrefWidth(900);
		cmdTextArea.setStyle("-fx-control-inner-background: #000000; -fx-font-size:16px;-fx-font-weight:bold;");
		cmdTextArea.appendText(
				"Microsoft Windows [Version 10.0.17134.648]" + "\n" + "(c) 2018 Microsoft Corporation." + "\n" + "\n");
		Scene cmdScene = new Scene(cmdTextArea);
		window.setScene(cmdScene);
		window.show();

		grid = new GridPane();
		grid.setPrefHeight(800);
		grid.setPrefWidth(1500);
		grid.setPadding(new Insets(2));
		grid.setHgap(50);
		grid.setVgap(50);
		grid.setStyle(
				"-fx-background-size: 1500px; -fx-background-repeat:no-repeat; -fx-background-image: url('windows.jpg')");
		int folder = 0;
		Button refreshButton = new Button();
		refreshButton.setOnMouseClicked(new EventHandler() {
			@Override
			public void handle(Event e) {
				refresh();
			}
		});
		refreshButton.setStyle(
				"-fx-background-size: 21px; -fx-background-repeat: no-repeat;-fx-background-image: url('refresh.jpg');");

		Button backButton = new Button();
		backButton.setOnMouseClicked(new EventHandler() {
			@Override
			public void handle(Event e) {
				back();
			}
		});
		backButton.setStyle(
				"-fx-background-size: 21px; -fx-background-repeat: no-repeat;-fx-background-image: url('back.jpg');");

		int r = 1;
		for (; r < 5 && folder < desktop.getArrayFolder().size(); r++)
			for (int j = 0; folder < desktop.getArrayFolder().size() && j < 5; folder++, j++) {
				Button button = new Button('\n' + desktop.getArrayFolder().get(folder).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						openedFolder(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-background-image: url('folderEmpty.jpg');");
				grid.add(button, j, r);
			}
		int file = 0;
		for (; r < 10 && file < desktop.getArrayFile().size(); r++)
			for (int j = 0; file < desktop.getArrayFile().size() && j < 5; file++, j++) {
				Button button = new Button('\n' + desktop.getArrayFile().get(file).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						openedFile(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-text-fill: #000000;-fx-background-image: url('file.jpg');");
				grid.add(button, j, r);
			}
		ScrollPane scrollPane = new ScrollPane(grid);
		BorderPane root2 = new BorderPane();

		HBox hbox1 = new HBox(backButton, refreshButton);
		hbox1.setStyle("-fx-border-style: solid inside;   -fx-background-color: #2f4f4f;\n" + "    -fx-spacing: 10;");

		root2.setTop(hbox1); // Set header
		root2.setCenter(scrollPane); // add your table

		Scene scene2 = new Scene(root2);
		primaryStage.setScene(scene2);
		primaryStage.show();
		// ==================================================GUI===================================================================

		Scanner sc = new Scanner(System.in);

		cmdTextArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					String[] arr = cmdTextArea.getText().split("\n");

					cmdTextArea.appendText("\n");
					String cmd = arr[arr.length - 1];

					if (cmd.equals("hacker mode")) {
						cmd = "clear";
						cmdTextArea.setStyle(
								"-fx-control-inner-background: #000000; -fx-text-fill:green; -fx-font-size: 16px;-fx-font-weight:bold;");
						cmdTextArea.appendText("Microsoft Windows [Version 10.0.17134.648]" + "\n"
								+ "(c) 2018 Microsoft Corporation." + "\n" + "\n");
					}

					if (cmd.equals("clear")) {
						cmdTextArea.setText("");
						cmdTextArea.appendText("Microsoft Windows [Version 10.0.17134.648]" + "\n"
								+ "(c) 2018 Microsoft Corporation." + "\n" + "\n");
					}

					String firstPart = cmd.split(" ")[0];
					// System.out.println(firstPart);
					Process process = null;
					// System.out.println("here" + cmd);
					if (!cmd.equals("clear"))
						switch (firstPart) {
						case "ls":
							// System.out.println("in ls");
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
							process = new userProcess(user.getID(), 1, 5, cmd); // change weight in memory to 0!!!
							break;
						case "editFile":
							process = new userProcess(user.getID(), 2, 10, cmd);
							break;
						}
					if (process != null) {
						System.out.println("pushed process");
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
							System.out.println("in thread");
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

	public void openedFolder(String path) {
		for (Folder f : terminal.getCurrentDirectory().getArrayFolder())
			if (f.Name.equals(path)) {
				terminal.changeDirectory(f);
				pathStack.push(pathStack.peek() + "/" + path);
				System.out.println(pathStack.toString());
				break;
			}
		grid.getChildren().clear();
		grid.setPadding(new Insets(2));
		grid.setHgap(50);
		grid.setVgap(50);
		grid.setStyle("-fx-background-size: 1500px; -fx-background-repeat:no-repeat;");
		int folder = 0;
		int r = 1;
		for (; r < 5 && folder < terminal.getCurrentDirectory().getArrayFolder().size(); r++)
			for (int j = 0; folder < terminal.getCurrentDirectory().getArrayFolder().size() && j < 5; folder++, j++) {
				Button button = new Button('\n' + terminal.getCurrentDirectory().getArrayFolder().get(folder).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						// goBack();
						openedFolder(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-background-image: url('folderEmpty.jpg');");
				grid.add(button, j, r);
			}
		int file = 0;
		for (; r < 10 && file < terminal.getCurrentDirectory().getArrayFile().size(); r++)
			for (int j = 0; file < terminal.getCurrentDirectory().getArrayFile().size() && j < 5; file++, j++) {
				Button button = new Button('\n' + terminal.getCurrentDirectory().getArrayFile().get(file).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						openedFile(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-text-fill: #000000;-fx-background-image: url('file.jpg');");
				grid.add(button, j, r);
			}
//		refresh();
	}

	public void openedFile(String path) {
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!

		fileTextWindow = new Stage();
		fileTextWindow.setTitle(path);
		fileText = new TextArea();
		fileText.setPrefHeight(400);
		fileText.setPrefWidth(900);
		fileText.setStyle("-fx-control-inner-background: #ffffff; -fx-font-size: 16px;-fx-font-weight:bold;");
		String text = "";
		for (File f : terminal.getCurrentDirectory().getArrayFile())
			if (f.Name.equals(path)) {
				text = f.getText();
				break;
			}
		fileText.appendText(text);
		fileText.setEditable(false);
		Scene textScene = new Scene(fileText);
		fileTextWindow.setScene(textScene);
		fileTextWindow.show();

		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
		// add in memory!!!
	}

	public void goBack(String path) {
		System.out.println("Path: " + path);
	}

	public void refresh() {
		grid.getChildren().clear();
		grid.setPadding(new Insets(2));
		grid.setHgap(50);
		grid.setVgap(50);
		if (terminal.getCurrentDirectory().path != null && terminal.getCurrentDirectory().path.equals("desktop"))
			grid.setStyle(
					"-fx-background-size: 1500px; -fx-background-repeat:no-repeat; -fx-background-image: url('windows.jpg')");
		int folder = 0;
		int r = 1;
		for (; r < 5 && folder < terminal.getCurrentDirectory().getArrayFolder().size(); r++)
			for (int j = 0; folder < terminal.getCurrentDirectory().getArrayFolder().size() && j < 5; folder++, j++) {
				Button button = new Button('\n' + terminal.getCurrentDirectory().getArrayFolder().get(folder).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						// goBack();
						openedFolder(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-background-image: url('folderEmpty.jpg');");
				grid.add(button, j, r);
			}
		int file = 0;
		for (; r < 10 && file < terminal.getCurrentDirectory().getArrayFile().size(); r++)
			for (int j = 0; file < terminal.getCurrentDirectory().getArrayFile().size() && j < 5; file++, j++) {
				Button button = new Button('\n' + terminal.getCurrentDirectory().getArrayFile().get(file).Name);
				button.setOnMouseClicked(new EventHandler() {
					@Override
					public void handle(Event e) {
						openedFile(button.getText().substring(1));
					}
				});
				button.setStyle(
						"-fx-background-color: transparent; -fx-background-size: 30px; -fx-background-repeat: no-repeat;-fx-text-fill: #000000;-fx-background-image: url('file.jpg');");
				grid.add(button, j, r);
			}
	}

	public void back() {
		if (pathStack.size() == 1) {
			showAlert(Alert.AlertType.ERROR, window.getScene().getWindow(), "System Error!",
					"CANNOT GO BEYOND DESKTOP!");
			return;
		}
		pathStack.pop();
		terminal.executeCommand("cd " + pathStack.peek());
		refresh();
	}

	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	public void render() {
		Folder currDir = terminal.getCurrentDirectory();
		ArrayList<File> fileList = currDir.getArrayFile();
		ArrayList<Folder> folderList = currDir.getArrayFolder();

	}
}
