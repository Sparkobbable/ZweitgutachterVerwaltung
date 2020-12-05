package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;

/**
 * {@link SingleEventSource} represented by a {@link JComboBox}
 * <p>
 * Triggers events when an item in a JComboBox is selected.
 *
 * @see JFileChooser#addActionListener(java.awt.event.ActionListener)
 */
public class ComboBoxEventSource extends SingleEventSource {
	
	private JComboBox<String> comboBox;
	
	public ComboBoxEventSource(EventId eventId, JComboBox<String> comboBox, Supplier<?>... params) {
		super(eventId, params);
		this.comboBox = comboBox;
	}

	@Override
	public void addEventHandler(Action action) {
		comboBox.addActionListener(e -> action.perform(params));
	}
}
