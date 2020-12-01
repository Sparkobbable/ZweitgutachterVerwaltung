package controller.commands.base;

import java.util.ArrayList;
import java.util.List;

public class BatchCommand extends DefaultCommand {

	private final List<Command> commands;
	private final boolean revertible;

	public BatchCommand(List<Command> commands) {
		super(commands.stream().findAny().orElseThrow().getApplicationState());
		if (commands.stream().anyMatch(c -> c.getApplicationState() != this.getApplicationState())) {
			throw new IllegalArgumentException("Only one ApplicationState is allowed.");
		}
		this.commands = new ArrayList<>(commands);
		this.revertible = this.commands.stream().noneMatch(c -> !c.isRevertible());
	}

	@Override
	public void execute() {
		this.commands.forEach(Command::execute);
	}

	@Override
	public void revert() {
		// revert in inverse order
		for (int i = this.commands.size() - 1; i >= 0; i--) {
			this.commands.get(i).revert();
		}
	}

	@Override
	public boolean isRevertible() {
		return revertible;
	}

}
