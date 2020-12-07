package view.panels.editor;

import static view.tableModels.ThesesOverviewTableModel.AUTHOR_NAME;
import static view.tableModels.ThesesOverviewTableModel.AUTHOR_STUDY_GROUP;
import static view.tableModels.ThesesOverviewTableModel.FIRST_REVIEWER;
import static view.tableModels.ThesesOverviewTableModel.TOPIC;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.events.EventSource;
import controller.search.BachelorThesisSearchStrategy;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.Column;
import view.tableModels.ThesesOverviewTableModel;
import view.widgets.SearchField;

/**
 * Panel responsible for assigning a thesis to the selectedReviwer
 * <p>
 * It is required that a reviewer is currently selected when opening this panel.
 * This panel's content updates automatically when the selected reviewer or any
 * of its theses changes.
 *
 */
@SuppressWarnings("serial") // should not be serialized
public class ThesesAssignmentPanel extends DefaultPanel {

	// Constants
	/**
	 * List of Columns displayed in this panel's table
	 */
	private static final List<Column<BachelorThesis, ?>> THESES_TABLE_COLUMNS = List.of(AUTHOR_NAME, AUTHOR_STUDY_GROUP,
			TOPIC, FIRST_REVIEWER);

	// Data
	private Reviewer selectedReviewer;
	private Model model;

	// UI-Elements
	private JTable thesesTable;
	private JScrollPane thesisScrollPane;
	private ThesesAssignmentActionPanel actionPanel;
	private ThesesOverviewTableModel thesesTableModel;
	private SearchField<BachelorThesis> searchField;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param model The presented Model, needs for data access
	 */
	public ThesesAssignmentPanel(Model model) {
		super("Bachelorthesis-Editor");
		this.model = model;
		this.observe(model);

		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new BorderLayout(10, 10));

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();

		this.updateSelectedReviewer(model.getSelectedReviewer());

	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.actionPanel);
	}

	private List<BachelorThesis> getSelectedTheses() {
		return IntStream.of(this.thesesTable.getSelectedRows()).map(this.thesesTable::convertRowIndexToModel)
				.mapToObj(this.thesesTableModel::getByIndex).collect(Collectors.toList());
	}

	private void createUIElements() {
		this.thesesTableModel = new ThesesOverviewTableModel(THESES_TABLE_COLUMNS, this.getThesisTableFilters(),
				this.model);
		this.thesesTable = new JTable(this.thesesTableModel);
		this.thesesTable.setAutoCreateRowSorter(true);
		this.thesesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ThesesAssignmentPanel.this.actionPanel.setTextForReviewer(ThesesAssignmentPanel.this.selectedReviewer);
			}
		});
		this.thesisScrollPane = new JScrollPane(this.thesesTable);
		this.searchField = new SearchField<>(new BachelorThesisSearchStrategy(), (t) -> this.updateThesesList());
		this.actionPanel = new ThesesAssignmentActionPanel(() -> getSelectedTheses());
	}

	private List<Predicate<BachelorThesis>> getThesisTableFilters() {
		return List.of(t -> t.getSecondReview().isEmpty(), t -> !this.selectedReviewer.reviewsThesis(t),
				t -> this.searchField.matchesSearch(t));
	}

	private void addUIElements() {
		this.add(this.searchField, BorderLayout.PAGE_START);
		this.add(this.thesisScrollPane, BorderLayout.CENTER);
		this.add(this.actionPanel, BorderLayout.PAGE_END);
	}

	@SuppressWarnings("unchecked")
	private void initializePropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
		this.onPropertyChange(Reviewer.SECOND_REVIEWS, (evt) -> updateThesesList());
		this.onPropertyChange(Reviewer.FIRST_REVIEWS, (evt) -> updateThesesList());

	}

	private void updateThesesList() {
		if (Objects.isNull(this.selectedReviewer)) {
			return;
		}
		this.thesesTableModel.updateData();
		this.repaint();
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		if (selectedReviewer.isPresent()) {
			if (Objects.nonNull(this.selectedReviewer)) {
				this.stopObserving(this.selectedReviewer);
			}
			this.selectedReviewer = selectedReviewer.get();
			this.observe(this.selectedReviewer);
			this.actionPanel.setTextForReviewer(this.selectedReviewer);
			this.thesesTableModel.updateData();
			this.repaint();
		} else {
			this.selectedReviewer = null;
		}
	}

}
