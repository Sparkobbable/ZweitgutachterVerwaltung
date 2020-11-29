package controller.commands.base;

import java.util.List;

public class BatchCommand extends RevertableCommand{

	private List<Command> commands;
	
	public BatchCommand(List<Command> commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {
		this.commands.forEach(Command::execute);
	}

	@Override
	public void revert() {
		//revert in inverse order
		for (int i = this.commands.size() - 1; i >= 0; i--) {
			this.commands.get(i).revert();
		}
	}
	
	
		
}
