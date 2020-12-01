package controller.commands.model;

import controller.commands.base.RevertibleCommand;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;

public class CreateReviewerCommand extends RevertibleCommand{

	private static final String DEFAULT_NAME = "Nachname, Vorname";
	private static final int DEFAULT_THESIS_COUNT = 0;

	private Reviewer reviewer;
	private Model model;
	
	public CreateReviewerCommand(Model model, ApplicationState applicationState) {
		super(applicationState);
		this.model = model;
	}
	
	@Override
	public void execute() {
		this.reviewer = new Reviewer(DEFAULT_NAME, DEFAULT_THESIS_COUNT);
		this.model.addReviewer(reviewer);
	}

	@Override
	public void revert() {
		this.model.removeReviewer(reviewer);
	}

}
