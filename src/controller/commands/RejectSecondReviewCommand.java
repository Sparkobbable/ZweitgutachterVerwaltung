package controller.commands;

import model.domain.Review;

public class RejectSecondReviewCommand implements Command {

	private Review review;
	
	public RejectSecondReviewCommand(Review review) {
		this.review = review;
	}

	@Override
	public void execute() {
//		this.review.getReviewer().removeSecondReviewBachelorThesis();
	}

	@Override
	public void revert() {
		// TODO Auto-generated method stub
		
	}

}
