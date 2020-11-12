package controller.statecontrollers;

import java.util.HashSet;
import java.util.Set;
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
	 * Set of responsible controller for a given state
	 */
	private Set<AbstractStateController> stateControllers;

	/**
	 * Navigation stack which stores the visited ApplicationStates
	 */
	private Stack<ApplicationState> visitedStates;

	public ApplicationStateController(Model model, View view) {
		this.view = view;
		this.model = model;
		visitedStates = new Stack<>();
		
		stateControllers = new HashSet<>();
		stateControllers.add(new HomeStateController(view, this, model));
		stateControllers.add(new ReviewerOverviewStateController(view, this, model));
		stateControllers.add(new ThesesOverviewStateController(view, this, model));
		stateControllers.add(new ReviewerEditorStateController(view, this, model));
		stateControllers.add(new StateChooserStateController(view, this, model));
		stateControllers.add(new ThesisAssignmentStateController(view, this, model));
		stateControllers.add(new CollaborationOverviewStateController(view, this, model));
		stateControllers.add(new CollaborationOverviewStateController(view, this, model));

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
		Logger.getLogger(Controller.class.getName())
				.info(String.format("Switched to ApplicationState: %s", applicationState));
		this.model.setApplicationState(applicationState);

		if (this.visitedStates.empty() || !this.visitedStates.peek().equals(applicationState)) {
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
		} else {
			this.visitedStates.pop().toString();
			if (this.visitedStates.empty()) {
				return ApplicationState.HOME;
			} else {
				return this.visitedStates.peek();
			}
		}
	}
}
