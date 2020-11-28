package controller.commands;

public abstract class UnrevertableCommand implements Command{

	@Override
	public final void revert() {
		throw new UnsupportedOperationException("Revert is not support for unrevertable commands.");
	}

	@Override
	public final boolean isRevertible() {
		return false;
	}

}
