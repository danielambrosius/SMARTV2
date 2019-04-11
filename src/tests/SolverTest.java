package tests;

import junit.framework.TestCase;
import smrt2.Solver;

public class SolverTest extends TestCase {
	public void testSolverCreation() {
		
		String[] Formulas = {"1", "2"};
		double[] S0 = {0, 0};
		double[] P = {1, 2};
		double tEnd = 10;
		double tStep = 1;
		Solver s = new Solver(Formulas, S0, P, tEnd, tStep);
		
		double[][] expected = {{0, 0, 0 }, {1, 1, 2}, {2, 2, 4}, {3, 3, 6}, {4, 4, 8}, {5, 5, 10}, {6, 6, 12},
				{7, 7, 14}, {8, 8, 16}, {9, 9, 18}, {10, 10, 20}};
		double[][] actual = s.solve();
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
//				System.out.printf("%s = %s | ", expected[i][j], actual[i][j]);
				assertEquals(expected[i][j], actual[i][j]);
			}
		System.out.println();
		}
		

		
	}
	
	
	
}
