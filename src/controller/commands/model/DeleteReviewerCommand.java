package controller.commands.model;

import controller.commands.base.RevertableCommand;
import model.Model;
import model.domain.Review;
import model.domain.Reviewer;
import model.enums.CascadeMode;

public class DeleteReviewerCommand extends RevertableCommand {

	private Reviewer reviewer;
	private Model model;

	public DeleteReviewerCommand(Model model, Reviewer reviewer) {
		this.model = model;
		this.reviewer = reviewer;
	}

	@Override
	public void execute() {
		System.out.println("exec");
		if (!this.reviewer.getFirstReviews().isEmpty()) {
			throw new IllegalStateException("Cannot delete reviewers that supervise a BachelorThesis as FirstReviewer");
		}

		this.reviewer.getUnrejectedSecondReviews().stream().map(Review::getBachelorThesis)
				.forEach(thesis -> thesis.removeSecondReview());
		this.model.removeReviewer(this.reviewer);
	}

	@Override
	public void revert() {
		this.reviewer.getUnrejectedSecondReviews().stream()
				.forEach(review -> review.getBachelorThesis().setSecondReview(review, CascadeMode.STOP));
		this.model.addReviewer(reviewer);
	}

}
