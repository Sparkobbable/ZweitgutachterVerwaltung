package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS;
import static model.enums.EventId.APPROVE_SEC_REVIEW;
import static model.enums.EventId.COMMENT_CHANGED;
import static model.enums.EventId.EMAIL_CHANGED;
import static model.enums.EventId.MAX_SUPERVISED_THESES_CHANGED;
import static model.enums.EventId.NAME_CHANGED;
import static model.enums.EventId.REJECT;
import static model.enums.EventId.SAVE_REVIEWER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.base.BatchCommand;
import controller.commands.base.Command;
import controller.commands.review.RejectSecondReviewCommand;
import controller.commands.review.ReviewTypeChangeCommand;
import controller.commands.reviewer.ReviewerCommentChangeCommand;
import controller.commands.reviewer.ReviewerEmailChangeCommand;
import controller.commands.reviewer.ReviewerMaxSupervisedThesesChangeCommand;
import controller.commands.reviewer.ReviewerNameChangeCommand;
import model.Model;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;
import model.enums.ReviewType;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_EDITOR}
 */
public class ReviewerEditorStateController extends AbstractStateController {

	public ReviewerEditorStateController(View view, Controller controller, Model model) {
		super(ApplicationState.REVIEWER_EDITOR, view, controller, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(SAVE_REVIEWER, (params) -> saveReviewer());
		this.registerEvent(ADD_THESIS, (params) -> addThesis());
		this.registerEvent(REJECT, (params) -> rejectSecondReviews((Collection<SecondReview>) params[0].get()));
		this.registerEvent(APPROVE_SEC_REVIEW, (params) -> approveReviews((Collection<SecondReview>) params[0].get()));

		this.registerEvent(NAME_CHANGED, (params) -> nameChanged((String) params[0].get()));
		this.registerEvent(MAX_SUPERVISED_THESES_CHANGED,
				(params) -> maxSupervisedThesesChanged((String) params[0].get()));
		this.registerEvent(EMAIL_CHANGED, (params) -> emailChanged((String) params[0].get()));
		this.registerEvent(COMMENT_CHANGED, (params) -> commentChanged((String) params[0].get()));
	}

	private void nameChanged(String newValue) {
		if (!this.model.getSelectedReviewer().orElseThrow().getName().equals(newValue)) {
			this.execute(new ReviewerNameChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void maxSupervisedThesesChanged(String newValue) {
		int maxSupervisedTheses = Integer.parseInt(newValue);
		if (this.model.getSelectedReviewer().orElseThrow().getMaxSupervisedThesis() != maxSupervisedTheses) {
			this.execute(new ReviewerMaxSupervisedThesesChangeCommand(this.model.getSelectedReviewer().orElseThrow(),
					maxSupervisedTheses, ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void emailChanged(String newValue) {
		if (!this.model.getSelectedReviewer().orElseThrow().getEmail().equals(newValue)) {
			this.execute(new ReviewerEmailChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void commentChanged(String newValue) {
		if (!this.model.getSelectedReviewer().orElseThrow().getComment().equals(newValue)) {
			this.execute(new ReviewerCommentChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void approveReviews(Collection<SecondReview> reviews) {

		if (this.model.getSelectedReviewer().isEmpty()) {
			throw new IllegalStateException("Selected reviewer must not be empty");
		}
		reviews.stream().filter(review -> review.getReviewType() == ReviewType.SECOND_REVIEW)
				.map(review -> (SecondReview) review).forEach(this::approve);
	}

	private void approve(SecondReview review) {
		this.execute(new ReviewTypeChangeCommand(review, ReviewStatus.APPROVED, ApplicationState.REVIEWER_EDITOR));
	}

	private void rejectSecondReviews(Collection<SecondReview> reviews) {
		List<Command> commands = new ArrayList<>();
		reviews.forEach(
				review -> commands.add(new RejectSecondReviewCommand(review, ApplicationState.REVIEWER_EDITOR)));
		this.execute(new BatchCommand(commands));
	}

	private void addThesis() {
		if (!validate()) {
			return;
		}
		switchState(ApplicationState.THESIS_ASSIGNMENT);
	}

	private boolean validate() {

		return true;
	}

	private void saveReviewer() {
		if (!this.validateFields()) {
			this.view.alert("Die benötigten Felder wurden nicht alle gefüllt!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		switchToLastVisitedState();
	}

	public boolean validateFields() {
		Reviewer selectedReviewer = this.model.getSelectedReviewer().orElseThrow();
		return selectedReviewer.getName() != null && !selectedReviewer.getName().isBlank()
				&& selectedReviewer.getMaxSupervisedThesis() >= 0;
	}
}
