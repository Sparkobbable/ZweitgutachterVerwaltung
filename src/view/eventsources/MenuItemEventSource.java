package view.eventsources;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.Action;
import model.enums.EventId;

public class MenuItemEventSource extends SingleEventSource {
	private JMenu menuItem; // TODO change to JMenuItem

	public MenuItemEventSource(EventId eventId, JMenu menuItem) {
		super(eventId);
		this.menuItem = menuItem;
	}

	@Override
	public void addEventHandler(Action action) {
		// TODO don't use JMenus like that because this is abuse and leads to weird
		// behavior
		menuItem.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				action.perform();
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
