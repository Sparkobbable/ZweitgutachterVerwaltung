package controller.UndoRedo;

public interface Command {

	
	public void execute();
	public void revert();
}
