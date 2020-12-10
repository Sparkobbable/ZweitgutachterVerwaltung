package view.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.events.EventSource;
import model.Model;
import model.Pair;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

public class BarChartHorizontal extends DefaultPanel{
	
	private static final long serialVersionUID = 1L;
	
	private Model model;
	private String title;
 
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel panel;
 
	 public BarChartHorizontal(String title, Model model) {
		 super("");
		 this.model =model;
		 this.title = title;
		 this.dataset = new DefaultCategoryDataset();
		 this.initializePropertyChangeHandlers();
		 this.observe(this.model);
		 this.setBackground(ViewProperties.BACKGROUND_COLOR);
		 
		 this.createUIElements();
	 }
	 
	private void createDataset() {
		this.dataset.clear();
		Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> reviewers = this.model.getAnalyseReviewers();
		
		for(Entry<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> reviewer : reviewers.entrySet()) {
			if(reviewer.getValue().getLeft().isPresent()) {
				this.dataset.addValue(reviewer.getValue().getLeft().get(), reviewer.getKey().getName(), "Erstgutachten");
			}
			if(reviewer.getValue().getRight().isPresent()) {
				this.dataset.addValue(reviewer.getValue().getRight().get(), reviewer.getKey().getName(), "Zweitgutachten");
			}
		}
	}
	
	private void createUIElements() {
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
		this.chart =ChartFactory.createStackedBarChart(this.title, "Gutachten", "Anzahl Gutachten", this.dataset, PlotOrientation.HORIZONTAL, true, true, false);
		this.panel = new ChartPanel(chart);
		this.panel.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.add(panel);
	}
	
	protected List<EventSource> getEventSources(){
		return Collections.emptyList();
	}
	
	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.ANALYSE_REVIEWERS, (evt) -> createDataset());
	}	
}
