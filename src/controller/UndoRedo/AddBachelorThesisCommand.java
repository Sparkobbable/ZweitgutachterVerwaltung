package controller.UndoRedo;

import java.util.Optional;

import model.data.BachelorThesis;
import model.data.Reviewer;
import model.data.SecondReview;
import model.enums.ReviewType;

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
		System.out.println("executing");
		reviewer.addBachelorThesis(bachelorThesis, ReviewType.SECOND_REVIEW);

	}

	@Override
	public void revert() {
		System.out.println("reverting");
		reviewer.removeSecondReviewBachelorThesis(bachelorThesis);
		bachelorThesis.setSecondReview(oldReview);
	}

}
