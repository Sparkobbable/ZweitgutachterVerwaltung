package controller.statecontrollers;

import static model.enums.EventId.SELECT;
import static model.enums.EventId.SEARCH_OVERVIEW_THESES;

import java.util.ArrayList;


import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
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
		this.registerEvent(SEARCH_OVERVIEW_THESES, (params) -> this.onThesisSearch((String) params[0].get()));
	}
	
	@SuppressWarnings("unchecked")
	private void onThesisSearch(String searchText) {
		ArrayList<BachelorThesis> copyList = new ArrayList<BachelorThesis>(this.model.getTheses());
		this.model.clearDisplayedTheses();
		this.model.addDisplayedTheses((ArrayList<BachelorThesis>) searchController.handleSearch(copyList, searchText));
	}

	private void addSecondReviewer(int[] indices, Reviewer reviewer) {
		// check that one and only one row is selected
		if (indices.length != 1) {
			Log.warning(this, "Only one thesis can be edited at a time.");
			return;
		}
		// indices contains only one element
		int index = indices[0];

		this.model.getDisplayedTheses().get(index).setSecondReviewer(reviewer);
	}
}
