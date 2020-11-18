package view.collaboration;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import model.EventSource;
import model.enums.EventId;
import view.eventsources.ComboBoxEventSource;
import view.panelstructure.DefaultPanel;

public class CollaborationComboBoxPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel headline;
	private JComboBox<String> choosePresentationMode;
	private EventId eventId;
	
	public CollaborationComboBoxPanel(String headline, String[] options, EventId eventId) {
		super("");
		this.eventId = eventId;
		
		this.setBackground(Color.RED);
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
