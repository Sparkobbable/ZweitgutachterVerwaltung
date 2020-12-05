package controller.events;

import java.util.function.Supplier;

/**
 * Interface that represents an action that can be performed with a given set of
 * parameters
 *
 */
public interface Action {

	/**
	 * Perform this action. This method should internally retrieve the parameter
	 * values from the given set of {@link Supplier}s
	 * 
	 * @param parameters An array of parameter suppliers
	 */
	public void perform(Supplier<?>... parameters);

}
