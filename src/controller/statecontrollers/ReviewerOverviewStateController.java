package controller.statecontrollers;

import static model.enums.EventId.EDIT;
import static model.enums.EventId.NEW;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
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

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, (params) -> this.switchToState(ApplicationState.REVIEWER_EDITOR, (int[]) params[0].get()));
//		this.registerEvent(DELETE, (params) -> this.deleteEntries((int[]) params[0].get())); // TODO Remove delete
		this.registerEvent(EventId.SHOW_COLLABORATION, (params) -> switchToState(ApplicationState.COLLABORATION_TABLE, (int[]) params[0].get()));
																						// refreshed
		this.registerEvent(NEW, (params) -> switchState(ApplicationState.REVIEWER_EDITOR));
	}


	private void switchToState(ApplicationState applicationState, int[] indices) {
		// check that one and only one row is selected
		if (indices.length != 1) {
			Log.warning(this.getClass().getName(), "Only one reviewer can be edited at a time. ");
			return;
		}
		// indices contains only one element
		Integer index = indices[0];
		
		model.setSelectedReviewer(index);
		Log.info(this.getClass().getName(), "Starting editmode on reviewer %s", model.getSelectedReviewer().get().getName());
		switchState(applicationState);
	}
}
