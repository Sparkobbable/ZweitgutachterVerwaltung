package controller.commands.model;

import java.util.List;

import controller.commands.base.Command;
import controller.commands.base.IrevertibleCommand;
import model.Model;
import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.persistence.PersistenceHandler;

/**
 * {@link Command} that load a SystemyState
 * 
 */
public class LoadSystemStateCommand extends IrevertibleCommand {

	private final PersistenceHandler persistenceHandler;
	private final Model model;
	private boolean override;

	public LoadSystemStateCommand(PersistenceHandler persistenceHandler, Model model, boolean override) {
		super(model.getApplicationState());
		this.model = model;
		this.override = override;
		this.persistenceHandler = persistenceHandler;
	}

	@Override
	public void execute() {
		Pair<List<Reviewer>, List<BachelorThesis>> load = persistenceHandler.load();
		if (this.override) {
			this.model.overrideReviewers(load.getLeft());
			this.model.overrideBachelorTheses(load.getRight());
		} else {
			this.model.addReviewers(load.getLeft());
			this.model.addBachelorTheses(load.getRight());
		}
	}

}
