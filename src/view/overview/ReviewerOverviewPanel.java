package view.overview;

import java.util.List;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.SearchFieldEventSource;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.DividedProgressRenderer;
import view.tableModels.ReviewerOverviewTableModel;

public class ReviewerOverviewPanel extends OverviewPanel<Reviewer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param model Needs the model as the data access
	 */
	public ReviewerOverviewPanel(Model model) {
		super(model, "Dozentenübersicht");
		this.actionPanel = new ReviewerOverviewActionPanel(() -> getSelectedElements());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		
		this.observe(this.model);
		
		this.onPropertyChange(Model.REVIEWERS, (evt) -> updateTableModel());
		this.onPropertyChange(Model.DISPLAYED_REVIEWERS, (evt) -> updateTableModel());
		
		this.tableModel.updateData();

	}

	protected AbstractDataTableModel<Reviewer> createTableModel() {
		return new ReviewerOverviewTableModel(model);
	}

	@Override
	protected List<EventSource> getEventSources() {
		List<EventSource> eventSources = super.getEventSources();
		eventSources.add(new SearchFieldEventSource(EventId.SEARCH_OVERVIEW_REVIEWER, this.searchField));
		return eventSources;
	}

	@Override
	protected void createUIElements() {
		super.createUIElements();
		this.table.getColumnModel().getColumn(2).setCellRenderer(new DividedProgressRenderer());
	}

}
