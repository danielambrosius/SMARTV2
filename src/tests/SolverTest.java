package tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import smrt2.SmartTableModel;
import smrt2.Solver;

public class SolverTest extends TestCase {
	
	public void testSolver() {
		List<String> colNames = Arrays.asList("A", "B");
		SmartTableModel S = new SmartTableModel(colNames);
		String[] Formulas = {"P[0]", "P[1]*S[1]"};
		Double[] S0 = {0., 0.};
		Double[] P = {1., 2.};
		double tStart = 0;
		double tEnd = 10;
		double tStep = 1;
		
		Double[][] expected = {{0.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {2.0, 2.0, 2.0}, {3.0, 3.0, 6.0}, {4.0, 4.0, 12.0}, {5.0, 5.0, 20.0}, {6.0, 6.0, 30.0},
				{7.0, 7.0, 42.0}, {8.0, 8.0, 56.0}, {9.0, 9.0, 72.0}, {10.0, 10.0, 90.0}};
		Solver s = new Solver();
		s.solveEulerForward(S, Formulas, S0, P, tStart, tEnd, tStep);
		
		for (int i = 0; i < S.getRowCount(); i++) {
			for (int j = 0; j < S.getColumnCount(); j++) {
				assertEquals(expected[i][j], S.getValueAt(i, j));
			}
		}		
	}
	
}
