package smrt2;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class SaverLoader {
	private static SaverLoader instance = null;
	
	private ObjectMapper mapper;
	
	private SaverLoader() {
		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	/**
	 * Used to get have only one instance of this class
	 * @return the instance of this class
	 */
	public static SaverLoader getInstance() {
		if (instance == null) {
			instance = new SaverLoader();
		}
		return instance;
	}
	
	/**
	* Used to save a file
	* @param savePath is the path where to save the file 
	* @param obj is the class of the saved file
	*/
	public void save(String savePath, Object obj) {
		try {
			mapper.writeValue(new File(savePath), obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to load a file
	 * @param savePath is the path which file to load
	 * @param cls is the class that has to be loaded
	 * @return
	 */
	public Object load(String savePath, Class<?> cls) {
		Object myClass = null;
		 try {
			myClass = mapper.readValue(new File(savePath), cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myClass;
		
	}
	
	

}
