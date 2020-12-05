package controller.events;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import model.enums.EventId;

/**
 * {@link SingleEventSource} that can be triggered manually by calling the
 * {@link #trigger()} method
 */
public class CustomEventSource extends SingleEventSource {

	private Set<Action> actions;

	public CustomEventSource(EventId eventId, Supplier<?>... params) {
		super(eventId, params);
		this.actions = new HashSet<>();
	}

	@Override
	public void addEventHandler(Action action) {
		this.actions.add(action);
	}

	public void trigger() {
		this.actions.forEach(a -> a.perform(params));
	}

}
