package view.editor;

import static view.tableModels.ThesesOverviewTableModel.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.EventSource;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.eventsources.SearchFieldEventSource;
import view.panelstructure.DefaultPanel;
import view.tableModels.AbstractTableModel.Column;
import view.widgets.SearchField;
import view.tableModels.ThesesOverviewTableModel;

public class ThesisAssignmentPanel extends DefaultPanel {

	//Constants
	private static final long serialVersionUID = 1L;
	private static final List<Column<BachelorThesis, ?>> THESES_TABLE_COLUMNS = List.of(AUTHOR_NAME, AUTHOR_STUDY_GROUP,
			TOPIC, FIRST_REVIEWER);
	
	//Data
	private Reviewer selectedReviewer;
	private Model model;

	//UI-Elements
	private JTable thesisTable;
	private JScrollPane thesisScrollPane;
	private JButton addThesis;
	private ThesesOverviewTableModel thesesTableModel;
	private SearchField searchField;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as data access
	 */
	public ThesisAssignmentPanel(Model model) {
		super("Bachelorthesis-Editor");
		this.model = model;
		this.observe(model);

		this.setBackground(Color.GRAY); // TODO only for component identification, remove before launch
		this.setLayout(new BorderLayout(10,10));

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();

		this.updateSelectedReviewer(model.getSelectedReviewer());

	}

	@Override
	protected List<EventSource> getEventSources() {
		return List
				.of(new ButtonEventSource(EventId.ADD_THESIS_TO_REVIEWER, this.addThesis, () -> getSelectedTheses()),
					new SearchFieldEventSource(EventId.SEARCH_THESIS, this.searchField));
	}

	private List<BachelorThesis> getSelectedTheses() {
		return IntStream.of(this.thesisTable.getSelectedRows()).map(this.thesisTable::convertRowIndexToModel)
				.mapToObj(this.thesesTableModel::getByIndex).collect(Collectors.toList());
	}

	private void createUIElements() {
		this.thesesTableModel = new ThesesOverviewTableModel(THESES_TABLE_COLUMNS, this.getThesisTableFilters(),
				this.model);
		this.thesisTable = new JTable(this.thesesTableModel);
		this.thesisTable.setAutoCreateRowSorter(true);
		this.thesisScrollPane = new JScrollPane(this.thesisTable);
		this.addThesis = new JButton();
		this.searchField = new SearchField();
	}

	private List<Predicate<BachelorThesis>> getThesisTableFilters() {
		return List.of(t -> t.getSecondReview().isEmpty(), t -> !this.selectedReviewer.reviewsThesis(t));
	}

	protected String createButtonText() {
		return String.format("Zweitgutachten %s zuordnen", this.selectedReviewer.getName());
	}

	private void addUIElements() {
		this.add(this.searchField, BorderLayout.PAGE_START);
		this.add(this.thesisScrollPane, BorderLayout.CENTER);
		this.add(this.addThesis, BorderLayout.PAGE_END);
	}

	@SuppressWarnings("unchecked")
	private void initializePropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
		this.onPropertyChange(Reviewer.SECOND_REVIEWS,
				(evt) -> updateThesesList((ArrayList<BachelorThesis>) evt.getNewValue()));
		this.onPropertyChange(Reviewer.FIRST_REVIEWS,
				(evt) -> updateThesesList((ArrayList<BachelorThesis>) evt.getNewValue()));
		this.onPropertyChange(Model.DISPLAYED_THESES, (evt) -> updateSearchedThesesList((ArrayList<BachelorThesis>) evt.getNewValue()));
	}

	private void updateSearchedThesesList(ArrayList<BachelorThesis> newValue) {
		if (Objects.isNull(this.selectedReviewer)) {
			return;
		}
		updateThesesList(newValue);
	}

	private void updateThesesList(ArrayList<BachelorThesis> updatedThesisList) {
		this.thesesTableModel.updateData();
		this.repaint();
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		if (selectedReviewer.isPresent()) {
			this.selectedReviewer = selectedReviewer.get();
			this.observe(this.selectedReviewer);
			this.addThesis.setText(this.createButtonText());
			this.thesesTableModel.updateData();
			this.repaint();
		} else {
			this.selectedReviewer = null;
		}
	}

}
