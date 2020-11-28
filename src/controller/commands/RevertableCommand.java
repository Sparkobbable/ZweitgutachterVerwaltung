package controller.commands;

public abstract class RevertableCommand implements Command{

	@Override
	public final boolean isRevertible() {
		return true;
	}

}
