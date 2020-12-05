package controller.commands.reviewer;

import controller.commands.base.Command;
import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;
import model.enums.ApplicationState;

/**
 * {@link Command} that changes the {@link Reviewer} email
 */
public class ReviewerEmailChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerEmailChangeCommand(Reviewer reviewer, String newValue, ApplicationState applicationState) {
		super(Reviewer::setEmail, Reviewer::getEmail, reviewer, newValue, applicationState);
	}

}
