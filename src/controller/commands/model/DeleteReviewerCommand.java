package controller.commands.model;

import controller.commands.base.Command;
import controller.commands.base.RevertibleCommand;
import model.Model;
import model.domain.Review;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.exceptions.SupervisesThesisException;

/**
 * {@link Command} that deletes a (second) {@link Reviewer}
 * 
 */
public class DeleteReviewerCommand extends RevertibleCommand {

	private final Reviewer reviewer;
	private final Model model;

	public DeleteReviewerCommand(Reviewer reviewer, Model model, ApplicationState applicationState) {
		super(applicationState);
		this.model = model;
		this.reviewer = reviewer;
	}

	@Override
	public void execute() {
		if (!this.reviewer.getFirstReviews().isEmpty()) {
			throw new SupervisesThesisException(
					"Cannot delete reviewers that supervise a BachelorThesis as FirstReviewer", this.reviewer);
		}

		this.reviewer.getUnrejectedSecondReviews().stream().map(Review::getBachelorThesis)
				.forEach(thesis -> thesis.removeSecondReview());
		this.model.removeReviewer(this.reviewer);
	}

	@Override
	public void revert() {
		this.reviewer.getUnrejectedSecondReviews().stream()
				.forEach(review -> review.getBachelorThesis().setSecondReview(review));
		this.model.addReviewer(reviewer);
	}

}
