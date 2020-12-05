package controller.events;

import java.util.Collection;
import java.util.HashSet;

import model.enums.EventId;

/**
 * An {@link EventSource} composed by a set of internal EventSources
 * <p>
 * An event is omitted from this EventSource whenever it is omitted by any of
 * the internal EventSources.
 * <p>
 * Technically, it redirects all incoming
 * {@link #addEventHandler(EventId, Action)} requests to the underlying
 * EventSources.
 */
public class CompositeEventSource implements EventSource {
	private Collection<EventSource> eventSources;

	public CompositeEventSource() {
		this.eventSources = new HashSet<>();
	}

	public void register(EventSource eventSource) {
		this.eventSources.add(eventSource);
	}

	public void registerAll(Collection<EventSource> eventSource) {
		this.eventSources.addAll(eventSource);
	}

	public void addEventHandler(EventId eventId, Action action) {
		this.validateEventId(eventId);
		this.eventSources.stream().filter(eventSource -> eventSource.canOmit(eventId))
				.forEach(eventSource -> eventSource.addEventHandler(eventId, action));
	}

	public boolean canOmit(EventId eventId) {
		return this.eventSources.stream().anyMatch(eventSource -> eventSource.canOmit(eventId));
	}

}
