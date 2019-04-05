package gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {

	// Opens a dialog to select a <type> to open, returns path of file.
	public static String open(String type, String extension) {
		
		String filePath = null;
		
		JFileChooser jfc = new JFileChooser("./data/");
		jfc.setDialogTitle("Select a " + type);
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(type + " files", extension);
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			filePath = jfc.getSelectedFile().getPath();
		}
		return filePath;

	}
	
	
	// Main method just for test purposes.
	public static void main(String[] args) {
		open("experiment", "exp");
	}

}

