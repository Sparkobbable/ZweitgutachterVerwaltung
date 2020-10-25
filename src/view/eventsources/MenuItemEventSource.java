package view.eventsources;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.enums.EventId;

public class MenuItemEventSource extends SingleEventSource {
	private JMenu menuItem; // TODO change to JMenuItem

	public MenuItemEventSource(EventId eventId, JMenu menuItem) {
		super(eventId);
		this.menuItem = menuItem;
	}

	@Override
	public void addEventHandler(EventId eventId, Runnable action) {
		if (!this.canOmit(eventId)) {
			throw new IllegalArgumentException(
					String.format("JMenuItem %s will never omit event %s. EventHandler could not be added",
							menuItem.getText(), eventId));
		}

		// TODO don't use JMenus like that because this is abuse and leads to weird
		// behavior
		menuItem.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				action.run();
				menuItem.setSelected(false); // uaagh
			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
	}

}
