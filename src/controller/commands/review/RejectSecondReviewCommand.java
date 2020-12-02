package controller.commands.review;

import controller.commands.base.RevertibleCommand;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.CascadeMode;
import model.enums.ReviewStatus;

public class RejectSecondReviewCommand extends RevertibleCommand {

	private SecondReview review;
	private ReviewStatus oldStatus;

	public RejectSecondReviewCommand(SecondReview review, ApplicationState applicationState) {
		super(applicationState);
		this.review = review;
	}

	@Override
	public void execute() {
		Reviewer reviewer = this.review.getReviewer();
		BachelorThesis bachelorThesis = this.review.getBachelorThesis();

		this.oldStatus = this.review.getStatus();
		this.review.setStatus(ReviewStatus.REJECTED);
		reviewer.removeSecondReview(this.review);
		reviewer.addRejectedSecondReview(this.review);
		bachelorThesis.removeSecondReview();
	}

	@Override
	public void revert() {
		Reviewer reviewer = this.review.getReviewer();
		BachelorThesis bachelorThesis = this.review.getBachelorThesis();

		this.review.setStatus(this.oldStatus);
		reviewer.removeRejectedSecondReview(this.review);
		reviewer.addSecondReview(review, CascadeMode.STOP);
		bachelorThesis.setSecondReview(review, CascadeMode.STOP);
	}

}
