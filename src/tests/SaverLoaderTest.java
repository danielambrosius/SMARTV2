package tests;

import junit.framework.TestCase;
import smrt2.Model;
import smrt2.SaverLoader;

public class SaverLoaderTest extends TestCase {
	private Model m;
	
	public void setUp() {
		m = new Model();
	}
	
	public void testSaveLoadModel() {
		m.setName("Test");
		String path = "./data/ModelTestSave.bin";
		SaverLoader.save(m, path);
		
		Model loadedModel = (Model) SaverLoader.load(path);
		assertEquals(m.getName(), loadedModel.getName());
	}
	
	public void testIsPath(){
		String path = "./data/ModelTestSave.bin";
		String notPath = "Hoi";
		
		
		SaverLoader sl = new SaverLoader();
		assertTrue(sl.isPath(path));
		assertEquals(false, sl.isPath(notPath));
	
		
	}
}
