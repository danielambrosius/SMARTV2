package smrt2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;




public class SaverLoaderJSON {
	
	public static void main(String[] args) {
		//Model myOde  = (Model) SaverLoader.load("./data/model_for_json.model");
		
		Ode myOde = new Ode("A", "hello some text");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("./data/test_Json.json"), myOde);
			String jsonString = mapper.writeValueAsString(myOde);
			System.out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		try {
			Ode yourOde = mapper.readValue(new File("./data/test_Json.json"), Ode.class);
			String jsonString = mapper.writeValueAsString(yourOde);
			System.out.println(jsonString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	private ObjectMapper mapper;
	//TODO create a saver loader that can handle JSON
}
