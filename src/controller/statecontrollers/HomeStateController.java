package controller.statecontrollers;

import static model.enums.EventId.SHOW_REVIEWERS;

import model.Model;
import model.enums.ApplicationState;
import view.View;

/**
 * Handles the Application when in ApplicationState {@link ApplicationState#HOME}
 */
public class HomeStateController extends AbstractStateController{

	public HomeStateController(View view, ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.HOME, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SHOW_REVIEWERS, () -> switchState(ApplicationState.REVIEWER_OVERVIEW));
	}
}
