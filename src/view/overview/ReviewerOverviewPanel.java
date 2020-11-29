package view.overview;

import static view.tableModels.ReviewerOverviewTableModel.NAME;
import static view.tableModels.ReviewerOverviewTableModel.OCCUPATION;
import static view.tableModels.ReviewerOverviewTableModel.THESIS_COUNT;

import java.util.List;

import controller.search.ReviewerSearchStrategy;
import controller.search.SearchStrategy;
import model.Model;
import model.domain.Reviewer;
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

		this.tableModel.updateData();

	}

	@Override
	protected AbstractDataTableModel<Reviewer> createTableModel() {
		return new ReviewerOverviewTableModel(List.of(NAME, THESIS_COUNT, OCCUPATION),
				List.of(r -> this.searchField.matchesSearch(r)), model);
	}

	@Override
	protected void createUIElements() {
		super.createUIElements();
		this.table.getColumnModel().getColumn(2).setCellRenderer(new DividedProgressRenderer());
	}

	@Override
	protected SearchStrategy<Reviewer> createSearchStrategy() {
		return new ReviewerSearchStrategy();
	}

}
