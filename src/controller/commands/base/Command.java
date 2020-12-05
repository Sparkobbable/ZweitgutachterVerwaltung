package controller.commands.base;

import model.Model;
import model.enums.ApplicationState;

/**
 * Encapsulates an action that affects the {@link Model}.
 * 
 */
public interface Command {

	/**
	 * Execute this command.
	 */
	public void execute();

	/**
	 * Revert this command.
	 * <p>
	 * Prerequisites:
	 * <ul>
	 * <li>The {@link #execute()} method has been called
	 * <li>It is assumed that the system state is the same as if the execute command
	 * had just been called.
	 * 
	 * 
	 */
	public void revert();

	/**
	 * @return True if and only if this Command can be reverted.
	 * @see #revert()
	 */
	public boolean isRevertible();

	/**
	 * Returns the ApplicationState relvevant for this command.
	 * <p>
	 * Any invoker should ensure that the current {@link ApplicationState} equals
	 * this method's returned ApplicationState to increase usability
	 * 
	 * @return The ApplicationState relevant for this command.
	 */
	public ApplicationState getApplicationState();
}
