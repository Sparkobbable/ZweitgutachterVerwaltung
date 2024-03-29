package controller.statecontrollers;

import static model.enums.EventId.SHOW_REVIEWERS;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

/**
 * Handles the Application when in ApplicationState {@link ApplicationState#HOME}
 */
public class HomeStateController extends AbstractStateController{

	public HomeStateController(View view, Controller controller, Model model) {
		super(ApplicationState.HOME, view, controller, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SHOW_REVIEWERS, (params) -> switchState(ApplicationState.REVIEWER_OVERVIEW));
		this.registerEvent(EventId.SHOW_THESES, (params) -> switchState(ApplicationState.THESES_OVERVIEW));
		this.registerEvent(EventId.CHOOSE_FILEPATH, (params) -> switchState(ApplicationState.STATE_CHOOSER));
		this.registerEvent(EventId.ANALYSE, (params) -> switchState(ApplicationState.ANALYSE_TABLE));
		this.registerEvent(EventId.IMPORT_FIRST_REVIEWERS, (params) -> switchState(ApplicationState.FIRSTREVIEWER_IMPORT));
	}
}
