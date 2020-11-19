package view.widgets;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;

public class StackedBarChart{
	
	private CategoryDataset dataSet;
	private double[][] data;
	private JFreeChart chart;
	private String firstBar;
	private String secBar;

//	private final ChartPanel chartPanel = new ChartPanel(chart);
	
	private StackedBarChart(CategoryDataset dataSet, double[][] data, JFreeChart chart, String firstBar, String secBar) {
		this.dataSet = dataSet;
		this.data = data;
		this.chart = chart;
		this.firstBar = firstBar;
		this.secBar = secBar;
	}

	/**
	 * Initializes a stacked bar chart with the given categories
	 * @param firstBar Category of the first bar chart
	 * @param secBar   Category of the second bar chart
	 * @return		   The stacked bar chart without data
	 */
	public static StackedBarChart createStackedBarChart(String firstBar, String secBar) {
		double[][] data = new double[][] {{0.0}, {0.0}};
		
		CategoryDataset dataSet = DatasetUtils.createCategoryDataset(firstBar, secBar, data);
		
		JFreeChart chart = buildStackedBarChart(dataSet);
		
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
		BarRenderer.setDefaultBarPainter(new StandardBarPainter());
		BarRenderer.setDefaultShadowsVisible(false);
		
		return new StackedBarChart(dataSet, data, chart, firstBar, secBar);
	}

	/**
	 * Sets the data for a initialized stacked bar chart (Only 0 - 100)
	 * @param firstBar	Value of the first bar chart
	 * @param secBar	Value of the second bar chart
	 */
	public void setValue(double firstBar, double secBar) {
		
		this.data[0][0] = firstBar;
		this.data[1][0] = secBar;
		
		this.dataSet = DatasetUtils.createCategoryDataset(this.firstBar, this.secBar, this.data);
		this.chart = buildStackedBarChart(this.dataSet);
	}
	
	private static JFreeChart buildStackedBarChart(CategoryDataset dataSet) {
		JFreeChart chart = ChartFactory.createStackedBarChart(null, null, null, dataSet, PlotOrientation.HORIZONTAL, false, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);
		
		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(128, 0, 0));
		renderer.setSeriesPaint(1, new Color(0, 0, 255));
		renderer.setDefaultFillPaint(new Color(0, 0, 0));
		
		plot.getDomainAxis().setVisible(false);
		
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setRange(0, 100);
		rangeAxis.setVisible(false);
		
		return chart;
	}
	
	/**
	 * @return The inherited JFreeChart
	 */
	public JFreeChart getChart() {
		return this.chart;
	}
}