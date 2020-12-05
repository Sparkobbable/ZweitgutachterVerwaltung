package view.eventsources;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;


/**
 * {@link SingleEventSource} represented by a {@link JMenuItem}
 * <p>
 * Triggers events when a JMenuItem is clicked.
 *
 * TODO removed if unused
 * @see JFileChooser#addActionListener(java.awt.event.ActionListener)
 */
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
