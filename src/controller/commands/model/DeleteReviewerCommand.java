package controller.commands.model;

import controller.commands.base.RevertibleCommand;
import model.Model;
import model.domain.Review;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.CascadeMode;

public class DeleteReviewerCommand extends RevertibleCommand {

	private Reviewer reviewer;
	private Model model;

	public DeleteReviewerCommand(Reviewer reviewer, Model model, ApplicationState applicationState) {
		super(applicationState);
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
