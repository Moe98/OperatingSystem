
public class File {

	String Name;
	String Text = "";
	private final int Size = 1;

	File(String Name) {
		this.Name = Name;
	}

	public void Rename(String newName) {
		Name = newName;
	}
	public String getName() {
		return Name;
	}
	public void openFile() {
		// TODO GUI HANDELING
	}

	public void addText(String text) {
		Text += text + " ";
	}

	public void editText(String text, int start, int end) {
		Text = Text.substring(0, start) + "" + text + "" + Text.substring(end + 1);
	}

	public void deleteText(int start, int end) {
		Text = Text.substring(0, start) + "" + Text.substring(end + 1);
	}

	public void enterPressed() {
		Text += "\n";
	}
	
	public int getSize() {
		return this.Size;
	}
	
	
}

