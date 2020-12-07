package view.panels;

import java.util.List;

import javax.swing.JButton;

import controller.events.EventSource;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.DefaultPanel;

/**
 * Inner Panel inside a {@link StateChooserPanel} responsible for the displayed
 * buttons
 */
@SuppressWarnings("serial") // should not be serialized
public class StateChooserButtonsPanel extends DefaultPanel {

	private JButton loadState;
	private JButton saveState;

	public StateChooserButtonsPanel() {
		super("");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	
	private void createUIElements() {
		this.loadState = this.buttonFactory.createButton("Systemzustand laden");
		this.saveState = this.buttonFactory.createButton("Systemzustand speichern");
	}

	private void addUIElements() {
		this.add(loadState);
		this.add(saveState);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.LOAD, loadState),
				new ButtonEventSource(EventId.SAVE, saveState));
	}
}
