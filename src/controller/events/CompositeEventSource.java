package controller.events;

import java.util.Collection;
import java.util.HashSet;

import model.enums.EventId;

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
