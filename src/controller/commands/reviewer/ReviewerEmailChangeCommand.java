package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;

public class ReviewerEmailChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerEmailChangeCommand(Reviewer reviewer, String newValue) {
		super(Reviewer::setEmail, Reviewer::getEmail, reviewer, newValue);
	}

}
