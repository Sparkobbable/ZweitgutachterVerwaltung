package view.eventsources;

import model.EventSource;
import model.enums.EventId;

/**
 * Comfort class that manages an EventSource that can only omit one Event
 *
 */
public abstract class SingleEventSource implements EventSource {

	private EventId eventId;

	public SingleEventSource(EventId eventId) {
		this.eventId = eventId;
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.eventId == eventId;
	}


}
