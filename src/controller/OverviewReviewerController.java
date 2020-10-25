package controller;

import java.util.logging.Logger;

import model.Reviewer;
import model.ReviewerList;
import model.constants.ViewID;
import view.editor.ReviewerEditor;
import view.table.OverviewTable;
import view.table.ReviewerOverviewTableModel;

public class OverviewReviewerController extends AbstractSubController {
	private OverviewTable overviewTablePanel;
	private ReviewerList data;
	
	public OverviewReviewerController(OverviewTable overviewTablePanel, ReviewerList data, Controller mainController) {
		super(mainController);
		this.overviewTablePanel = overviewTablePanel;
		this.data = data;
	}

	public void deleteEntry() {
		int rowIdx = this.overviewTablePanel.getReviewerOverviewTable().getSelectedRow();
		this.data.removeIdx(rowIdx);
	}

	/**
	 * Opens a view to give the user the possibility to edit the reviewer
	 */
	void switchToEdit() {
		int selectedIdx = this.overviewTablePanel.getReviewerOverviewTable().getSelectedRow();
		String reviewerName = (String) this.overviewTablePanel.getReviewerOverviewTable().getValueAt(selectedIdx, ReviewerOverviewTableModel.REVIEWER_COLUMN);
		Reviewer reviewer = this.data.findReviewerByName(reviewerName);
		Logger.getLogger(Controller.class.getName()).info(String.format("Starting editmode on reviewer %s", reviewer.getName()));
		
		ReviewerEditor editor = new ReviewerEditor(ViewID.EDITOR.getViewID(), "Editor", reviewer);
		mainController.window.registerView(editor);
		
		mainController.reviewerEditorController = new ReviewerEditorController(mainController);
		
		mainController.switchToView(editor);
	}
}
