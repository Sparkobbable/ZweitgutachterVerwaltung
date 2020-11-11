package view.overview;

import model.Model;
import model.enums.ViewId;
import view.tableModels.ProgressRenderer;
import view.tableModels.ReviewerOverviewTableModel;

public class ReviewerOverviewPanel extends OverviewPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as the data access
	 */
	public ReviewerOverviewPanel(ViewId viewId, Model model) {
		super(viewId, model, "Dozentenübersicht");
		this.actions = new ReviewerOverviewActionPanel(ViewId.ACTIONS, () -> getSelectedRowIndex());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();
		this.addObservables(this.model);
	}

	protected ReviewerOverviewTableModel createTableModel() {
		return new ReviewerOverviewTableModel(model);
	}
	
	@Override
	protected void createUIElements() {
		super.createUIElements();
		this.table.getColumnModel().getColumn(2).setCellRenderer(new ProgressRenderer());
	}

}
