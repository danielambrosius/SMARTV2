package tests;

import gui.GraphBuilder;
import javafx.scene.chart.XYChart.Series;
import junit.framework.TestCase;

public class GraphBuilderTest extends TestCase {
	public void testArraysToSeries() {
		Double[][] testInput = {{10.0, 120.0, 0.0}};
		Series<Number,Number> seriesResult = GraphBuilder.ArraysToSeries(1, testInput);
		String expected = "[Data[10.0,120.0,null]]";
		System.out.println(seriesResult.getData().toString());
		assertEquals(expected,seriesResult.getData().toString());
	}

}
