package view.panels.overview;

import static view.tableModels.ThesesOverviewTableModel.AUTHOR_NAME;
import static view.tableModels.ThesesOverviewTableModel.AUTHOR_STUDY_GROUP;
import static view.tableModels.ThesesOverviewTableModel.FIRST_REVIEWER;
import static view.tableModels.ThesesOverviewTableModel.SECOND_REVIEWER;
import static view.tableModels.ThesesOverviewTableModel.SECOND_REVIEWER_STATUS;
import static view.tableModels.ThesesOverviewTableModel.TOPIC;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import controller.events.EventSource;
import controller.search.BachelorThesisSearchStrategy;
import controller.search.SearchStrategy;
import model.Model;
import model.domain.BachelorThesis;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.TableClickEventSource;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.Column;
import view.tableModels.ThesesOverviewTableModel;

public class ThesesOverviewPanel extends OverviewPanel<BachelorThesis> {

	private static final List<Column<BachelorThesis, ?>> THESES_TABLE_COLUMNS = List.of(AUTHOR_NAME, AUTHOR_STUDY_GROUP,
			TOPIC, FIRST_REVIEWER, SECOND_REVIEWER, SECOND_REVIEWER_STATUS);
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param model The model that shall be visualized
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
				new TableClickEventSource(EventId.NAVIGATE, this.table, 1, this::getNavigationTarget)));
	}

	private void updateTheses(List<BachelorThesis> oldValue, List<BachelorThesis> newValue) {
		this.stopObserving(oldValue);
		this.observe(newValue);
		this.updateTableModel();
	}

	private Optional<?> getNavigationTarget() {
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
