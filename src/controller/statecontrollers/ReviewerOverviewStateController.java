package controller.statecontrollers;

import static model.enums.EventId.EDIT;

import java.util.logging.Logger;

import javax.swing.JTable;

import controller.Controller;
import controller.ReviewerEditorController;
import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.ViewId;
import view.View;
import view.editor.ReviewerEditor;
import view.table.OverviewTable;
import view.table.ReviewerOverviewTableModel;

/**
 * Handles the Application when in ApplicationState {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ReviewerOverviewStateController extends AbstractStateController{

	public ReviewerOverviewStateController(View view,
			ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.REVIEWER_OVERVIEW, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, () -> switchToEdit());
	}
	
	public void deleteEntry() {
		int rowIdx = ((OverviewTable) this.view.assumeState(state)).getReviewerOverviewTable().getSelectedRow();
		this.data.removeIdx(rowIdx);
	}

	private void switchToEdit() {
		JTable reviewerOverviewTable = ((OverviewTable) this.view.assumeState(state)).getReviewerOverviewTable(); //TODO rework this
		int selectedIdx = reviewerOverviewTable.getSelectedRow();
		if(selectedIdx == -1) {
			// No row selected
			return; 
		}
		String reviewerName = reviewerOverviewTable.getValueAt(selectedIdx, ReviewerOverviewTableModel.REVIEWER_COLUMN).toString();
		Reviewer reviewer = this.data.findReviewerByName(reviewerName);
		Logger.getLogger(Controller.class.getName()).info(String.format("Starting editmode on reviewer %s", reviewer.getName()));
		
//		ReviewerEditor editor = new ReviewerEditor(ViewId.EDITOR.getViewID(), "Editor", reviewer);
//		mainController.window.registerView(editor);
//		
//		mainController.reviewerEditorController = new ReviewerEditorController(mainController);
//		
//		mainController.switchToView(editor);
		
		switchState(ApplicationState.HOME); //TODO Implement new edit-view and pass the reviewer
	}
}
