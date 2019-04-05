package smrt2;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class ModelLoader {
	
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
}
