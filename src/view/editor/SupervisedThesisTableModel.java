package view.editor;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.data.BachelorThesis;

public class SupervisedThesisTableModel extends AbstractTableModel {
	private ArrayList<BachelorThesis> supervisedThesis;
	
	/**
	 * Creates a TableModel of assigned bachelorThesis.
	 * @param supervisedThesis Needs the list of by the reviewer supervised thesis.
	 */
	public SupervisedThesisTableModel(ArrayList<BachelorThesis> supervisedThesis) {
		this.supervisedThesis = supervisedThesis;
	}
	
	@Override
	public int getRowCount() {
		return this.supervisedThesis.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Titel";
		case 1:
			return "Autor";
		default:
			return null;
		}
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		BachelorThesis bachelorThesis = this.supervisedThesis.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return bachelorThesis.getTopic();
		case 1:
			return bachelorThesis.getAuthor().getName();
		default: 
			return null;
		}
	}


}
