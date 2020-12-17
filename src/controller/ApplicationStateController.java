package controller;

import java.util.Stack;

import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import util.Log;
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
	 * Navigation stack which stores the visited ApplicationStates
	 */
	private Stack<ApplicationState> visitedStates;

	public ApplicationStateController(Model model, View view) {
		this.view = view;
		this.model = model;
		this.visitedStates = new Stack<>();

		this.view.addEventHandler(EventId.HOME, (params) -> switchState(ApplicationState.HOME));
		this.view.addEventHandler(EventId.BACK, (params) -> switchToLastVisitedState());

	}

	/**
	 * Switches to the last visited {@link ApplicationState}
	 */
	public void switchToLastVisitedState() {
		this.switchState(this.getLastVisitedState());
	}

	/**
	 * Switches to a new ApplicationState
	 * 
	 * @param state The new ApplicationState
	 */
	public void switchState(ApplicationState applicationState) {
		Log.info(this.getClass().getName(), "Switched to ApplicationState: %s", applicationState.name());
		this.model.setApplicationState(applicationState);

		if (this.visitedStates.empty() || !this.visitedStates.peek().isEqual(applicationState)) {
			this.visitedStates.push(applicationState);
		}
	}

	/**
	 * Sets the first ApplicationState in the navigation stack to the one before and
	 * returns it
	 * 
	 * @return ApplicationState Last visited ApplicatonState
	 */
	private ApplicationState getLastVisitedState() {
		if (this.visitedStates.empty()) {
			return ApplicationState.HOME;
		}
		// remove this applicationState from the stack
		this.visitedStates.pop();
		
		// return to the last visited applicationState or HOME, if there is none
		if (this.visitedStates.empty()) {
			return ApplicationState.HOME;
		} else {
			return this.visitedStates.peek();
		}
	}
}
