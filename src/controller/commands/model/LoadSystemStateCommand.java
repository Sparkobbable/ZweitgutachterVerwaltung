package controller.commands.model;

import java.util.List;

import com.sun.tools.javac.util.Pair;

import controller.commands.base.IrevertibleCommand;
import model.Model;
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
		try {
			Pair<List<Reviewer>, List<BachelorThesis>> load = persistenceHandler.load();
			System.out.println("Loaded:");
			System.out.println(load.fst);
			System.out.println(load.snd);
			this.model.overrideReviewers(load.fst);
			this.model.overrideBachelorTheses(load.snd);
		} catch (Exception e) {
			// don't check this exception at compile time
			throw new RuntimeException(e);
		}
	}

}
