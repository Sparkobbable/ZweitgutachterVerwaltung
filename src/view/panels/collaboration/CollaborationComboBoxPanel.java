package view.panels.collaboration;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.events.EventSource;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ComboBoxEventSource;
import view.panels.prototypes.DefaultPanel;

@SuppressWarnings("serial") // should not be serialized
public class CollaborationComboBoxPanel extends DefaultPanel {

	private JLabel headline;
	private JComboBox<String> choosePresentationMode;
	private EventId eventId;
	
	public CollaborationComboBoxPanel(String headline, String[] options, EventId eventId) {
		super("");
		this.eventId = eventId;
		
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new GridLayout(2,1));
		
		this.createUIElements(headline, options);
		this.addUIElements();
		this.registerEventSources();
	}
	
	private void createUIElements(String headline, String[] options) {
		this.headline = new JLabel("Darstellung");
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
	
	private String getPresentationMode() {
			return this.choosePresentationMode.getSelectedItem().toString();
	}
}
