package controller.commands;

public interface Command {
	
	public void execute();
	public void revert();
	
	public boolean isRevertible();
}
