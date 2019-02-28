import java.util.ArrayList;

public class Folder {

	String Name;
	private int Size;
	ArrayList<Folder> arrayFolder;
	ArrayList<File> arrayFile;

	Folder(String Name) {
		// by default the memory for folder is 5
		usedMemory(5);

		this.Name = Name;
		Main.folders.add(this);
	}
	public String getName() {
		return Name;
	}
	public void Rename(String newName) {
		Name = newName;
	}

	public void openFolder() {
		// TODO GUI HANDELING
	}

	public String createFolderInside(String name) {

		if (!checkForNameUniqness(name))
			return "There is folder or file existing with the same name";

		usedMemory(this.Size + 5);
		arrayFolder.add(new Folder(name));

		return "Created successfully";

	}

	public String createFileInside(String name) {

		if (!checkForNameUniqness(name))
			return "There is folder or file existing with the same name";

		usedMemory(this.Size + 1);
		arrayFile.add(new File(name));

		return "Created successfully";
	}

	public String deleteFolderInside(String name) {
		boolean found = false;

		for (int i = 0; i < arrayFolder.size(); i++) {
			Folder curFolder = arrayFolder.get(i);
			if (curFolder.Name.equals(name)) {
				found = true;
				arrayFolder.remove(i);
				break;
			}

		}

		if (found)
			return "Deleted successfully";
		else
			return "Deletion failed";

	}

	public String deleteFileInside(String name) {
		boolean found = false;

		for (int i = 0; i < arrayFile.size(); i++) {
			File curFile = arrayFile.get(i);
			if (curFile.Name.equals(name)) {
				found = true;
				arrayFile.remove(i);
				break;
			}
		}

		if (found)
			return "Deleted successfully";
		else
			return "Deletion failed";

	}

	public Object Find(String name) {

		// this method as if using control F to find anything

		for (int i = 0; i < arrayFile.size(); i++) {
			if (arrayFile.get(i).Name.equals(name)) {
				return arrayFile.get(i);
			}
		}

		for (int i = 0; i < arrayFolder.size(); i++) {
			if (arrayFolder.get(i).Name.equals(name)) {
				return arrayFolder.get(i);
			}
		}

		return null;

	}

	private void usedMemory(int size) {
		this.Size = size;
	}

	public int getSize() {
		return this.Size;
	}

	public void updateSize() {
		int totalSize = 5;

		for (int i = 0; i < arrayFile.size(); i++) {
			totalSize += arrayFile.get(i).getSize();
		}

		for (int i = 0; i < arrayFolder.size(); i++) {
			totalSize += arrayFolder.get(i).getSize();
		}

		this.usedMemory(totalSize);

	}

	public boolean checkForNameUniqness(String name) {

		for (int i = 0; i < arrayFolder.size(); i++) {
			if (arrayFolder.get(i).Name.equals(name))
				return false;
		}

		for (int i = 0; i < arrayFile.size(); i++) {
			if (arrayFile.get(i).Name.equals(name))
				return false;
		}

		return true;

	}
	public String listAllFilesAndFolders() {
		StringBuilder ans=new StringBuilder();
		for(File file:arrayFile)
			ans.append(file.getName()+"\n");
		for(Folder folder:arrayFolder)
			ans.append(folder.getName()+"\n");
		return ans.toString();
	}

}

