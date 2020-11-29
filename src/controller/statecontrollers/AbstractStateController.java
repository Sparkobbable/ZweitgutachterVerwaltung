package controller.statecontrollers;

import controller.Controller;
import controller.commands.base.Command;
import controller.events.Action;
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
	private Controller controller;
	protected Model model;

	/**
	 * The ApplicationState that this StateController is Responsible for
	 */
	protected ApplicationState state;

	/**
	 * Creates a StateController for the given states and registers actions for each
	 * Event that can be omitted in this ApplicationState
	 * 
	 * @param states
	 * @param view
	 * @param controller
	 */
	public AbstractStateController(ApplicationState state, View view,
			Controller controller, Model model) {
		this.state = state;
		this.view = view;
		this.controller = controller;
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
		this.view.addEventHandler(this.state, eventId, action);
	}

	/**
	 * Switches to a new ApplicationState
	 * 
	 * @param state The new ApplicationState
	 */
	public void switchState(ApplicationState state) {
		controller.switchState(state);
	}

	/**
	 * Switches to the last visited {@link ApplicationState}
	 */
	protected void switchToLastVisitedState() {
		controller.switchToLastVisitedState();
	}

	/**
	 * Registers each event possibly occurring in this state by defining an action
	 * that is performed on that event.
	 * <p>
	 * This method is automatically called in the constructor and should <b>not</b>
	 * be called elsewhere.
	 */
	protected abstract void registerEvents();
	
	protected void execute(Command command) {
		this.controller.execute(command);
	}
	
}
