package smrt2;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SaverLoader {
	
	public Model load(String path){
	Model loadedModel = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			loadedModel = (Model) objectIn.readObject();
			objectIn.close();
			
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	return loadedModel;
	}

	public void save(Model model, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(model);
			objectOut.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}

	public boolean isPath(String path) {
		if (path.matches(".*[\n\r\t\0\f`?*<>|\"].*")) {
			throw new IllegalArgumentException("Error: Illegal charcter used");
		}
		if(!path.endsWith(".bin")){
			throw new IllegalArgumentException("Error: Illegal extension, expecting .bin");
		}
		return true;
	}	
	
	
}
