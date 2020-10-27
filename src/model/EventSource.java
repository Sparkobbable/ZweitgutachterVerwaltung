package model;

import model.enums.EventId;

public interface EventSource {

	/**
	 * Binds an action to this EventSource that is called whenever this EventSource
	 * omits the specified element
	 * 
	 * @param eventId The id of the event
	 * @param action  Action that shall be performed whenever an event with the
	 *                given eventId is ommitted
	 */
	public void addEventHandler(EventId eventId, Action action);

	/**
	 * Detects whether this EventSource can omit the event with the given eventId
	 * 
	 * @param eventId The id of the event
	 * @return true if and only if this EventSource can omit the event with the
	 *         given eventId
	 */
	public boolean canOmit(EventId eventId);
}
