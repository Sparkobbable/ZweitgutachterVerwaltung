package controller.commands.base;

import model.enums.ApplicationState;

public interface Command {
	
	public void execute();
	public void revert();
	public boolean isRevertible();
	public ApplicationState getApplicationState();
}
