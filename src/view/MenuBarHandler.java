package view;

import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Action;
import model.EventSource;
import model.data.CompositeEventSource;
import model.enums.EventId;
import view.eventsources.MenuItemEventSource;

public class MenuBarHandler extends JMenuBar implements EventSource {

	private static final long serialVersionUID = 1L;
	private CompositeEventSource eventSourceHandler;
	private JMenuItem back;

	public MenuBarHandler() {
		this.eventSourceHandler = new CompositeEventSource();
		this.back = new JMenuItem("Zurück");
		this.back.setMaximumSize(new Dimension((int) this.back.getPreferredSize().getWidth(),
				(int) this.back.getMaximumSize().getHeight()));
		this.add(back);
		this.registerEventSources();
	}

	protected void registerEventSources() {
		this.eventSourceHandler.register(new MenuItemEventSource(EventId.BACK, back));
	}

	/*
	 * -----------------------------------------------------------------------------
	 * -- | Delegate methods to the responsible Objects
	 * -----------------------------------------------------------------------------
	 * --
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
