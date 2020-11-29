package controller.statecontrollers;

import static model.enums.EventId.DELETE;
import static model.enums.EventId.EDIT;
import static model.enums.EventId.NEW;
import static model.enums.EventId.SHOW_COLLABORATION;

import java.util.ArrayList;
import java.util.List;

import controller.commands.BatchCommand;
import controller.commands.Command;
import controller.commands.DeleteReviewerCommand;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ReviewerOverviewStateController extends AbstractStateController<Reviewer> {

	public ReviewerOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.REVIEWER_OVERVIEW, view, applicationStateController, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT,
				(params) -> this.switchToState(ApplicationState.REVIEWER_EDITOR, (List<Reviewer>) params[0].get()));
		this.registerEvent(DELETE, (params) -> this.deleteReviewers((List<Reviewer>) params[0].get()));
		this.registerEvent(SHOW_COLLABORATION,
				(params) -> switchToState(ApplicationState.COLLABORATION_TABLE, (List<Reviewer>) params[0].get()));
		this.registerEvent(NEW, (params) -> switchState(ApplicationState.REVIEWER_EDITOR));
	}

	private void deleteReviewers(List<Reviewer> reviewers) {
		List<Command> deleteCommands = new ArrayList<>();
		reviewers.forEach(r -> deleteCommands.add(new DeleteReviewerCommand(this.model, r)));
		this.commandExecutionController.execute(new BatchCommand(deleteCommands));
	}

	private void switchToState(ApplicationState applicationState, List<Reviewer> selectedReviewers) {
		// check that one and only one row is selected
		if (selectedReviewers.size() != 1) {
			Log.warning(this.getClass().getName(), "Only one reviewer can be edited at a time. ");
			return;
		}
		Reviewer selectedReviewer = selectedReviewers.get(0);

		this.model.setSelectedReviewer(selectedReviewer);
		Log.info(this.getClass().getName(), "Starting editmode on reviewer %s", selectedReviewer.getName());
		switchState(applicationState);
	}
}
