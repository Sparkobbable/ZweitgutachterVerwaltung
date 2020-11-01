package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import model.Action;
import model.EventSource;
import model.data.CompositeEventSource;
import model.enums.EventId;
import view.eventsources.MenuItemEventSource;

public class MenuBarHandler extends JMenuBar implements EventSource {

	private static final long serialVersionUID = 1L;
	private CompositeEventSource eventSourceHandler;
	private JMenu back; // TODO replace with JMenuItem

	public MenuBarHandler() {
		this.eventSourceHandler = new CompositeEventSource();
		this.back = new JMenu("Zurück");
		this.registerEventSources();
	}

	public void init() {
		this.add(back);
	}

	protected void registerEventSources() {
		this.eventSourceHandler.register(new MenuItemEventSource(EventId.BACK, back));
	}

	/*
	 * -------------------------------------------------------------------------------
	 * | Delegate methods to the responsible Objects 
	 * -------------------------------------------------------------------------------
	 */
	@Override
	public void addEventHandler(EventId eventId, Action action) {
		this.eventSourceHandler.addEventHandler(eventId, action);
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.eventSourceHandler.canOmit(eventId);
	}
}
