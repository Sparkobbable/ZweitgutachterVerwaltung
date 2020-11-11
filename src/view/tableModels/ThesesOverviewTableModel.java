package view.tableModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.data.BachelorThesis;

public class ThesesOverviewTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<BachelorThesis> thesisList;
	
	/**
	 * Creates a TableModel of the bachelorThesis-list
	 * 
	 * @param thesisList Needs the list of bachelorThesis to be shown
	 */
	public ThesesOverviewTableModel(List<BachelorThesis> thesisList) {
		this.thesisList = thesisList;
	}
	
	@Override
	public int getRowCount() {
		return thesisList.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0: 
				return "Autor";
			case 1: 
				return "Thema";
			case 2: 
				return "Erstgutachter";
			default:
				return null;
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BachelorThesis thesis = this.thesisList.get(rowIndex);
		switch (columnIndex) {
			case 0: 
				return thesis.getAuthor().getName();
			case 1: 
				return thesis.getTopic();
			case 2: 
				return thesis.getFirstReview().get().getReviewer().getName();
			default:
				return null;
		}
	}

}
