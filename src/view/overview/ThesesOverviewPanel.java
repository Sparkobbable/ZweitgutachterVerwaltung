package view.overview;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Model;
import model.data.BachelorThesis;
import view.tableModels.ThesesOverviewTableModel;

public class ThesesOverviewPanel extends OverviewPanel {

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

		addObservables(model);

		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	
	@Override
	protected AbstractTableModel createTableModel() {
		return new ThesesOverviewTableModel(model, model.getSelectedReviewer());
	}
}
