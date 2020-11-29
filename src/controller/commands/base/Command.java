package controller.commands.base;

public interface Command {
	
	public void execute();
	public void revert();
	
	public boolean isRevertible();
}
