package controller.commands;

import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.CascadeMode;

public class AddBachelorThesisCommand extends RevertableCommand {

	private Reviewer reviewer;
	private BachelorThesis bachelorThesis;

	private SecondReview oldReview;

	public AddBachelorThesisCommand(Reviewer reviewer, BachelorThesis bachelorThesis) {
		this.reviewer = reviewer;
		this.bachelorThesis = bachelorThesis;
		this.oldReview = this.bachelorThesis.getSecondReview().orElse(null);
	}

	@Override
	public void execute() {
		SecondReview review = new SecondReview(this.reviewer, this.bachelorThesis);
		this.reviewer.addSecondReviewerReview(review, CascadeMode.STOP);
		this.bachelorThesis.setSecondReview(review, CascadeMode.STOP);
	}

	@Override
	public void revert() {
		this.reviewer.removeSecondReviewBachelorThesis(this.bachelorThesis);
		this.bachelorThesis.setSecondReview(this.oldReview, CascadeMode.STOP);
	}

}
