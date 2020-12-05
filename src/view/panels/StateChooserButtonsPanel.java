package view.panels;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.DefaultPanel;

/**
 * Inner Panel inside a {@link StateChooserPanel} responsible for the displayed
 * buttons
 */
@SuppressWarnings("serial") // should not be serialized
public class StateChooserButtonsPanel extends DefaultPanel {

	private final JButton loadState;
	private final JButton saveState;

	public StateChooserButtonsPanel() {
		super("");

		this.loadState = this.buttonFactory.createButton("Systemzustand laden");
		this.saveState = this.buttonFactory.createButton("Systemzustand speichern");
		this.registerEventSources();

		this.init();
	}

	public void init() {
		this.setBackground(Color.gray);
		this.add(loadState);
		this.add(saveState);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.LOAD_STATE, loadState),
				new ButtonEventSource(EventId.SAVE_STATE, saveState));
	}
}
