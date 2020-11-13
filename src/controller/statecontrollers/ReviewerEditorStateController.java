package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS;
import static model.enums.EventId.APPROVE_SEC_REVIEW;
import static model.enums.EventId.DELETE_THESIS;
import static model.enums.EventId.SAVE_REVIEWER;

import java.util.Comparator;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;
import view.View;
import view.editor.ReviewerEditorPanel;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_EDITOR}
 */
public class ReviewerEditorStateController extends AbstractStateController {

	public ReviewerEditorStateController(View view,
			ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.REVIEWER_EDITOR, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SAVE_REVIEWER, (params) -> saveReviewer(params));
		this.registerEvent(ADD_THESIS, (params) -> addThesis(params));
		this.registerEvent(DELETE_THESIS, (params) -> deleteThesis(params));
		this.registerEvent(APPROVE_SEC_REVIEW, (params) -> approveThesis(params));
	}


	private void approveThesis(Supplier<?>[] indices) {
		Stream<Integer> indicesStream = IntStream.of((int[]) indices[0].get()).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder());
		indicesStream.forEach(index -> this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.getSupervisedThesis().get(index)
				.getSecondReview().ifPresent(review -> review.setStatus(review.getReviewer().equals(reviewer) ? ReviewStatus.APPROVED : review.getStatus())))); 
	}

	private void deleteThesis(Supplier<?>[] indices) {
		this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.removeThesisByIndices((int[]) indices[0].get()));
	}

	private void addThesis(Supplier<?>[] params) {
		if (!validate()) {
			return;
		}
		Logger.getLogger(ReviewerEditorStateController.class.getName()).info(String.format("Starting add-thesis on Reviewer %s", ((Reviewer) params[0].get()).getName()));
		switchState(ApplicationState.THESIS_ASSIGNMENT);
	}

	private boolean validate() {
		if (!((ReviewerEditorPanel) this.view.assumeState(ApplicationState.REVIEWER_EDITOR)).validateFields()) {
			this.view.assumeState(ApplicationState.REVIEWER_EDITOR)
			.alert("Die benötigten Felder wurden nicht alle gefüllt!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void saveReviewer(Supplier<?>[] params) {
		if(!validate()) {
			return;
		}
		Logger.getLogger(ReviewerEditorStateController.class.getName()).info(String.format("Saving edited Reviewer %s", ((Reviewer) params[0].get()).getName()));
		int index = model.getReviewers().indexOf((Reviewer) params[0].get());
		if (index >= 0) {
			model.removeByIndex(index);
		}
		model.addReviewer((Reviewer) params[0].get());
		switchToLastVisitedState();
	}

}
