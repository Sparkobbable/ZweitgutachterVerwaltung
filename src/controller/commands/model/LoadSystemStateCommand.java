package controller.commands.model;

import controller.commands.base.IrevertibleCommand;
import model.Model;
import model.persistence.PersistenceHandler;

public class LoadSystemStateCommand extends IrevertibleCommand {

	private PersistenceHandler persistenceHandler;
	private Model model;
	
	public LoadSystemStateCommand(PersistenceHandler persistenceHandler, Model model) {
		super(model.getApplicationState());
		this.model = model;
		this.persistenceHandler = persistenceHandler;
	}

	@Override
	public void execute() {
		try {
			this.model.clear();
			persistenceHandler.load();
		} catch (Exception e) {
			// don't check this exception at compile time
			throw new RuntimeException(e);
		}
	}

}
