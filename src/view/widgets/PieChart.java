package view.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.DefaultPieDataset;

import controller.events.EventSource;
import model.Model;
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
		List<Reviewer> reviewers = this.model.getAnalyseReviewers();
		int reviewcount = 0;
		for(Reviewer reviewer : reviewers) {
			reviewcount += reviewer.getTotalReviewCount();
		}
		
		for(Reviewer reviewer : reviewers) {
			if(reviewer.getTotalReviewCount() > (reviewcount * 0.05)) {
				this.dataset.setValue(reviewer.getName(), reviewer.getTotalReviewCount());
			} else {
				try {
					double count = (double) this.dataset.getValue("Sonstige");
					this.dataset.setValue("Sonstige", count + reviewer.getTotalReviewCount());
				} catch(UnknownKeyException e) {
					this.dataset.setValue("Sonstige", reviewer.getTotalReviewCount());
				}
			}
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
	}
}
