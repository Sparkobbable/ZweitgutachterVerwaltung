package view.collaboration;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import controller.events.EventSource;
import controller.propertychangelistener.PropertyChangeManager;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Review;
import model.domain.Reviewer;
import view.panelstructure.DefaultPanel;

public class PieChart extends DefaultPanel{

	private Model model;
	
	private Optional<DefaultPieDataset> dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	
	private PropertyChangeManager propertyChangeManager;
	
	public PieChart(Model model) {
		super("");
		this.model = model;
		this.dataset = Optional.empty();
		this.propertyChangeManager = new PropertyChangeManager();
		
		this.initializePropertyChangeHandlers();
		this.createUIElements();
		this.observe(this.model);
	}
	
	private void createDataset() {
		this.dataset = Optional.of(new DefaultPieDataset());
		HashMap<Reviewer, Double> reviewers = this.model.getCollaboratingReviewers();
		for(Entry<Reviewer, Double>reviewer : reviewers.entrySet()) {
			System.out.println("View PieChart");
			this.dataset.get().setValue(reviewer.getKey().getName(), reviewer.getValue());
		} 
		createUIElements();
	}
	
	private void createUIElements() {
		if(this.dataset.isPresent()) {
			this.chart = ChartFactory.createPieChart("Zusammenarbeit mit Gutachtern", this.dataset.get(), true, true, false);
			this.panel = new ChartPanel(chart);
			this.add(this.panel);
		}
	}

	@Override
	protected List<EventSource> getEventSources() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	@SuppressWarnings("unchecked")
	private void initializePropertyChangeHandlers() {
		this.propertyChangeManager.onPropertyChange(Model.COLLABORATING_REVIEWERS, 
				(evt) -> createDataset());
	}
}
