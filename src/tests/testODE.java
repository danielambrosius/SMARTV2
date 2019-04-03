package tests;

import junit.framework.TestCase;
import smrt2.Ode;

public class testODE extends TestCase {
	public void testOdeExists(){
		Ode myOde = new Ode();
		assertNotNull(myOde);
	}
}
