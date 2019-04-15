package tests;

import gui.GraphBuilder;
import javafx.scene.chart.XYChart.Series;
import junit.framework.TestCase;

public class GraphBuilderTest extends TestCase {
	public void testArraysToSeries() {
		Double[][] testInput = {{0.0, 0.0, 0.0}};
		Series<Double,Double> seriesResult = GraphBuilder.ArraysToSeries(1, testInput);
		String expected = "[Data[0.0,0.0,null]]";
		assertEquals(expected,seriesResult.getData().toString());
	}

}
