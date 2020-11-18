package view;

import javax.swing.JButton;
import javax.swing.JMenuBar;

import controller.events.Action;
import controller.events.CompositeEventSource;
import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;

public class MenuBarHandler extends JMenuBar implements EventSource {

	private static final long serialVersionUID = 1L;
	private CompositeEventSource eventSourceHandler;
	private JButton back;
	private JButton undo;
	private JButton redo;

	public MenuBarHandler() {
		this.eventSourceHandler = new CompositeEventSource();
		this.back = new JButton("Zurück");
		this.undo = new JButton("Rückgängig");
		this.redo = new JButton("Wiederherstellen");
		designButton(this.back);
		designButton(this.undo);
		designButton(this.redo);
		undo.setEnabled(false);
		redo.setEnabled(false);
		this.add(back);
		this.add(undo);
		this.add(redo);

		this.registerEventSources();
	}

	private void designButton(JButton button) {
		button.setOpaque(true);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}

	protected void registerEventSources() {
		this.eventSourceHandler.register(new ButtonEventSource(EventId.BACK, back));
		this.eventSourceHandler.register(new ButtonEventSource(EventId.UNDO, undo));
		this.eventSourceHandler.register(new ButtonEventSource(EventId.REDO, redo));
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

	public void setUndoable(boolean b) {
		this.undo.setEnabled(b);
	}

	public void setRedoable(boolean b) {
		this.redo.setEnabled(b);
	}
}
