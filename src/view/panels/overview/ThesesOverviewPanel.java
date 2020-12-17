package view.panels.overview;

import static view.tableModels.ThesesOverviewTableModel.AUTHOR_NAME;
import static view.tableModels.ThesesOverviewTableModel.AUTHOR_STUDY_GROUP;
import static view.tableModels.ThesesOverviewTableModel.FIRST_REVIEWER;
import static view.tableModels.ThesesOverviewTableModel.SECOND_REVIEWER;
import static view.tableModels.ThesesOverviewTableModel.SECOND_REVIEWER_STATUS;
import static view.tableModels.ThesesOverviewTableModel.TOPIC;
import static view.tableModels.ThesesOverviewTableModel.COMMENT;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import controller.events.EventSource;
import controller.search.BachelorThesisSearchStrategy;
import controller.search.SearchStrategy;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.TableClickEventSource;
import view.panels.editor.ReviewerEditorPanel;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.Column;
import view.tableModels.ThesesOverviewTableModel;

/**
 * {@link OverviewPanel} that is responsible for displaying an overview of a list of {@link BachelorThesis}.
 * <p>
 * It is responsible for searching bachelortheses as well as adding {@link BachelorThesis} as a {@link SecondReview} to a {@link Reviewer}
 */
public class ThesesOverviewPanel extends OverviewPanel<BachelorThesis> {

	private static final List<Column<BachelorThesis, ?>> THESES_TABLE_COLUMNS = List.of(AUTHOR_NAME, AUTHOR_STUDY_GROUP,
			TOPIC, COMMENT, FIRST_REVIEWER, SECOND_REVIEWER, SECOND_REVIEWER_STATUS);
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the {@link BachelorThesis} without a
	 * {@link SecondReview} and other data of the {@link BachelorThesis}
	 * 
	 * @param model The {@link Model} that shall be visualized
	 */
	@SuppressWarnings("unchecked")
	public ThesesOverviewPanel(Model model) {
		super(model, "Bachelorthesis-Übersicht");
		this.actionPanel = new ThesesOverviewActionPanel(this.model, () -> getSelectedElements());

		this.observe(this.model);
		this.observe(this.model.getTheses());

		this.setLayout(new BorderLayout());
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.onPropertyChange(Model.THESES, (evt) -> updateTheses((List<BachelorThesis>) evt.getOldValue(),
				(List<BachelorThesis>) evt.getNewValue()));
		this.onPropertyChange(BachelorThesis.SECOND_REVIEW, (evt) -> updateTableModel());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.tableModel.updateData();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return new LinkedList<EventSource>(List.of(this.actionPanel,
				new TableClickEventSource(EventId.NAVIGATE, this.table, 2, this::getNavigationTarget)));
	}

	private void updateTheses(List<BachelorThesis> oldValue, List<BachelorThesis> newValue) {
		this.stopObserving(oldValue);
		this.observe(newValue);
		this.updateTableModel();
	}

	private Optional<?> getNavigationTarget() {
		if (this.table.getSelectedColumn() == -1 || this.table.getSelectedRow() == -1) {
			return Optional.empty();
		}
		int selectedColumn = this.table.convertColumnIndexToModel(this.table.getSelectedColumn());
		int selectedRow = this.table.convertRowIndexToModel(this.table.getSelectedRow());
		return this.tableModel.getReferencedAt(selectedRow, selectedColumn);
	}

	@Override
	protected AbstractDataTableModel<BachelorThesis> createTableModel() {
		return new ThesesOverviewTableModel(THESES_TABLE_COLUMNS, List.of(t -> this.searchField.matchesSearch(t)),
				model);
	}

	@Override
	protected SearchStrategy<BachelorThesis> createSearchStrategy() {
		return new BachelorThesisSearchStrategy();
	}

}
