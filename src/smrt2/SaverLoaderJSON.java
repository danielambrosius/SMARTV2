package smrt2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class SaverLoaderJSON {
	private static SaverLoaderJSON instance = null;
	
	private ObjectMapper mapper;
	
	private SaverLoaderJSON() {
		mapper = new ObjectMapper();
		//mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	public static SaverLoaderJSON getInstance() {
		if (instance == null) {
			instance = new SaverLoaderJSON();
		}
		return instance;
	}

	public void save(String savePath, Object obj) {
		try {
			mapper.writeValue(new File(savePath), obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object load(String savePath, Class<?> cls) {
		Object myClass = null;
		try {
			myClass = mapper.readValue(new File("./data/test_Json.json"), cls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myClass;
		
	}
	
	

}
