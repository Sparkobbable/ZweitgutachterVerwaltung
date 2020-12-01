package controller.commands.base;

import model.enums.ApplicationState;

public abstract class RevertibleCommand extends DefaultCommand{

	public RevertibleCommand(ApplicationState applicationState) {
		super(applicationState);
	}

	@Override
	public final boolean isRevertible() {
		return true;
	}

}
