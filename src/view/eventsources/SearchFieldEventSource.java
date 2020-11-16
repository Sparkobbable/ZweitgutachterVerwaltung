package view.eventsources;

import model.Action;
import model.enums.EventId;
import view.widgets.SearchField;

/**
 * Handles the role of SearchFields as EventSource
 */
public class SearchFieldEventSource extends SingleEventSource {
	
	private SearchField searchField;
	
	/**
	 * Creates a new EventSource with the given Parameters
	 * @param eventId Unique {@link EventId} for this event
	 * @param searchField Needs the exact {@link SearchField}
	 */
	public SearchFieldEventSource(EventId eventId, SearchField searchField) {
		super(eventId, () -> searchField.getSearchText());
		this.searchField = searchField;
	}

	@Override
	public void addEventHandler(Action action) {
		this.searchField.setSearchHandler(e -> action.perform(params), e -> action.perform(params));
	}

}
