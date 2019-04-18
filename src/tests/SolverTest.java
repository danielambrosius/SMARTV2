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
		double tStart = 0.0;
		
		Double[][] expected = {{0.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {2.0, 2.0, 2.0}, {3.0, 3.0, 6.0}, {4.0, 4.0, 12.0}, {5.0, 5.0, 20.0}, {6.0, 6.0, 30.0},
				{7.0, 7.0, 42.0}, {8.0, 8.0, 56.0}, {9.0, 9.0, 72.0}, {10.0, 10.0, 90.0}};
		Solver s = new Solver();
		Double[][] actual = s.solveEulerForward(Formulas, S0, P, tEnd, tStep, tStart);
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
		}
	}

	public void testSolverCreationWithDifferentStartTime() {
		String[] Formulas = {"P[0]", "P[1]*S[1]"};
		Double[] S0 = {0., 0.};
		Double[] P = {1., 2.};
		double tEnd = 3;
		double tStep = 1.0;
		double tStart = 2.0;
		
		Double[][] expected = {{2.0, 0.0, 0.0}, {3.0, 1.0, 0.0}};
		Solver s = new Solver();
		Double[][] actual = s.solveEulerForward(Formulas, S0, P, tEnd, tStep, tStart);
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				assertEquals(expected[i][j], actual[i][j]);
			}
		}	
	}
}
