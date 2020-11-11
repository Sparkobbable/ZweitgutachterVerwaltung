package controller.statecontrollers;

import static model.enums.EventId.EDIT;

import model.Model;
import model.enums.ApplicationState;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ThesesOverviewStateController extends AbstractStateController {

	public ThesesOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.THESES_OVERVIEW, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, (params) -> switchToEdit((int[]) params[0].get()));
	}

	private void switchToEdit(int[] indices) {
		// check that one and only one row is selected
		if (indices.length != 1) {
			Log.warning(this, "Only one thesis can be edited at a time.");
			return;
		}
		// indices contains only one element
		Integer index = indices[0];
		
		Log.info(this, "Starting reviewer assignment on thesis %s", index);
		model.setSelectedReviewer(index);
		switchState(ApplicationState.HOME);
	}
}
