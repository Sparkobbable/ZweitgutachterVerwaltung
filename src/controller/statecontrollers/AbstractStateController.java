package controller.statecontrollers;

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
	protected ApplicationState state;

	/**
	 * Creates a StateController for the given state and registers actions for each
	 * Event that can be omitted in this ApplicationState
	 * 
	 * @param state
	 * @param view
	 * @param applicationStateController
	 */
	public AbstractStateController(ApplicationState state, View view,
			ApplicationStateController applicationStateController, Model model) {
		this.state = state;
		this.view = view;
		this.applicationStateController = applicationStateController;
		this.model = model;
		
		this.registerEvents();
	}

	/**
	 * registers an action that is called whenever this event is omitted while the
	 * Application in the state in which this Controller is responsible
	 * 
	 * @param eventId Event Id whose omittance shall be observed
	 * @param action Action performed when this event is omited in this state
	 */
	public void registerEvent(EventId eventId, Action action) {
		this.view.atState(state).addEventHandler(eventId, action);
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
