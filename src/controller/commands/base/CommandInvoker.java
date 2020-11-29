package controller.commands.base;

import java.util.Stack;

import model.enums.EventId;
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
		System.out.println("Executing command " + command);
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
		System.out.println("Reverting command " + command);
		this.redoStack.push(command);
		command.revert();
		updateView();
	}

	public void redo() {
		Command command = this.redoStack.pop();
		System.out.println("Re-executing command " + command);
		this.undoStack.push(command);
		command.execute();
		updateView();
	}
	
	private void updateView() {
		this.view.setUndoable(!this.undoStack.isEmpty());
		this.view.setRedoable(!this.redoStack.isEmpty());
	}
}
