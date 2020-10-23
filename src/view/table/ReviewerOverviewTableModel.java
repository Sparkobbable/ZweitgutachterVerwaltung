package view.table;

import javax.swing.table.AbstractTableModel;

import model.CurrentData;
import model.Reviewer;

public class ReviewerOverviewTableModel extends AbstractTableModel{
	private CurrentData data;
	
	/**
	 * Creates a TableModel of the Reviewerlist
	 * @param data Needs the DataAccess of the Application
	 */
	public ReviewerOverviewTableModel(CurrentData data) {
		this.data = data;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0 : 
			return "Name";
		case 1 :
			return "Anzahl betreute Bachelorarbeiten";
		default :
			return null;
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.getReviewer().size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reviewer reviewer = data.getReviewer().get(rowIndex);
		switch (columnIndex) {
		case 0 :
			return reviewer.getName();
		case 1 :
			return reviewer.getSupervisedThesis().size();
		default :
			return null;
		}
	}
	
}
