package model.data;

import java.util.Collection;
import java.util.HashSet;

import model.EventSource;
import model.enums.EventId;

public class EventSourceHandler {
	private Collection<EventSource> eventSources;

	public EventSourceHandler() {
		this.eventSources = new HashSet<>();
	}
	
	public void register(EventSource eventSource) {
		eventSources.add(eventSource);
	}
	
	public void registerAll(Collection<EventSource> eventSource) {
		eventSources.addAll(eventSource);
	}

	public void addEventHandler(EventId event, Runnable action) {
		eventSources.stream().filter(eventSource -> eventSource.canOmit(event))
				.forEach(eventSource -> eventSource.addEventHandler(event, action));
	}

	public boolean canOmit(EventId event) {
		return eventSources.stream().anyMatch(eventSource -> eventSource.canOmit(event));
	}

}
