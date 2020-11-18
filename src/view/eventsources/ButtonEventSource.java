package view.eventsources;

import java.util.function.Supplier;

import javax.swing.JButton;

import controller.events.Action;
import controller.events.SingleEventSource;
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

	/**
	 * Creates a new ButtonEventSource from the given parameters. This is equivalent
	 * to {@link #ButtonEventSource(EventId, JButton, Supplier...)} and only
	 * provided for comfortable constructor calling.
	 * 
	 * @param eventId
	 * @param button
	 * @param params
	 * @return
	 */
	public static ButtonEventSource of(EventId eventId, JButton button, Supplier<?>... params) {
		return new ButtonEventSource(eventId, button, params);
	}

}
