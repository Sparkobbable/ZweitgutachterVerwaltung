package controller.statecontrollers;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
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

	/**
	 * Navigation stack which stores the visited ApplicationStates
	 */
	private Stack<ApplicationState> visitedStates;
	
	public ApplicationStateController(Model model, View view) {
		this.view = view;
		this.model = model;
		visitedStates = new Stack<>();
		stateControllers = new HashMap<>();
		stateControllers.put(ApplicationState.HOME, new HomeStateController(view, this, model));
		stateControllers.put(ApplicationState.REVIEWER_OVERVIEW,
				new ReviewerOverviewStateController(view, this, model));
		stateControllers.put(ApplicationState.REVIEWER_EDITOR, new ReviewerEditorStateController(view, this, model));
		stateControllers.put(ApplicationState.STATE_CHOOSER, new StateChooserStateController(view, this, model));
		stateControllers.put(ApplicationState.THESIS_ASSIGNMENT, new ThesisAssignmentStateController(view, this, model));

		this.view.atAnyState().addEventHandler(EventId.BACK,
				(params) -> this.switchState(this.getLastVisitedState()));
	}

	/**
	 * Switches to a new ApplicationState
	 * 
	 * @param state The new ApplicationState
	 */
	public void switchState(ApplicationState applicationState) {
		Logger.getLogger(Controller.class.getName())
				.info(String.format("Switched to ApplicationState: %s", applicationState));
		this.model.setApplicationState(applicationState);
		view.switchState(applicationState); // TODO remove, add observer
		
		if(this.visitedStates.empty() || !this.visitedStates.peek().equals(applicationState)) {
			this.visitedStates.push(applicationState);
		}
	}
	
	/**
	 * Sets the first ApplicationState in the navigation stack to the one before and returns it
	 * 
	 * @return ApplicationState Last visited ApplicatonState
	 */
	private ApplicationState getLastVisitedState() {
		if(this.visitedStates.empty()) {
			return ApplicationState.HOME;
		} else {
			this.visitedStates.pop().toString();
			if(this.visitedStates.empty()) {
				return ApplicationState.HOME;
			} else {
				return this.visitedStates.peek();
			}
		}
	}
}
