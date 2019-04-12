package tests;

import junit.framework.TestCase;
import smrt2.Solver;

public class SolverTest extends TestCase {
	public void testSolverCreation() {
		

		String[] Formulas = {"P[0]", "P[1]*S[1]"};
		Double[] S0 = {0., 0.};
		Double[] P = {1., 2.};
		double tEnd = 10;
		double tStep = 1;
		Solver s = new Solver(Formulas, S0, P, tEnd, tStep);
		
		Double[][] expected = {{0.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {2.0, 2.0, 2.0}, {3.0, 3.0, 6.0}, {4.0, 4.0, 12.0}, {5.0, 5.0, 20.0}, {6.0, 6.0, 30.0},
				{7.0, 7.0, 42.0}, {8.0, 8.0, 56.0}, {9.0, 9.0, 72.0}, {10.0, 10.0, 90.0}};
		Double[][] actual = s.solve();
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				System.out.printf("%s = %s | ", expected[i][j], actual[i][j]);
				assertEquals(expected[i][j], actual[i][j]);
			}
		System.out.println();
		}
		

		
	}
	
	
	
}
