package controller.UndoRedo;

import java.util.Stack;

import model.enums.EventId;
import view.View;

public class CommandExecutionController {
	private View view;
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();

	public CommandExecutionController(View view) {
		this.view = view;
		this.view.addEventHandler(EventId.UNDO, (params) -> this.undo());
		this.view.addEventHandler(EventId.REDO, (params) -> this.redo());

	}

	public void execute(Command command) {

		command.execute();
		undoStack.push(command);
		redoStack.clear();
		updateView();
	}

	public void undo() {
		Command command = undoStack.pop();
		redoStack.push(command);
		command.revert();
		updateView();
	}

	private void updateView() {
		this.view.setUndoable(!this.undoStack.isEmpty());
		this.view.setRedoable(!this.redoStack.isEmpty());
	}

	public void redo() {
		Command command = redoStack.pop();
		undoStack.push(command);
		command.execute();
		updateView();
	}
}
