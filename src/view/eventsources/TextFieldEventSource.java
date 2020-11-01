package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JTextField;


import model.Action;
import model.enums.EventId;

/**
 * Handles the role of JTextFields as EventSource
 * Fires Event when the value in TextField is changed
 */
public class TextFieldEventSource extends SingleEventSource {
	private JTextField textField;
	
	public TextFieldEventSource(EventId eventId, JTextField textfield, Supplier<String> value) {
		super(eventId, value);
		this.textField = textfield;
	}

	@Override
	public void addEventHandler(Action action) {
		textField.addPropertyChangeListener(e -> action.perform(params));
	}

}
