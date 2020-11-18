package view.eventsources;

import javax.swing.JMenuItem;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;

public class MenuItemEventSource extends SingleEventSource {
	private JMenuItem menuItem;

	public MenuItemEventSource(EventId eventId, JMenuItem menuItem) {
		super(eventId);
		this.menuItem = menuItem;
	}

	@Override
	public void addEventHandler(Action action) {
		menuItem.addActionListener(e -> action.perform());
	}

}
