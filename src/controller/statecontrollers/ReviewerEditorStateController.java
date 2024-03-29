package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS;
import static model.enums.EventId.APPROVE_SEC_REVIEW;
import static model.enums.EventId.COMMENT_CHANGED;
import static model.enums.EventId.EMAIL_CHANGED;
import static model.enums.EventId.MAX_SUPERVISED_THESES_CHANGED;
import static model.enums.EventId.NAME_CHANGED;
import static model.enums.EventId.REJECT;
import static model.enums.EventId.RESERVE_SEC_REVIEW;

import java.util.Collection;

import controller.Controller;
import controller.commands.review.RejectSecondReviewCommand;
import controller.commands.review.ReviewTypeChangeCommand;
import controller.commands.reviewer.ReviewerCommentChangeCommand;
import controller.commands.reviewer.ReviewerEmailChangeCommand;
import controller.commands.reviewer.ReviewerMaxSupervisedThesesChangeCommand;
import controller.commands.reviewer.ReviewerNameChangeCommand;
import model.Model;
import model.domain.Review;
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
		this.registerEvent(ADD_THESIS, (params) -> addThesis());
		this.registerEvent(REJECT, (params) -> rejectSecondReviews((Collection<Review>) params[0].get()));
		this.registerEvent(APPROVE_SEC_REVIEW, (params) -> approveReviews((Collection<Review>) params[0].get()));
		this.registerEvent(RESERVE_SEC_REVIEW, (params) -> reserveReviews((Collection<Review>) params[0].get()));

		this.registerEvent(NAME_CHANGED, (params) -> nameChanged((String) params[0].get()));
		this.registerEvent(MAX_SUPERVISED_THESES_CHANGED,
				(params) -> maxSupervisedThesesChanged((String) params[0].get()));
		this.registerEvent(EMAIL_CHANGED, (params) -> emailChanged((String) params[0].get()));
		this.registerEvent(COMMENT_CHANGED, (params) -> commentChanged((String) params[0].get()));
	}

	private void reserveReviews(Collection<Review> collection) {
		if (this.model.getSelectedReviewer().isEmpty()) {
			throw new IllegalStateException("Selected reviewer must not be empty");
		}
		collection.stream().filter(review -> review.getReviewType() == ReviewType.SECOND_REVIEW)
				.map(review -> (SecondReview) review).findAny().ifPresentOrElse(this::reserve, this::notifyFirstReview);
	}

	private void nameChanged(String newValue) {
		String name = this.model.getSelectedReviewer().orElseThrow().getName();

		if (name.isBlank()) {
			this.popupInfo("Der Name des Gutachters darf nicht leer sein.");
			this.model.getSelectedReviewer().orElseThrow().setName(name);
			return;
		}

		if (!name.equals(newValue)) {
			this.execute(new ReviewerNameChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void maxSupervisedThesesChanged(String newValue) {
		int maxSupervisedTheses = Integer.parseInt(newValue);

		int supervised = this.model.getSelectedReviewer().orElseThrow().getAllSupervisedReviews().size();
		if (supervised > maxSupervisedTheses) {
			this.popupInfo(String.format(
					"%s sind %s Gutachten zugeordnet. Daher kann die Anzahl der maximal betreuten Bachelorarbeiten nicht unter %s liegen.",
					this.model.getSelectedReviewer().orElseThrow().getName(), supervised, supervised));
			this.model.getSelectedReviewer().orElseThrow().setMaxSupervisedTheses(supervised);
			return;
		}

		if (this.model.getSelectedReviewer().orElseThrow().getMaxSupervisedThesis() != maxSupervisedTheses) {
			this.execute(new ReviewerMaxSupervisedThesesChangeCommand(this.model.getSelectedReviewer().orElseThrow(),
					maxSupervisedTheses, ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void emailChanged(String newValue) {
		if (this.model.getSelectedReviewer().orElseThrow().getEmail() != null
				&& !this.model.getSelectedReviewer().orElseThrow().getEmail().equals(newValue)) {
			this.execute(new ReviewerEmailChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void commentChanged(String newValue) {
		if (this.model.getSelectedReviewer().orElseThrow().getComment() != null
				&& !this.model.getSelectedReviewer().orElseThrow().getComment().equals(newValue)) {
			this.execute(new ReviewerCommentChangeCommand(this.model.getSelectedReviewer().orElseThrow(), newValue,
					ApplicationState.REVIEWER_EDITOR));
		}
	}

	private void approveReviews(Collection<Review> collection) {
		if (this.model.getSelectedReviewer().isEmpty()) {
			throw new IllegalStateException("Selected reviewer must not be empty");
		}
		collection.stream().filter(review -> review.getReviewType() == ReviewType.SECOND_REVIEW)
				.map(review -> (SecondReview) review).findAny().ifPresentOrElse(this::approve, this::notifyFirstReview);
	}

	private void notifyFirstReview() {
		this.popupInfo(
				"Ausgew�hlt ist ein Gutachten, welches als Erstgutachten betreut wird. Die gew�hlte Aktion kann darauf nicht angewendet werden.");
	}

	private void approve(SecondReview review) {
		this.execute(new ReviewTypeChangeCommand(review, ReviewStatus.APPROVED, ApplicationState.REVIEWER_EDITOR));
	}

	private void reserve(SecondReview review) {
		if (ReviewStatus.APPROVED.equals(review.getStatus())) {
			this.popupInfo("Das Gutachten wurde bereits best�tigt und kann nicht mehr reserviert werden.");
			return;
		}
		this.execute(new ReviewTypeChangeCommand(review, ReviewStatus.RESERVED, ApplicationState.REVIEWER_EDITOR));
	}

	private void rejectSecondReviews(Collection<Review> collection) {
		collection.stream().filter(review -> review.getReviewType().equals(ReviewType.SECOND_REVIEW))
				.map(review -> (SecondReview) review).findAny().ifPresentOrElse(this::reject, this::notifyFirstReview);
	}

	private void reject(SecondReview review) {
		if (ReviewStatus.APPROVED.equals(review.getStatus())) {
			this.popupInfo("Das Gutachten wurde bereits best�tigt und kann nicht mehr abgelehnt werden.");
			return;
		}
		this.execute(new RejectSecondReviewCommand(review, ApplicationState.REVIEWER_EDITOR));
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
}
