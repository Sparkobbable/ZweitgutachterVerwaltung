package controller.commands.base;

import model.enums.ApplicationState;

/**
 * {@link DefaultCommand} that cannot be reverted.
 *
 */
public abstract class IrevertibleCommand extends DefaultCommand {

	public IrevertibleCommand(ApplicationState applicationState) {
		super(applicationState);
	}

	/**
	 * This command cannot be reverted, so calling this method results in an
	 * {@link UnsupportedOperationException}
	 */
	@Override
	public final void revert() {
		throw new UnsupportedOperationException("Revert is not support for unrevertable commands.");
	}

	/**
	 * @return false by this class's definition
	 * @see IrevertibleCommand
	 */
	@Override
	public final boolean isRevertible() {
		return false;
	}

}
