
public class Terminal {
	private Folder currentDirectory;

	public Terminal() {
		// not sure how to know the name of the user
		System.out.println("Welcome User");
		currentDirectory = Main.desktop;
	}

	public boolean changeDirectory(Folder folder) { // cd
		if (folder == null)
			return false;
		currentDirectory = folder;
		return true;
	}

	public void printCurrentDirectory() { // pwd
		System.out.println(currentDirectory.getName());
	}

	public void listAllFiles() { // ls
		System.out.println(currentDirectory.listAllFilesAndFolders());
	}

	public boolean createNewFolder(String folderName) {
		if (!currentDirectory.checkForNameUniqness(folderName))
			return false;
		currentDirectory.createFolderInside(folderName);
		return true;
	}

	public boolean createNewFile(String fileName) {
		if (!currentDirectory.checkForNameUniqness(fileName))
			return false;
		currentDirectory.createFolderInside(fileName);
		return true;
	}

	public boolean executeCommand(String cmd) {
		if (cmd.startsWith("ls")) {
			listAllFiles();
			return true;
		}
		if (cmd.startsWith("cd")) {
			Folder to = Folder.getFolder(cmd.split(" ")[1]);
			changeDirectory(to);
			return to != null;
		}
		if (cmd.startsWith("pwd")) {
			printCurrentDirectory();
			return true;
		}
		if (cmd.startsWith("create")) // create folderName
		{
			createNewFolder(cmd.split(" ")[1]);
			return true;
		}
		if (cmd.startsWith("touch")) { // create fileName
			createNewFile(cmd.split(" ")[1]);
			return true;
		}
		return false;
	}

}
