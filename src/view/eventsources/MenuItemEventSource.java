package view.eventsources;

import javax.swing.JMenuItem;

import model.Action;
import model.enums.EventId;

public class MenuItemEventSource extends SingleEventSource {
	private JMenuItem menuItem; // TODO change to JMenuItem

	public MenuItemEventSource(EventId eventId, JMenuItem menuItem) {
		super(eventId);
		this.menuItem = menuItem;
	}

	@Override
	public void addEventHandler(Action action) {
		// TODO don't use JMenus like that because this is abuse and leads to weird
		// behavior
		menuItem.addActionListener(e -> action.perform());
	}

}
