package controller.commands.base;

public abstract class RevertableCommand implements Command{

	@Override
	public final boolean isRevertible() {
		return true;
	}

}
