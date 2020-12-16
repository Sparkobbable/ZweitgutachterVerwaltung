package view.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;

import org.jfree.chart.ChartPanel;

public class StackedBarChartWidget extends ChartPanel {

	private static final long serialVersionUID = 1L;
	private static final String FIRST_REVIEWERS_LABEL = "Erstgutachten";
	private static final String SECOND_REVIEWERS_LABEL = "Zweitgutachten";

	private StackedBarChart stackedBarChart;
	private JLabel label;

	public static StackedBarChartWidget getInstance() {
		StackedBarChart stackedBarChart = StackedBarChart.createStackedBarChart(FIRST_REVIEWERS_LABEL, SECOND_REVIEWERS_LABEL);
		stackedBarChart.setValue(20, 50);

		return new StackedBarChartWidget(stackedBarChart);
	}
	
	private StackedBarChartWidget(StackedBarChart stackedBarChart) {
		super(stackedBarChart.getChart());
		this.stackedBarChart = stackedBarChart;
		this.setLayout(new BorderLayout());
		this.label = new JLabel();
		this.add(this.label, BorderLayout.LINE_END);
		this.stackedBarChart.setValue(50, 5);
		this.setDomainZoomable(false);
		this.setRangeZoomable(false);
		this.setPreferredSize(new Dimension(200, 30));
		
	}

	public void update(int firstReviwers, int secondReviewers, int maxReviewers) {
		this.stackedBarChart.setValue(firstReviwers * 100. / maxReviewers, secondReviewers * 100. / maxReviewers);
		this.label.setText(String.format("%d / %d", firstReviwers + secondReviewers, maxReviewers));
	}

}
