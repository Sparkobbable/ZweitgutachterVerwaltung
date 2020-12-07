package view.tableModels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Objects;

import javax.swing.JLabel;
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
		this.stackedChart = StackedBarChart.createStackedBarChart("Erstgutachten", "Zweitgutachten");
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Double firstBar = ((Double[]) value)[0];
		Double secBar = ((Double[]) value)[1];
		Double occupation = ((Double[]) value)[2];
		int firstBarInt;
		int secBarInt;
		int occupationInt;
		if (Objects.isNull(firstBar) || Objects.isNull(secBar)) {
			return new JLabel("   ----   ");
		} else {
			firstBarInt = (int) (firstBar * 100);
			secBarInt = (int) (secBar * 100);
			occupationInt = (int) (occupation * 100);
		}
		this.stackedChart.setValue(firstBarInt, secBarInt);
	    ChartPanel chartPanel = new ChartPanel(this.stackedChart.getChart());
	    chartPanel.setLayout(new BorderLayout());
	    chartPanel.add(new JLabel(occupationInt + "%   "), BorderLayout.EAST);
	    chartPanel.setBackground(Color.WHITE);
		return chartPanel;
	}

}
