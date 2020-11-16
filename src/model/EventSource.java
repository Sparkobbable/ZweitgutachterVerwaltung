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

	/**
	 * Validates that adding an Action for the given EventId is theoretically possible 
	 * 
	 * @param eventId The id of the event
	 * @throws IllegalArgumentException if Events with the given EventId can't be omitted by this EventSource
	 * @return true if and only if this EventSource can omit the event with the
	 *         given eventId
	 * 
	 */
	public default void validateEventId(EventId eventId) {
		if (!this.canOmit(eventId)) {
			throw new IllegalArgumentException(String.format(
					"The EventSource will never omit Events with eventId %s. EventHandler could not be added to %s.",
					eventId, this));
		}
	}
}
