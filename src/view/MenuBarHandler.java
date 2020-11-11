package view;

import javax.swing.JButton;
import javax.swing.JMenuBar;

import model.Action;
import model.EventSource;
import model.data.CompositeEventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;

public class MenuBarHandler extends JMenuBar implements EventSource {

	private static final long serialVersionUID = 1L;
	private CompositeEventSource eventSourceHandler;
	private JButton back;

	public MenuBarHandler() {
		this.eventSourceHandler = new CompositeEventSource();
		this.back = new JButton("Zurück");
		this.back.setOpaque(true);
		this.back.setContentAreaFilled(false);
		this.back.setBorderPainted(false);
		this.back.setFocusable(false);
		this.add(back);

		this.registerEventSources();
	}

	protected void registerEventSources() {
		this.eventSourceHandler.register(new ButtonEventSource(EventId.BACK, back));

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
