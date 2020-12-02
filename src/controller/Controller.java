package controller;

import java.util.HashSet;
import java.util.Set;

import controller.commands.base.Command;
import controller.commands.base.CommandInvoker;
import controller.statecontrollers.AbstractStateController;
import controller.statecontrollers.CollaborationOverviewPieChartStateController;
import controller.statecontrollers.CollaborationOverviewTableStateController;
import controller.statecontrollers.HomeStateController;
import controller.statecontrollers.ReviewerEditorStateController;
import controller.statecontrollers.ReviewerOverviewStateController;
import controller.statecontrollers.StateChooserStateController;
import controller.statecontrollers.ThesesOverviewStateController;
import controller.statecontrollers.ThesisAssignmentStateController;
import model.Model;
import model.enums.ApplicationState;
import view.View;

/**
 * Controls the application flow
 *
 */
public class Controller {
	private Model model;
	private View view;
	private ApplicationStateController applicationStateController;
	private CommandInvoker commandInvoker;

	/**
	 * Set of responsible controller for a given state
	 */
	private Set<AbstractStateController> stateControllers;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		this.applicationStateController = new ApplicationStateController(model, view);
		this.commandInvoker = new CommandInvoker(this.view, this.model, this.applicationStateController);

		this.initializeStateControllers();
	}

	protected void initializeStateControllers() {
		stateControllers = new HashSet<>();
		stateControllers.add(new HomeStateController(this.view, this, this.model));
		stateControllers.add(new ReviewerOverviewStateController(this.view, this, this.model));
		stateControllers.add(new ThesesOverviewStateController(this.view, this, this.model));
		stateControllers.add(new ReviewerEditorStateController(this.view, this, this.model));
		stateControllers.add(new StateChooserStateController(this.view, this, this.model));
		stateControllers.add(new ThesisAssignmentStateController(this.view, this, this.model));
		stateControllers.add(new CollaborationOverviewTableStateController(this.view, this, this.model));
		stateControllers.add(new CollaborationOverviewPieChartStateController(this.view, this, this.model));
	}

	/**
	 * @see ApplicationStateController#switchState(ApplicationState)
	 * @param state
	 */
	public void switchState(ApplicationState state) {
		this.applicationStateController.switchState(state);
	}

	/**
	 * @see ApplicationStateController#switchToLastVisitedState()
	 */
	public void switchToLastVisitedState() {
		this.applicationStateController.switchToLastVisitedState();
	}

	/**
	 * @see CommandInvoker#execute(Command)
	 * @param command
	 */
	public void execute(Command command) {
		this.commandInvoker.execute(command);
	}

}
