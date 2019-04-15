package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;
import smrt2.Ode;
import smrt2.SaverLoaderJSON;

public class SaverLoaderJSONTest extends TestCase {

	public void testCreateSaverLoaderJSON() {
		SaverLoaderJSON mySl= SaverLoaderJSON.getInstance();
		assertNotNull(mySl);
	}
	
	public void testSavingFile() {
		String savePath = "./data/test_Json.json";
		Path savePath2 = Paths.get("./data/test_Json.json");
		String expected = "{\"formula\":\"H*R\",\"state\":\"F\",\"variables\":[\"H\",\"R\"],\"operators\":[\"\",\"*\"]}";
		Ode myOde = new Ode("F","H*R");
		SaverLoaderJSON mySl= SaverLoaderJSON.getInstance();
		mySl.save(savePath, myOde);
		try {
			String observed = new String(Files.readAllBytes(savePath2));
			assertEquals(expected, observed );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLoadFile() {
		String savePath = "./data/test_Json.json";
		Ode expectedOde = new Ode("F","H*R");
		SaverLoaderJSON mySl= SaverLoaderJSON.getInstance();
		mySl.save(savePath, expectedOde);
		
		Ode observedOde =  (Ode) mySl.load(savePath, Ode.class);
		assertEquals(expectedOde.getState(), observedOde.getState());
		assertEquals(expectedOde.getFormula(), observedOde.getFormula());
		
	}
}
