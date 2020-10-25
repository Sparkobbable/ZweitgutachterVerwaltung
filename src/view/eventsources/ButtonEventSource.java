package view.eventsources;

import javax.swing.JButton;

import model.enums.EventId;

/**
 * Handles the role of JButtons as EventSource
 */
public class ButtonEventSource extends SingleEventSource {
	private JButton button;

	public ButtonEventSource(EventId eventId, JButton button) {
		super(eventId);
		this.button = button;
	}

	@Override
	public void addEventHandler(EventId eventId, Runnable action) {
		if (!this.canOmit(eventId)) {
			throw new IllegalArgumentException(String.format(
					"JButton %s will never omit event %s. EventHandler could not be added", button.getText(), eventId));
		}
		button.addActionListener(e -> action.run());
	}

}
