package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import controller.events.Action;
import controller.events.CompositeEventSource;
import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.widgets.ButtonFactory;

/**
 * Responsible for visualization and event handling for a menu bar.
 *
 */
@SuppressWarnings("serial") // this class should not be serialized
public class MenuBarHandler extends JMenuBar implements EventSource {

	private CompositeEventSource eventSourceHandler;
	private ButtonFactory buttonFactory;
	private JButton back;
	private JButton undo;
	private JButton redo;
	private JButton valuesChanged;

	public MenuBarHandler() {
		this.eventSourceHandler = new CompositeEventSource();
		this.buttonFactory = ButtonFactory.getInstance();
		this.back = this.buttonFactory.createMenuButton("Zurück");
		this.undo = this.buttonFactory.createMenuButton("Rückgängig");
		this.redo = this.buttonFactory.createMenuButton("Wiederherstellen");
		this.valuesChanged = this.buttonFactory.createMenuButton("Alle Eingaben wurden gespeichert", Color.RED);
		this.valuesChanged.setVisible(false);
		undo.setEnabled(false);
		redo.setEnabled(false);
		
		JPanel leftLayout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rightLayout = new JPanel();
		rightLayout.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		leftLayout.add(back);
		leftLayout.add(undo);
		leftLayout.add(redo);
		rightLayout.add(valuesChanged);
		
		this.add(leftLayout);
		this.add(rightLayout);
		
		this.registerEventSources();
	}
	
	public void notifyValuesChanged() {
		this.valuesChanged.setVisible(true);
		ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
		scheduler.schedule(() -> hideValuesChanged(), ViewProperties.NOTIFICATION_TIMER, TimeUnit.SECONDS);
	}
	
	private void hideValuesChanged() {
		this.valuesChanged.setVisible(false);
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
