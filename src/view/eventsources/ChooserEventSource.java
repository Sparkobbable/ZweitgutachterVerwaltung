package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JFileChooser;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;

/**
 * {@link SingleEventSource} represented by a JFileChooser
 * <p>
 * Triggers events when a File is chosen with a JFileChooser
 *
 * @see JFileChooser#addActionListener(java.awt.event.ActionListener)
 */
public class ChooserEventSource extends SingleEventSource {

	private final JFileChooser filechooser;

	public ChooserEventSource(EventId eventId, JFileChooser filechooser, Supplier<?>... params) {
		super(eventId, params);
		this.filechooser = filechooser;
	}

	@Override
	public void addEventHandler(Action action) {
		filechooser.addActionListener(e -> action.perform(params));
	}
}
