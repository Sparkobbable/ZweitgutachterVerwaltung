package view.eventsources;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Supplier;

import javax.swing.JTextField;

import controller.events.Action;
import controller.events.SingleEventSource;
import model.enums.EventId;

/**
 * Handles the role of JTextFields as EventSource Fires Event when the value in
 * TextField is changed
 */
public class TextFieldEventSource extends SingleEventSource {
	private JTextField textField;

	public TextFieldEventSource(EventId eventId, JTextField textfield, Supplier<?> value) {
		super(eventId, value);
		this.textField = textfield;
	}

	@Override
	public void addEventHandler(Action action) {
		this.textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				action.perform(TextFieldEventSource.this.params);
			}
		});
	}

}
