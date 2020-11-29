package controller.commands.base;

import java.util.Stack;

import model.enums.EventId;
import util.Log;
import view.View;

public class CommandInvoker {
	private View view;
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();

	public CommandInvoker(View view) {
		this.view = view;
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
		updateView();
	}

	public void undo() {
		Command command = undoStack.pop();
		Log.info(this, "Reverting command %s.", command);
		this.redoStack.push(command);
		command.revert();
		updateView();
	}

	public void redo() {
		Command command = this.redoStack.pop();
		Log.info(this, "Re-executing command %s.", command);
		this.undoStack.push(command);
		command.execute();
		updateView();
	}
	
	private void updateView() {
		this.view.setUndoable(!this.undoStack.isEmpty());
		this.view.setRedoable(!this.redoStack.isEmpty());
	}
}
