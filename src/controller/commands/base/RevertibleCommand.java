package controller.commands.base;

import model.enums.ApplicationState;

/**
 * {@link DefaultCommand} that can be reverted.
 *
 */
public abstract class RevertibleCommand extends DefaultCommand{

	public RevertibleCommand(ApplicationState applicationState) {
		super(applicationState);
	}

	/**
	 * @return true by this class's definition
	 * @see RevertibleCommand
	 */
	@Override
	public final boolean isRevertible() {
		return true;
	}

}
