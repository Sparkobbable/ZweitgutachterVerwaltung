package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EventSource;
import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.EventId;

import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultViewPanel;
import view.tableModels.ThesesOverviewTableModel;

public class ThesisAssignmentPanel extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<BachelorThesis> thesisList;
	private Optional<Reviewer> selectedReviewer;

	private JTable thesisTable;
	private JScrollPane thesisScrollPane;
	private JButton addThesis;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as data access
	 */
	public ThesisAssignmentPanel(Model model) {
		super("Bachelorthesis-Editor");
		this.thesisList = model.getThesisMissingSecReview();
		this.selectedReviewer = model.getSelectedReviewer();

		addObservables(model);

		this.setBackground(Color.DARK_GRAY); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(4, 1));

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.ADD_THESIS_TO_REVIEWER, this.addThesis, () -> getThesis()));
	}

	private int[] getThesis() {
		return this.thesisTable.getSelectedRows();
	}

	private void createUIElements() {
		this.thesisTable = new JTable(new ThesesOverviewTableModel(this.thesisList));
		this.thesisScrollPane = new JScrollPane(this.thesisTable);
		this.addThesis = new JButton(this.createButtonText());
	}

	protected String createButtonText() {
		return String.format("Zweitgutachten %s zuordnen",
				this.selectedReviewer.map(reviewer -> reviewer.getName()).orElse("X"));
	}

	private void addUIElements() {
		this.add(this.thesisScrollPane);
		this.add(this.addThesis);
	}

	@SuppressWarnings("unchecked")
	private void initializePropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		this.addThesis.setText(this.createButtonText());
		this.repaint();
	}

}
