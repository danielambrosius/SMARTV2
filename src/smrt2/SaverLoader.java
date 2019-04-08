package smrt2;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class SaverLoader {
	// NOTE: This implementation brakes when the source code defining the
	// 		 the serialized class is changed. (e.g. the user will not be able to load
	//		  models generated with an earlier release of the code. Consider fixing.)	
	
	static public Object load(String path){
	Object loadedObject = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			loadedObject = objectIn.readObject();
			objectIn.close();
			
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	return loadedObject;
	}

	static public void save(Object object, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(object);
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
