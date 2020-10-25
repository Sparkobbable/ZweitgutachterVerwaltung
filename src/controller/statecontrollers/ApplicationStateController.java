package controller.statecontrollers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import view.View;

/**
 * Controls the Application's state and manages the controller responsible for
 * each UI state
 */
public class ApplicationStateController {

	// Referenced objects
	private View view;
	private Model model;

	/**
	 * Map which stores the responsible controller for a given state
	 */
	private Map<ApplicationState, AbstractStateController> stateControllers;

	public ApplicationStateController(Model model, View view) {
		this.view = view;
		this.model = model;
		stateControllers = new HashMap<>();
		stateControllers.put(ApplicationState.HOME, new HomeStateController(view, this, model));
		stateControllers.put(ApplicationState.REVIEWER_OVERVIEW,
				new ReviewerOverviewStateController(view, this, model));
	}

	/**
	 * Switches to a new ApplicationState
	 * @param state The new ApplicationState
	 */
	public void switchState(ApplicationState state) {
		Logger.getLogger(Controller.class.getName()).info(String.format("Switched to State: %s", state));
		view.switchState(state);
	}
}
