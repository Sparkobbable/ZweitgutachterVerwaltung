package view.overview;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Model;
import model.data.BachelorThesis;
import model.enums.ViewId;
import view.tableModels.ThesesOverviewTableModel;

public class ThesesOverviewPanel extends OverviewPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<BachelorThesis> thesisList;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a
	 * second review and other data of the thesis
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as data access
	 */
	public ThesesOverviewPanel(ViewId viewId, Model model) {
		super(viewId, model, "Bachelorthesis-Übersicht");
		this.actionPanel = new ThesesOverviewActionPanel(ViewId.ACTIONS, () -> getSelectedRowIndex());
		this.thesisList = model.getThesisMissingSecReview();

		addObservables(model);

		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	
	@Override
	protected AbstractTableModel createTableModel() {
		return new ThesesOverviewTableModel(this.thesisList);
	}
}
