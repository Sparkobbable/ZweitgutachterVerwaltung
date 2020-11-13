package view.tableModels;

import java.util.Optional;

import javax.swing.table.AbstractTableModel;

import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import model.data.SecondReview;
import model.enums.ReviewType;

public class SupervisedThesisTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Optional<Reviewer> selectedReviewer;

	/**
	 * Creates a TableModel of assigned bachelorThesis.
	 * 
	 * @param supervisedThesis Needs the list of by the reviewer supervised thesis.
	 */
	public SupervisedThesisTableModel(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
	}

	@Override
	public int getRowCount() {
		return this.selectedReviewer.map(Reviewer::getSupervisedThesesSize).orElse(0);
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
		Review review = getReviewerByIndex(rowIndex);
		BachelorThesis bachelorThesis = review.getBachelorThesis();
		switch (columnIndex) {
		case 0:
			return bachelorThesis.getTopic();
		case 1:
			return bachelorThesis.getAuthor().getName();
		case 2:
			return review.getReviewType() == ReviewType.SECOND_REVIEW ? ((SecondReview) review).getStatus().getLabel()
					: null;
		default:
			return null;
		}
	}

	public void setSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
	}
	
	public Review getReviewerByIndex(int rowIndex) {
		return this.selectedReviewer.get().getSupervisedTheses().get(rowIndex);
	}

}
