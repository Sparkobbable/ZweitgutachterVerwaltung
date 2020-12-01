package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;
import model.enums.ApplicationState;

public class ReviewerMaxSupervisedThesesChangeCommand extends ValueChangeCommand<Reviewer, Integer> {

	public ReviewerMaxSupervisedThesesChangeCommand(Reviewer reviewer, int newValue, ApplicationState applicationState) {
		super(Reviewer::setMaxSupervisedThesis, Reviewer::getMaxSupervisedThesis, reviewer, newValue, applicationState);
	}

}
