package controller.commands.base;

import model.enums.ApplicationState;

public abstract class DefaultCommand implements Command{

	private final ApplicationState applicationState;
	 
	public DefaultCommand(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	@Override
	public ApplicationState getApplicationState() {
		return applicationState;
	}

}
