package controller.statecontrollers;

import static model.enums.EventId.DELETE;
import static model.enums.EventId.EDIT;
import static model.enums.EventId.NEW;

import java.util.logging.Logger;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
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
		this.registerEvent(EDIT, (params) -> switchToEdit((int[]) params[0].get()));
		this.registerEvent(DELETE, (params) -> deleteEntries((int[]) params[0].get())); // TODO View isnt correctly
																						// refreshed
		this.registerEvent(NEW, (params) -> addNewReviewer());
	}

	private void addNewReviewer() {
		Logger.getLogger(ReviewerOverviewStateController.class.getName()).info("Starting editmode on new Reviewer");
		Reviewer reviewer = new Reviewer();
		data.setSelectedReviewer(reviewer);

		switchState(ApplicationState.REVIEWER_EDITOR);
	}

	private void deleteEntries(int[] indices) {
		this.data.removeByIndices(indices);
	}

	private void switchToEdit(int[] indices) {
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
		data.setSelectedReviewer(index);
		switchState(ApplicationState.REVIEWER_EDITOR);
	}
}
