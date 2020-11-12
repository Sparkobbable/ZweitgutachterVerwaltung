package view.collaboration;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import model.EventSource;
import model.enums.EventId;
import model.enums.PresentationMode;
import view.eventsources.ComboBoxEventSource;
import view.panelstructure.DefaultViewPanel;

public class CollaborationComboBoxPanel extends DefaultViewPanel {

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
		this.choosePresentationMode.setSelectedItem(options[0]);
	}
	
	private void addUIElements() {
		this.add(headline);
		this.add(choosePresentationMode);
	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ComboBoxEventSource(this.eventId, choosePresentationMode, () -> getPresentationMode()));
	}
	
	private PresentationMode getPresentationMode() {
		return PresentationMode.of((String) this.choosePresentationMode.getSelectedItem()).orElseThrow(() -> new IllegalArgumentException());
	}
}