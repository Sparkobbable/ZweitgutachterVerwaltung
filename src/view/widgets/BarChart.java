package view.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.events.EventSource;
import model.Model;
import model.Pair;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

public class BarChart extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
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
	
	private void createAnalysisDataset() {
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
	
	private void createSingleAnalysisDataset() {
		this.dataset.clear();
		Pair<Optional<Integer>, Optional<Integer>> reviews = this.model.getSingleReviews();
		try {
			if(reviews.getLeft().isPresent()) {
				this.dataset.addValue(reviews.getLeft().get(), this.model.getSelectedReviewer().orElseThrow().getName(),"Erstgutachten");
			}
			if(reviews.getRight().isPresent()) {
				this.dataset.addValue(reviews.getRight().get(), this.model.getSelectedReviewer().orElseThrow().getName(), "ZWeitgutachten");
			}
		} catch(NoSuchElementException e) {
			
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
		this.onPropertyChange(Model.ANALYSE_REVIEWERS, (evt) -> createAnalysisDataset());
		this.onPropertyChange(Model.SINGLE_REVIEWS, (evt) -> createSingleAnalysisDataset());
	}

}
