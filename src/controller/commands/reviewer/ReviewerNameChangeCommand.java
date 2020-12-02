package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;
import model.enums.ApplicationState;

public class ReviewerNameChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerNameChangeCommand(Reviewer reviewer, String newValue, ApplicationState applicationState) {
		super(Reviewer::setName, Reviewer::getName, reviewer, newValue, applicationState);
	}

}
