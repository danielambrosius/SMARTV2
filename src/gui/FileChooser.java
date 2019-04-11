package gui;

import java.io.File;

// https://www.mkyong.com/swing/java-swing-jfilechooser-example/

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	String filePath;

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
	
	// Opens a dialog to select a <type> to open, returns path of file.
	public static String save(String type, String extension) throws NullPointerException {
	    boolean acceptable = false;
	    String filePath = null;
	    
	    do {
	        filePath = null;
	        File f = null;
	        
	        JFileChooser jfc = new JFileChooser("./data/");
	        jfc.setDialogTitle("Save the " + type);
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(type + " files", extension);
			jfc.addChoosableFileFilter(filter);
			
	        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	            filePath = jfc.getSelectedFile().getAbsolutePath();
	            if (filePath.endsWith(extension)) {
	            	f = jfc.getSelectedFile();
	            	
	            }
	            else {
	            	f = new File(filePath + "." + extension);
	            	filePath = f.getAbsolutePath();
	            }
	
	            if (f.exists()) {
	                int result = JOptionPane.showConfirmDialog(null, filePath.split("\\\\")[filePath.split("\\\\").length-1] + " exists, overwrite?",
	                        "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
	                if (result == JOptionPane.YES_OPTION) {
	                    acceptable = true;
	                }
	            } else {
	                acceptable = true;
	            }
	        } else {
	        	acceptable = true;
	        	return null;
	        }
	    } while (!acceptable);
	    
	    return filePath;
	    
	}
	
	
	// Main method just for test purposes.
	public static void main(String[] args) {
		save("experiment", "exp");
	}

}

