package controller.statecontrollers;

import static model.enums.EventId.SELECT;

import java.util.List;
import java.util.Optional;

import controller.Controller;
import controller.commands.reviewer.AddBachelorThesisCommand;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ThesesOverviewStateController extends AbstractStateController {

	public ThesesOverviewStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.THESES_OVERVIEW, view, controller, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(SELECT, (params) -> addSecondReviewer((List<BachelorThesis>) params[0].get(), (Reviewer) params[1].get()));
		this.registerEvent(EventId.NAVIGATE, (params) -> navigate((Optional<Reviewer>) params[0].get()));
	}
	
	private void navigate(Optional<Reviewer> reviewer) {
		if (reviewer.isPresent()) {
			this.model.setSelectedReviewer(reviewer.get());
			this.switchState(ApplicationState.REVIEWER_EDITOR);
		}
	}

	private void addSecondReviewer(List<BachelorThesis> selectedTheses, Reviewer reviewer) {
		// check that one and only one row is selected
		if (selectedTheses.size() != 1) {
			Log.warning(this, "Only one thesis can be edited at a time.");
			return;
		}
		
		// list contains only one element

		if (reviewer.getMaxSupervisedThesis() - reviewer.getTotalReviewCount() < selectedTheses.size()) {
			//geht net
			Log.warning(this, "Max occupation reached.");
			return;
		}
		
		BachelorThesis selectedThesis = selectedTheses.get(0);
		
		this.execute(new AddBachelorThesisCommand(reviewer, selectedThesis, ApplicationState.THESES_OVERVIEW));
	}
}
