package view.eventsources;

import java.util.function.Supplier;

import model.EventSource;
import model.enums.EventId;

/**
 * Comfort class that manages an EventSource that can only omit one Event
 *
 */
public abstract class SingleEventSource implements EventSource {

	private EventId eventId;
	protected Supplier<?>[] params;

	public SingleEventSource(EventId eventId, Supplier<?>... params) {
		this.eventId = eventId;
		this.params = params;
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.eventId == eventId;
	}


}
