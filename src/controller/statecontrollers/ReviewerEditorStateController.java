package controller.statecontrollers;

import static model.enums.EventId.SAVE_REVIEWER;
import static model.enums.EventId.ADD_THESIS;
import static model.enums.EventId.DELETE_THESIS;

import java.util.function.Supplier;
import java.util.logging.Logger;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_EDITOR}
 */
public class ReviewerEditorStateController extends AbstractStateController {

	public ReviewerEditorStateController(View view,
			ApplicationStateController applicationStateController, Model data) {
		super(ApplicationState.REVIEWER_EDITOR, view, applicationStateController, data);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SAVE_REVIEWER, (params) -> saveReviewer(params));
		this.registerEvent(ADD_THESIS, (params) -> addThesis(params));
		this.registerEvent(DELETE_THESIS, (params) -> deleteThesis(params));
	}


	private void deleteThesis(Supplier<?>[] indices) {
		this.data.getSelectedReviewer().ifPresent(reviewer -> reviewer.removeThesisByIndices((int[]) indices[0].get()));
	}

	private void addThesis(Supplier<?>[] params) {
		Logger.getLogger(ReviewerEditorStateController.class.getName()).info(String.format("Starting add-thesis on Reviewer %s", ((Reviewer) params[0].get()).getName()));
	}

	private void saveReviewer(Supplier<?>[] params) {
		Logger.getLogger(ReviewerEditorStateController.class.getName()).info(String.format("Saving edited Reviewer %s", ((Reviewer) params[0].get()).getName()));
		int index = data.getReviewers().indexOf((Reviewer) params[0].get());
		if (index >= 0) {
			data.removeByIndex(index);
		}
		data.addReviewer((Reviewer) params[0].get());
		switchState(ApplicationState.REVIEWER_OVERVIEW);
	}

}
