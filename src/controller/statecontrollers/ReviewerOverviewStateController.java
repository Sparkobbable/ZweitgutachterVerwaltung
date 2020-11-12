package controller.statecontrollers;

import static model.enums.EventId.DELETE;
import static model.enums.EventId.EDIT;
import static model.enums.EventId.NEW;

import java.util.logging.Logger;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ReviewerOverviewStateController extends AbstractStateController {

	public ReviewerOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.REVIEWER_OVERVIEW, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, (params) -> switchToState(ApplicationState.REVIEWER_EDITOR, (int[]) params[0].get()));
		this.registerEvent(DELETE, (params) -> deleteEntries((int[]) params[0].get())); // TODO View isnt correctly
		this.registerEvent(EventId.SHOW_COLLABORATION, (params) -> switchToState(ApplicationState.COLLABORATION_TABLE, (int[]) params[0].get()));
																						// refreshed
		this.registerEvent(NEW, (params) -> addNewReviewer());
	}

	private void addNewReviewer() {
		Logger.getLogger(ReviewerOverviewStateController.class.getName()).info("Starting editmode on new Reviewer");
		Reviewer reviewer = new Reviewer();
		model.setSelectedReviewer(reviewer);

		switchState(ApplicationState.REVIEWER_EDITOR);
	}

	private void deleteEntries(int[] indices) {
		this.model.removeByIndices(indices);
	}

	private void switchToState(ApplicationState applicationState, int[] indices) {
		// check that one and only one row is selected
		if (indices.length != 1) {
			Logger.getLogger(ReviewerOverviewStateController.class.getName())
					.warning(String.format("Only one reviewer can be edited at a time. "));
			return;
		}
		// indices contains only one element
		Integer index = indices[0];
		
		Logger.getLogger(ReviewerOverviewStateController.class.getName())
				.info(String.format("Starting editmode on reviewer %s", index));
		model.setSelectedReviewer(index);
		switchState(applicationState);
	}
}
