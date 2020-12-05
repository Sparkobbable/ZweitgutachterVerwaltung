package controller.commands.base;

import model.enums.ApplicationState;

/**
 * Default implementation of the {@link Command} interface that already
 * implements {@link #getApplicationState()}
 *
 */
public abstract class DefaultCommand implements Command {

	private final ApplicationState applicationState;

	public DefaultCommand(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	@Override
	public ApplicationState getApplicationState() {
		return applicationState;
	}

}
