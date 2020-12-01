package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;
import model.enums.ApplicationState;

public class ReviewerCommentChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerCommentChangeCommand(Reviewer reviewer, String newValue, ApplicationState applicationState) {
		super(Reviewer::setComment, Reviewer::getComment, reviewer, newValue, applicationState);
	}

}
