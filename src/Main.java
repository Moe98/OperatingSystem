import java.util.ArrayList;

public class Main {

	public static boolean doesFolderExist(Folder folder) {
		for(Folder x:folders)
			if(x.getName().equals(folder.getName()))
				return true;
		return false;
	}
	static public ArrayList<Folder> folders;
	public static void main(String[] args) {
		Terminal terminal=new Terminal();
		folders=new ArrayList();
		

	}

}
