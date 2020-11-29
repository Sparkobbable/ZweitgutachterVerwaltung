package controller.commands.reviewer;

import controller.commands.base.ValueChangeCommand;
import model.domain.Reviewer;

public class ReviewerMaxSupervisedThesesChangeCommand extends ValueChangeCommand<Reviewer, Integer> {

	public ReviewerMaxSupervisedThesesChangeCommand(Reviewer reviewer, int newValue) {
		super(Reviewer::setMaxSupervisedThesis, Reviewer::getMaxSupervisedThesis, reviewer, newValue);
	}

}
