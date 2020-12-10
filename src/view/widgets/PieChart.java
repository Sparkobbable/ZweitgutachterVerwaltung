package view.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import controller.events.EventSource;
import model.Model;
import model.Pair;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

/**
 * Widget including PieChart presentation for analysis.
 */
@SuppressWarnings("serial") // should not be serialized
public class PieChart extends DefaultPanel {

	private Model model;
	private String title;

	private DefaultPieDataset dataset;
	private JFreeChart chart;
	private ChartPanel panel;

	public PieChart(String title,Model model) {
		super("");
		this.model = model;
		this.title = title;
		this.dataset = new DefaultPieDataset();
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		
		this.createUIElements();
	}

	private void createCallaborationDataset() {
		this.dataset.clear();
		Map<Reviewer, Double> reviewers = this.model.getCollaboratingReviewers();
		for (Entry<Reviewer, Double> reviewer : reviewers.entrySet()) {
			this.dataset.setValue(reviewer.getKey().getName(), reviewer.getValue());
		}
	}
	
	private void createAnalyseDataset() {
		this.dataset.clear();
		Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> reviewers = this.model.getAnalyseReviewers();
			for(Entry<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> reviewer : reviewers.entrySet()) {
				if(reviewer.getValue().getLeft().isPresent()) {
					if(reviewer.getValue().getRight().isPresent()) {
						int count = reviewer.getValue().getRight().get() + reviewer.getValue().getLeft().get();
						this.dataset.setValue(reviewer.getKey().getName(), count);
					} else {
						this.dataset.setValue(reviewer.getKey().getName(), reviewer.getValue().getLeft().get());
					}
					
				} else if(reviewer.getValue().getRight().isPresent()) {
					this.dataset.setValue(reviewer.getKey().getName(), reviewer.getValue().getRight().get());
				}
			}
	}
	
	private void createSingleAnalyseDataset() {
		this.dataset.clear();
		Pair<Optional<Integer>, Optional<Integer>> reviews = this.model.getSingleReviews();
		if(reviews.getLeft().isPresent()) {
			this.dataset.setValue("Erstgutachten", reviews.getLeft().get());
		}
		if(reviews.getRight().isPresent()) {
			this.dataset.setValue("Zweitgutachten", reviews.getRight().get());
		}
 	}

	private void createUIElements() {
			this.chart = ChartFactory.createPieChart(this.title, this.dataset, true, true,
					false);
			this.panel = new ChartPanel(chart);
			this.panel.setBackground(ViewProperties.BACKGROUND_COLOR);
			this.add(this.panel);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return Collections.emptyList();
	}

	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.COLLABORATING_REVIEWERS, (evt) -> createCallaborationDataset());
		this.onPropertyChange(Model.ANALYSE_REVIEWERS, (evt) -> createAnalyseDataset());
		this.onPropertyChange(Model.SINGLE_REVIEWS, (evt) -> createSingleAnalyseDataset());
	}
}
