package controller.statecontrollers;

import static model.enums.EventId.SELECT;

import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ThesesOverviewStateController extends AbstractStateController<BachelorThesis> {

	public ThesesOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.THESES_OVERVIEW, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SELECT, (params) -> addSecondReviewer((int[]) params[0].get(), (Reviewer) params[1].get()));
	}

	private void addSecondReviewer(int[] indices, Reviewer reviewer) {
		// check that one and only one row is selected
		if (indices.length != 1) {
			Log.warning(this, "Only one thesis can be edited at a time.");
			return;
		}
		// indices contains only one element
		int index = indices[0];

		this.model.getTheses().get(index).setSecondReviewer(reviewer);
	}
}
