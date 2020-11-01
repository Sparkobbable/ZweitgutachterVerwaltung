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
	public void addEventHandler(Action action) {
		button.addActionListener(e -> action.perform(params));
	}

}
