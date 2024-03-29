package view.widgets;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.events.EventSource;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ComboBoxEventSource;
import view.panels.prototypes.DefaultPanel;

/**
 * Widget ComboBox which can be used in every Panel
 */
@SuppressWarnings("serial") // should not be serialized
public class ComboBoxPanel extends DefaultPanel {

	private JLabel headline;
	private JComboBox<String> choosePresentationMode;
	private EventId eventId;
	
	public ComboBoxPanel(String headline, String[] options, EventId eventId) {
		super("");
		this.eventId = eventId;
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new GridLayout(2,1));
		
		this.createUIElements(headline, options);
		this.addUIElements();
		this.registerEventSources();
	}
	
	private void createUIElements(String headline, String[] options) {
		this.headline = new JLabel(headline);
		this.headline.setForeground(ViewProperties.FONT_COLOR);
		this.choosePresentationMode = new JComboBox<>(options);
	}
	
	private void addUIElements() {
		this.add(headline);
		this.add(choosePresentationMode);
	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ComboBoxEventSource(this.eventId, choosePresentationMode, () -> getPresentationMode()));
	}
	
	private ComboBoxMode getPresentationMode() {
			return ComboBoxMode.of(this.choosePresentationMode.getSelectedItem().toString()).orElseThrow();
	}
}
