package view.tableModels;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartPanel;

import view.widgets.StackedBarChart;

public class DividedProgressRenderer implements TableCellRenderer {
	
	private StackedBarChart stackedChart;
	
	/**
	 * Creates a Cell-Renderer which displays a horizontal stacked bar chart
	 */
	public DividedProgressRenderer() {
		this.stackedChart = StackedBarChart.createStackedBarChart("", "");
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.stackedChart.setValue(((Integer[]) value)[0], ((Integer[]) value)[1]);
	    ChartPanel chartPanel = new ChartPanel(this.stackedChart.getChart());
	    chartPanel.setBackground(Color.WHITE);
		return chartPanel;
	}

}
