package controller.commands.reviewer;

import controller.commands.base.RevertableCommand;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.CascadeMode;

public class AddBachelorThesisCommand extends RevertableCommand {

	private Reviewer reviewer;
	private BachelorThesis bachelorThesis;

	private SecondReview newReview;
	private SecondReview oldReview;

	public AddBachelorThesisCommand(Reviewer reviewer, BachelorThesis bachelorThesis) {
		this.newReview = new SecondReview(this.reviewer, this.bachelorThesis);
		this.oldReview = this.bachelorThesis.getSecondReview().orElse(null);
	}

	@Override
	public void execute() {
		this.newReview.getReviewer().addSecondReview(newReview, CascadeMode.STOP);
		this.newReview.getBachelorThesis().setSecondReview(newReview, CascadeMode.STOP);
	}

	@Override
	public void revert() {
		this.newReview.getReviewer().removeSecondReview(this.newReview);
		this.newReview.getBachelorThesis().setSecondReview(this.oldReview, CascadeMode.STOP);
	}

}
