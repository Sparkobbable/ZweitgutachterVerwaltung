package view.table;

import javax.swing.table.AbstractTableModel;

import model.Model;
import model.data.Reviewer;

public class ReviewerOverviewTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Model data;
	
	public static final int REVIEWER_COLUMN = 0;

	/**
	 * Creates a TableModel of the Reviewerlist
	 * 
	 * @param data Needs the DataAccess of the Application
	 */
	public ReviewerOverviewTableModel(Model data) {
		this.data = data;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case REVIEWER_COLUMN:
			return "Name";
		case 1:
			return "Anzahl betreute Bachelorarbeiten";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return data.getReviewer().size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reviewer reviewer = data.getReviewer().get(rowIndex);
		switch (columnIndex) {
		case REVIEWER_COLUMN:
			return reviewer.getName();
		case 1:
			return reviewer.getSupervisedThesis().size();
		default:
			return null;
		}
	}

}
