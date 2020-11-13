package view.tableModels;

import java.util.List;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;

import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;

public class ThesesOverviewTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<BachelorThesis> thesisList;
	private Model model;
	private Optional<Reviewer> selectedReviewer;
	
	/**
	 * Creates a TableModel of the bachelorThesis-list
	 * 
	 * @param thesisList Needs the list of bachelorThesis to be shown
	 */
	public ThesesOverviewTableModel(Model model, Optional<Reviewer> selectedReviewer) {
		this.model = model;
		this.selectedReviewer = selectedReviewer;
		this.thesisList = model.getThesisMissingSecReview(selectedReviewer.map(reviewer -> reviewer.getSecReviewedTheses()));
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
				return thesis.getFirstReview().getReviewer().getName();
			default:
				return null;
		}
	}

	public void getNewData() {
		this.thesisList = model.getThesisMissingSecReview(selectedReviewer.map(reviewer -> reviewer.getSecReviewedTheses()));
	}

}
