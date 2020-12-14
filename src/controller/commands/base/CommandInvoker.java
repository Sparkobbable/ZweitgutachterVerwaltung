package controller.commands.base;

import java.util.Stack;

import controller.ApplicationStateController;
import model.Model;
import model.enums.EventId;
import util.Log;
import view.View;

/**
 * Class responsible for invoking comments.
 * <p>
 * It implements an undo/redo-mechanism and updates the View's undoable and
 * redoable visualizations.
 *
 */
public class CommandInvoker {
	private final View view;
	private final Model model;
	private final ApplicationStateController applicationStateController;
	private final Stack<Command> undoStack;
	private final Stack<Command> redoStack;

	public CommandInvoker(View view, Model model, ApplicationStateController applicationStateController) {
		this.view = view;
		this.model = model;
		this.applicationStateController = applicationStateController;
		this.undoStack = new Stack<>();
		this.redoStack = new Stack<>();
		this.view.addEventHandler(EventId.UNDO, (params) -> this.undo());
		this.view.addEventHandler(EventId.REDO, (params) -> this.redo());

		

	}

	public void execute(Command command) {
		Log.info(this, "Executing command %s.", command);
		command.execute();
		this.undoStack.push(command);
		this.redoStack.clear();
		if (!command.isRevertible()) {
			this.undoStack.clear();
		}
		updateViewValueChanged();
	}

	public void undo() {
		if (this.undoStack.peek().getApplicationState() != this.model.getApplicationState()) {
			this.applicationStateController.switchState(this.undoStack.peek().getApplicationState());
		}
		Command command = this.undoStack.pop();
		Log.info(this, "Reverting command %s.", command);
		this.redoStack.push(command);
		command.revert();
		updateView();
	}

	public void redo() {
		if (this.redoStack.peek().getApplicationState() != model.getApplicationState()) {
			applicationStateController.switchState(this.redoStack.peek().getApplicationState());
		}
		Command command = this.redoStack.pop();
		Log.info(this, "Re-executing command %s.", command);
		this.undoStack.push(command);
		command.execute();
		updateViewValueChanged();
	}

	private void updateViewValueChanged() {
		updateView();
		this.view.notifyValuesChanged();
	}
	
	private void updateView() {
		this.view.setUndoable(!this.undoStack.isEmpty());
		this.view.setRedoable(!this.redoStack.isEmpty());
	}
}
