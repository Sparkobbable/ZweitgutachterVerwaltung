package view.widgets;

import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

public class BarChart extends DefaultPanel {

	private Model model;
	private String title;
	
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	
	public BarChart(String title, Model model) {
		super("");
		this.model = model;
		this.title = title;
		this.dataset = new DefaultCategoryDataset();
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		
		this.createUIElements();
	}
	
	private void createDataset() {
		this.dataset.clear();
		List<Reviewer> reviewers = this.model.getAnalyseReviewers();
		
		for(Reviewer reviewer : reviewers) {
			this.dataset.addValue(reviewer.getFirstReviewCount(), "Erstgutachten", reviewer.getName());
			this.dataset.addValue(reviewer.getSecondReviewCount(), "Zweitgutachten", reviewer.getName());
		}
	}
	
	private void createUIElements() {
		this.chart = ChartFactory.createBarChart(this.title, 
				"Gutachter", 
				"Anzahl Gutachten", 
				this.dataset);
		this.panel = new ChartPanel(chart);
		this.panel.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.add(panel);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return Collections.emptyList();
	}
	
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.ANALYSE_REVIEWERS, (evt) -> createDataset());
	}

}