package controller.commands.base;

import model.enums.ApplicationState;

public abstract class IrevertibleCommand extends DefaultCommand {

	public IrevertibleCommand(ApplicationState applicationState) {
		super(applicationState);
	}

	@Override
	public final void revert() {
		throw new UnsupportedOperationException("Revert is not support for unrevertable commands.");
	}

	@Override
	public final boolean isRevertible() {
		return false;
	}

}
