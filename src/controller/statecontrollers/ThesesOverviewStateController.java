package controller.statecontrollers;

import static model.enums.EventId.SELECT;

import java.util.List;

import controller.commands.AddBachelorThesisCommand;
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

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(SELECT, (params) -> addSecondReviewer((List<BachelorThesis>) params[0].get(), (Reviewer) params[1].get()));
	}
	
	private void addSecondReviewer(List<BachelorThesis> selectedTheses, Reviewer reviewer) {
		// check that one and only one row is selected
		if (selectedTheses.size() != 1) {
			Log.warning(this, "Only one thesis can be edited at a time.");
			return;
		}
		// list contains only one element
		BachelorThesis selectedThesis = selectedTheses.get(0);
		
		this.commandExecutionController.execute(new AddBachelorThesisCommand(reviewer, selectedThesis));
	}
}
