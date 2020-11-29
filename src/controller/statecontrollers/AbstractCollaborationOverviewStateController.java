package controller.statecontrollers;

import java.util.ArrayList;

import controller.ApplicationStateController;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ReviewType;
import view.View;

public abstract class AbstractCollaborationOverviewStateController extends AbstractStateController {

	public AbstractCollaborationOverviewStateController(ApplicationState applicationState, View view,
			ApplicationStateController applicationStateController, Model model) {
		super(applicationState, view, applicationStateController, model);
	}
	
	protected ArrayList<Reviewer> setCollaborationFirstReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
				.filter(review -> review.getReviewType().equals(ReviewType.SECOND_REVIEW))
				.forEach(review -> result.add(review.getBachelorThesis().getFirstReview().getReviewer())));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}
	
	protected ArrayList<Reviewer> setCollaborationSecondReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
				.filter(review -> review.getReviewType().equals(ReviewType.FIRST_REVIEW))
				.forEach(review -> review.getBachelorThesis().getSecondReview()
				.ifPresent(secondreview -> result.add(secondreview.getReviewer()))));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}
	
	protected ArrayList<Reviewer> setCollaborationAllReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
				.filter(review -> review.getReviewType().equals(ReviewType.SECOND_REVIEW))
				.forEach(review -> result.add(review.getBachelorThesis().getFirstReview().getReviewer())));
		
		this.model.getSelectedReviewer().ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
				.filter(review -> review.getReviewType().equals(ReviewType.FIRST_REVIEW))
				.forEach(review -> review.getBachelorThesis().getSecondReview()
				.ifPresent(secondreview -> result.add(secondreview.getReviewer()))));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}
}
