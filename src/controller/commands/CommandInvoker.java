package controller.commands;

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
		undoStack.push(command);
		redoStack.clear();
		updateView();
	}

	public void undo() {
		Command command = undoStack.pop();
		System.out.println("Reverting command " + command);
		redoStack.push(command);
		command.revert();
		updateView();
	}

	public void redo() {
		Command command = redoStack.pop();
		System.out.println("Re-executing command " + command);
		undoStack.push(command);
		command.execute();
		updateView();
	}
	
	private void updateView() {
		this.view.setUndoable(!this.undoStack.isEmpty());
		this.view.setRedoable(!this.redoStack.isEmpty());
	}
}
