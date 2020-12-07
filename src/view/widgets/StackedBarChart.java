package view.widgets;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtils;

//TODO @jakobwilmesmeier javadoc
public class StackedBarChart {

	private DefaultCategoryDataset dataSet;
	private double[][] data;
	private JFreeChart chart;

//	private final ChartPanel chartPanel = new ChartPanel(chart);

	private StackedBarChart(DefaultCategoryDataset dataSet, double[][] data, JFreeChart chart, String firstBar,
			String secBar) {
		this.dataSet = dataSet;
		this.data = data;
		this.chart = chart;
	}

	/**
	 * Initializes a stacked bar chart with the given categories
	 * 
	 * @param firstBar Category of the first bar chart
	 * @param secBar   Category of the second bar chart
	 * @return The stacked bar chart without data
	 */
	public static StackedBarChart createStackedBarChart(String firstBar, String secBar) {
		double[][] data = new double[][] { { 0.0 }, { 0.0 } };

		DefaultCategoryDataset dataSet = (DefaultCategoryDataset) DatasetUtils.createCategoryDataset(firstBar, secBar,
				data);
		BarRenderer.setDefaultBarPainter(new StandardBarPainter());
		BarRenderer.setDefaultShadowsVisible(false);

		JFreeChart chart = buildStackedBarChart(dataSet);


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
		
		this.dataSet.clear();
		this.dataSet.setValue(firstBar, Integer.valueOf(0), Integer.valueOf(0));
		this.dataSet.setValue(secBar, Integer.valueOf(1), Integer.valueOf(0));

	}

	private static JFreeChart buildStackedBarChart(CategoryDataset dataSet) {
		JFreeChart chart = ChartFactory.createStackedBarChart(null, null, null, dataSet, PlotOrientation.HORIZONTAL,
				false, true, true);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);

		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(200, 0, 0));
		renderer.setSeriesPaint(1, new Color(0, 0, 200));
		renderer.setDefaultFillPaint(new Color(0, 0, 0));
		renderer.setSeriesToolTipGenerator(0, new ToolTipGenerator("Erstgutachten"));
		renderer.setSeriesToolTipGenerator(1, new ToolTipGenerator("Zweitgutachten"));

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

//TODO @jakobwilmesmeier javadoc
class ToolTipGenerator implements CategoryToolTipGenerator {

	private String toolTip;

	ToolTipGenerator(String toolTip) {
		this.toolTip = toolTip;
	}

	@Override
	public String generateToolTip(CategoryDataset arg0, int arg1, int arg2) {
		return toolTip;
	}

}
