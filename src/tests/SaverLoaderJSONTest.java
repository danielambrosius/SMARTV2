package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.TestCase;
import smrt2.Model;
import smrt2.Ode;
import smrt2.SaverLoaderJSON;

public class SaverLoaderJSONTest extends TestCase {

	public void testCreateSaverLoaderJSON() {
		SaverLoaderJSON mySl= SaverLoaderJSON.getInstance();
		assertNotNull(mySl);
	}
	
	public void testSavingOde() {
		String savePath = "./data/test_Json.json";
		Path savePath2 = Paths.get("./data/test_Json.json");
		String expected = "{\"state\":\"F\",\"formula\":\"H*R\"}";
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
	
	public void testLoadOde() {
		String savePath = "./data/test_Json.json";
		Ode expectedOde = new Ode("F","H*R");
		SaverLoaderJSON mySl= SaverLoaderJSON.getInstance();
		mySl.save(savePath, expectedOde);
		
		Ode observedOde =  (Ode) mySl.load(savePath, Ode.class);
		assertEquals(expectedOde.getState(), observedOde.getState());
		assertEquals(expectedOde.getFormula(), observedOde.getFormula());
		
	}
	
	public void testSaveLoadModel() {
		String savePath = "./data/test_JsonModel.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		
		SaverLoaderJSON mySl = SaverLoaderJSON.getInstance();
		mySl.save(savePath, m);
		
		Model mObserved = (Model) mySl.load(savePath, Model.class);
		assertTrue(m.getStates().equals(mObserved.getStates()));
		
	}
}
