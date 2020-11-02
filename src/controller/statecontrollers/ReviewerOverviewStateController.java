package controller.statecontrollers;

import static model.enums.EventId.DELETE;
import static model.enums.EventId.EDIT;
import static model.enums.EventId.NEW;

import java.util.function.Supplier;
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
		this.registerEvent(EDIT, (params) -> switchToEdit(params));
		this.registerEvent(DELETE, (params) -> deleteEntry(params)); // TODO View isnt correctly refreshed
		this.registerEvent(NEW, (params) -> addNewReviewer(params));
	}

	private void addNewReviewer(Supplier<?>[] params) {
		Logger.getLogger(ReviewerOverviewStateController.class.getName()).info("Starting editmode on new Reviewer");
		Reviewer reviewer = new Reviewer();
		data.setSelectedReviewer(reviewer);
		
		switchState(ApplicationState.REVIEWER_EDITOR);
	}
	
	private void deleteEntry(Supplier<?>[] params) {
		this.data.removeIdx((int) params[0].get());
	}

	private void switchToEdit(Supplier<?>[] params) {
		// TODO check that one and only one row is selected
		Logger.getLogger(ReviewerOverviewStateController.class.getName())
				.info(String.format("Starting editmode on reviewer %s", params[0].get()));
		
		if ((int) params[0].get() == -1) {
			Logger.getLogger(ReviewerOverviewStateController.class.getName())
					.warning(String.format("No reviewer selected. Ignoring EDIT event.", params[0].get()));
			return;
		}
		

		Reviewer reviewer = data.getReviewers().get((int) params[0].get());
		data.setSelectedReviewer(reviewer);

		switchState(ApplicationState.REVIEWER_EDITOR);
	}
}
