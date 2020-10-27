package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JButton;

import model.Action;
import model.enums.EventId;

/**
 * Handles the role of JButtons as EventSource
 */
public class ButtonEventSource extends SingleEventSource {
	private JButton button;

	public ButtonEventSource(EventId eventId, JButton button, Supplier<?>... params) {
		super(eventId, params);
		this.button = button;
	}

	@Override
	public void addEventHandler(EventId eventId, Action action) {
		if (!this.canOmit(eventId)) {
			throw new IllegalArgumentException(String.format(
					"JButton %s will never omit event %s. EventHandler could not be added", button.getText(), eventId));
		}
		button.addActionListener(e -> action.perform(params));
	}

}
