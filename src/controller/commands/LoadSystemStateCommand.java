package controller.commands;

import model.persistence.PersistenceHandler;

public class LoadSystemStateCommand extends UnrevertableCommand {

	private PersistenceHandler persistenceHandler;

	public LoadSystemStateCommand(PersistenceHandler persistenceHandler) {
		this.persistenceHandler = persistenceHandler;
	}

	@Override
	public void execute() {
		try {
			persistenceHandler.load();
		} catch (Exception e) {
			// don't check this exception at compile time
			throw new RuntimeException(e);
		}
	}

}
