package view.tableModels;

import java.util.List;
import java.util.Optional;

import javax.swing.table.AbstractTableModel;

import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ReviewStatus;

public class SupervisedThesisTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Optional<Reviewer> selectedReviewer;
	
	/**
	 * Creates a TableModel of assigned bachelorThesis.
	 * @param supervisedThesis Needs the list of by the reviewer supervised thesis.
	 */
	public SupervisedThesisTableModel(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
	}
	
	@Override
	public int getRowCount() {
		return this.selectedReviewer.map(reviewer -> reviewer.getSupervisedThesis().size()).orElse(0);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Titel";
		case 1:
			return "Autor";
		case 2:
			return "Status";
		default:
			return null;
		}
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		if (this.selectedReviewer.isEmpty()) {
			return null;
		}
		BachelorThesis bachelorThesis = this.selectedReviewer.get().getSupervisedThesis().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return bachelorThesis.getTopic();
		case 1:
			return bachelorThesis.getAuthor().getName();
		case 2:
			List<BachelorThesis> secReviewedTheses = this.selectedReviewer.get().getSecReviewedTheses();
			int idx = secReviewedTheses.indexOf(bachelorThesis);
			if (idx == -1) {
				return "Erstgutachten";
			}
			return secReviewedTheses.get(idx).getSecondReview().get().getStatus().equals(ReviewStatus.REQUESTED) ? "Angefragt" : "Bestätigt";
		default: 
			return null;
		}
	}

	public void setSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
	}


}
