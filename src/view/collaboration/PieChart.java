package view.collaboration;

import java.util.Collections;
//github.com/Sparkobbable/ZweitgutachterVerwaltung.git
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panelstructure.DefaultPanel;

public class PieChart extends DefaultPanel {

	private Model model;

	private DefaultPieDataset dataset;
	private JFreeChart chart;
	private ChartPanel panel;

	public PieChart(Model model) {
		super("");
		this.model = model;
		this.dataset = new DefaultPieDataset();
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		
		this.createUIElements();
	}

	private void createDataset() {
		this.dataset.clear();
		Map<Reviewer, Double> reviewers = this.model.getCollaboratingReviewers();
		for (Entry<Reviewer, Double> reviewer : reviewers.entrySet()) {
			this.dataset.setValue(reviewer.getKey().getName(), reviewer.getValue());
		}
	}

	private void createUIElements() {
			this.chart = ChartFactory.createPieChart("Zusammenarbeit mit Gutachtern", this.dataset, true, true,
					false);
			this.panel = new ChartPanel(chart);
			this.panel.setBackground(ViewProperties.BACKGROUND_COLOR);
			this.add(this.panel);
	}

	@Override
	protected List<EventSource> getEventSources() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.COLLABORATING_REVIEWERS, (evt) -> createDataset());
	}
}
