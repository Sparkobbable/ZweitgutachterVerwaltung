package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;

public class ReviewerCommentChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerCommentChangeCommand(Reviewer reviewer, String newValue) {
		super(Reviewer::setComment, Reviewer::getComment, reviewer, newValue);
	}

}
