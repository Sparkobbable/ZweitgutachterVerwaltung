package controller.events;

import java.util.function.Supplier;

public interface Action {

	public void perform(Supplier<?>... parameters);
	
}
