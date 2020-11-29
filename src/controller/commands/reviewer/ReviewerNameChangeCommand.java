package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;

public class ReviewerNameChangeCommand extends ValueChangeCommand<Reviewer, String> {

	public ReviewerNameChangeCommand(Reviewer reviewer, String newValue) {
		super(Reviewer::setName, Reviewer::getName, reviewer, newValue);
	}

}
