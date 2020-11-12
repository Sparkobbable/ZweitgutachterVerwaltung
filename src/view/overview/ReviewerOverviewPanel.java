package view.overview;

import model.Model;
import view.tableModels.ProgressRenderer;
import view.tableModels.ReviewerOverviewTableModel;

public class ReviewerOverviewPanel extends OverviewPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param model  Needs the model as the data access
	 */
	public ReviewerOverviewPanel(Model model) {
		super(model, "Dozenten�bersicht");
		this.actionPanel = new ReviewerOverviewActionPanel(() -> getSelectedRowIndex());

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