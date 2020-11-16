package view.overview;

import java.awt.BorderLayout;

import model.Model;
import model.data.BachelorThesis;
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
	public ThesesOverviewPanel(Model model) {
		super(model, "Bachelorthesis-Übersicht");
		this.actionPanel = new ThesesOverviewActionPanel(() -> getSelectedRowIndex());

		this.observe(this.model);

		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.tableModel.updateData();
	}
	
	@Override
	protected AbstractDataTableModel<BachelorThesis> createTableModel() {
		return new ThesesOverviewTableModel(model);
	}
}
