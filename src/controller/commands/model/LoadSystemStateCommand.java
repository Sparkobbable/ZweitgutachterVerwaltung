package controller.commands.model;

import java.util.List;

import controller.commands.base.IrevertibleCommand;
import model.Model;
import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
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
		Pair<List<Reviewer>, List<BachelorThesis>> load = persistenceHandler.load();
		this.model.overrideReviewers(load.getLeft());
		this.model.overrideBachelorTheses(load.getRight());
	}

}
