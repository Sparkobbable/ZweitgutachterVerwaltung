package controller.commands;

import model.data.SecondReview;
import model.enums.ReviewStatus;

public class ReviewTypeChangeCommand implements Command {

	private SecondReview review;
	private ReviewStatus status;
	private ReviewStatus oldStatus;

	public ReviewTypeChangeCommand(SecondReview review, ReviewStatus status) {
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
