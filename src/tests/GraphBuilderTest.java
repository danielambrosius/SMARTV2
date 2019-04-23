package tests;

import gui.GraphBuilder;
import javafx.scene.chart.XYChart.Series;
import junit.framework.TestCase;

public class GraphBuilderTest extends TestCase {
	public void testArraysToSeries() {
		Double[] testInput = {10.0, 120.0, 0.0};
		Double[] testTime = {1., 2., 3.};
		Series<Number,Number> seriesResult = GraphBuilder.ArraysToSeries(testTime, testInput);
		String expected = "[Data[1.0,10.0,null], Data[2.0,120.0,null], Data[3.0,0.0,null]]";
		assertEquals(expected,seriesResult.getData().toString());
	}

}
