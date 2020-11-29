package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS;
import static model.enums.EventId.APPROVE_SEC_REVIEW;
import static model.enums.EventId.REJECT;
import static model.enums.EventId.SAVE_REVIEWER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.RejectSecondReviewCommand;
import controller.commands.ReviewTypeChangeCommand;
import controller.commands.base.BatchCommand;
import controller.commands.base.Command;
import model.Model;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;
import model.enums.ReviewType;
import util.Log;
import view.View;
import view.editor.ReviewerEditorPanel;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_EDITOR}
 */
public class ReviewerEditorStateController extends AbstractStateController {

	public ReviewerEditorStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.REVIEWER_EDITOR, view, controller, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(SAVE_REVIEWER,
				(params) -> saveReviewer((Reviewer) params[0].get(), (Optional<Reviewer>) params[1].get()));
		this.registerEvent(ADD_THESIS, (params) -> addThesis());
		this.registerEvent(REJECT, (params) -> rejectSecondReviews((Collection<SecondReview>) params[0].get()));
		this.registerEvent(APPROVE_SEC_REVIEW, (params) -> approveReviews((Collection<SecondReview>) params[0].get()));
	}

	private void approveReviews(Collection<SecondReview> reviews) {

		if (this.model.getSelectedReviewer().isEmpty()) {
			throw new IllegalStateException("Selected reviewer must not be empty");
		}
		reviews.stream().filter(review -> review.getReviewType() == ReviewType.SECOND_REVIEW)
				.map(review -> (SecondReview) review).forEach(this::approve);
	}

	private void approve(SecondReview review) {
		this.execute(new ReviewTypeChangeCommand(review, ReviewStatus.APPROVED));
	}

	private void rejectSecondReviews(Collection<SecondReview> reviews) {
		List<Command> commands = new ArrayList<>();
		reviews.forEach(review -> commands.add(new RejectSecondReviewCommand(review)));
		this.execute(new BatchCommand(commands));
	}

	private void addThesis() {
		if (!validate()) {
			return;
		}
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

	private void saveReviewer(Reviewer newReviewer, Optional<Reviewer> originalReviewer) {
		if (!validate()) {
			return;
		}
		Log.info(this.getClass().getName(), "Saving edited Reviewer %s.", newReviewer.getName());
		if (originalReviewer.isPresent()) {
			model.updateReviewer(originalReviewer.get(), newReviewer);
		} else {
			model.addReviewer(newReviewer);
		}
		this.model.clearSelectedReviewer();
		switchToLastVisitedState();
	}

	private void createReviewer(Reviewer reviewer) {

	}

}
