package controller.commands.reviewer;

import controller.commands.base.Command;
import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;
import model.enums.ApplicationState;

/**
 * {@link Command} that changes the number of maximally supervisable theses for a {@link Reviewer}
 */
public class ReviewerMaxSupervisedThesesChangeCommand extends ValueChangeCommand<Reviewer, Integer> {

	public ReviewerMaxSupervisedThesesChangeCommand(Reviewer reviewer, int newValue, ApplicationState applicationState) {
		super(Reviewer::setMaxSupervisedTheses, Reviewer::getMaxSupervisedThesis, reviewer, newValue, applicationState);
	}

}
