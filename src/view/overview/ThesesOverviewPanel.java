package view.overview;

import java.awt.BorderLayout;
import java.util.List;

import controller.events.EventSource;
import model.Model;
import model.domain.BachelorThesis;
import model.enums.EventId;
import view.View;
import view.eventsources.SearchFieldEventSource;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.ThesesOverviewTableModel;

public class ThesesOverviewPanel extends OverviewPanel<BachelorThesis> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as data access
	 */
	@SuppressWarnings("unchecked")
	public ThesesOverviewPanel(Model model) {
		super(model, "Bachelorthesis-Übersicht");
		this.actionPanel = new ThesesOverviewActionPanel(this.model, () -> getSelectedRowIndex());

		this.observe(this.model);
		this.observe(this.model.getTheses());

		this.setLayout(new BorderLayout());
		this.setBackground(View.background);

		this.onPropertyChange(Model.THESES, (evt) -> updateTheses((List<BachelorThesis>) evt.getOldValue(),
				(List<BachelorThesis>) evt.getNewValue()));
		this.onPropertyChange(BachelorThesis.SECOND_REVIEW, (evt) -> updateTableModel());
		this.onPropertyChange(Model.DISPLAYED_THESES, (evt) -> updateTableModel());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.tableModel.updateData();
	}

	private void updateTheses(List<BachelorThesis> oldValue, List<BachelorThesis> newValue) {
		this.stopObserving(oldValue);
		this.observe(newValue);
		this.updateTableModel();
	}

	@Override
	protected List<EventSource> getEventSources() {
		List<EventSource> eventSources = super.getEventSources();
		eventSources.add(new SearchFieldEventSource(EventId.SEARCH_OVERVIEW_THESES, this.searchField));
		return eventSources;
	}
	
	@Override
	protected AbstractDataTableModel<BachelorThesis> createTableModel() {
		return new ThesesOverviewTableModel(model);
	}

}
