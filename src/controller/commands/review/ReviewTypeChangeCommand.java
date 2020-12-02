package controller.commands.review;

import controller.commands.base.RevertibleCommand;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;

//TODO rename to ApproveSecondReviewCommand
public class ReviewTypeChangeCommand extends RevertibleCommand {

	private SecondReview review;
	private ReviewStatus status;
	private ReviewStatus oldStatus;

	public ReviewTypeChangeCommand(SecondReview review, ReviewStatus status, ApplicationState applicationState) {
		super(applicationState);
		this.review = review;
		this.status = status;
		this.oldStatus = this.review.getStatus();
	}

	@Override
	public void execute() {
		this.review.setStatus(this.status);
	}

	@Override
	public void revert() {
		this.review.setStatus(this.oldStatus);
	}

}
