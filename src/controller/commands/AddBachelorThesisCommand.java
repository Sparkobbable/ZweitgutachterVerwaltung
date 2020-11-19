package controller.commands;

import java.util.Optional;

import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;

public class AddBachelorThesisCommand implements Command {

	private Reviewer reviewer;
	private BachelorThesis bachelorThesis;

	private Optional<SecondReview> oldReview;

	public AddBachelorThesisCommand(Reviewer reviewer, BachelorThesis bachelorThesis) {
		this.reviewer = reviewer;
		this.bachelorThesis = bachelorThesis;
		this.oldReview = this.bachelorThesis.getSecondReview();
	}

	@Override
	public void execute() {
		this.reviewer.addBachelorThesis(this.bachelorThesis);
		this.bachelorThesis.setSecondReviewer(this.reviewer);
	}

	@Override
	public void revert() {
		this.reviewer.removeSecondReviewBachelorThesis(this.bachelorThesis);
		this.bachelorThesis.setSecondReview(this.oldReview);
	}

}
