package controller.statecontrollers;

import java.util.Collections;
import java.util.Set;

import model.Action;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

/**
 * Controls the Application in a given ApplicationState
 */
public abstract class AbstractStateController {

	// referenced objects
	protected View view;
	private ApplicationStateController applicationStateController;
	protected Model model;

	/**
	 * The ApplicationState that this StateController is Responsible for
	 */
	protected Set<ApplicationState> states;

	/**
	 * Creates a StateController for the given states and registers actions for each
	 * Event that can be omitted in this ApplicationState
	 * <p>
	 * This StateController may be responsible for multiple
	 * {@link ApplicationState}s if they produce the same events and if those events
	 * shall be treated identically.
	 * 
	 * @param states
	 * @param view
	 * @param applicationStateController
	 */
	public AbstractStateController(Set<ApplicationState> states, View view,
			ApplicationStateController applicationStateController, Model model) {
		this.states = states;
		this.view = view;
		this.applicationStateController = applicationStateController;
		this.model = model;

		this.registerEvents();
	}

	/**
	 * Creates a StateController for the given states and registers actions for each
	 * Event that can be omitted in this ApplicationState
	 * 
	 * @param states
	 * @param view
	 * @param applicationStateController
	 */
	public AbstractStateController(ApplicationState state, View view,
			ApplicationStateController applicationStateController, Model model) {
		this.states = Collections.singleton(state);
		this.view = view;
		this.applicationStateController = applicationStateController;
		this.model = model;

		this.registerEvents();
	}

	/**
	 * Registers an action that is called whenever this event is omitted while the
	 * Application in each state in which this Controller is responsible
	 * 
	 * @param eventId Event Id whose omittance shall be observed
	 * @param action  Action performed when this event is omited in this state
	 */
	public void registerEvent(EventId eventId, Action action) {
		this.states.forEach(state -> this.view.addEventHandler(state, eventId, action));
	}

	/**
	 * Switches to a new ApplicationState
	 * 
	 * @param state The new ApplicationState
	 */
	public void switchState(ApplicationState state) {
		applicationStateController.switchState(state);
	}

	/**
	 * Switches to the last visited {@link ApplicationState}
	 */
	protected void switchToLastVisitedState() {
		applicationStateController.switchToLastVisitedState();
	}

	/**
	 * Registers each event possibly occurring in this state by defining an action
	 * that is performed on that event.
	 * <p>
	 * This method is automatically called in the constructor and should <b>not</b>
	 * be called elsewhere.
	 */
	protected abstract void registerEvents();

}
