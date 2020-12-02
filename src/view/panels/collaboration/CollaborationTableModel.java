package view.panels.collaboration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

public class CollaborationTableModel extends DefaultPanel {
	
	private Model model;
	
	private String[] columnNames = {"Gutachter", "Anzahl gemeinsamer Bachelorarbeiten"};
	private Object[][] data;
	private JTable table;
	
	public CollaborationTableModel(Model model) {
		super("");
		this.model = model;
		this.data = new Object[30][2];
		
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		
		this.createUIElements();
	}
	
	private void createDataset() {
		for(int i = 0; i < this.data.length; i++) {
			this.data[i][0] = null;
			this.data[i][1] = null;
		}
		Map<Reviewer, Double> reviewers = this.model.getCollaboratingReviewers();
		int i = 0;
		for (Entry<Reviewer, Double> reviewer : reviewers.entrySet()) {
			this.data[i][0] = reviewer.getKey();
			this.data[i][1] = reviewer.getValue().intValue();
			i++;
			System.out.println("Schleife: " + i);
		}
		this.repaint();
		
	}
	
	private void createUIElements() {
		this.table = new JTable(data, columnNames);
		JScrollPane sp = new JScrollPane(table);
		this.add(sp);
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

