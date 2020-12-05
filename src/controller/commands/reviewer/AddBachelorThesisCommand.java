package controller.commands.reviewer;

import controller.commands.base.RevertibleCommand;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.CascadeMode;

public class AddBachelorThesisCommand extends RevertibleCommand {

	private SecondReview newReview;
	private SecondReview oldReview;

	public AddBachelorThesisCommand(Reviewer reviewer, BachelorThesis bachelorThesis, ApplicationState applicationState) {
		super(applicationState);
		this.newReview = new SecondReview(reviewer, bachelorThesis);
		this.oldReview = bachelorThesis.getSecondReview().orElse(null);
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