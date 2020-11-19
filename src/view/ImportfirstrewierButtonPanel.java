package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultPanel;

public class ImportfirstrewierButtonPanel extends DefaultPanel{

private static final long serialVersionUID = 1L;
	
	private JButton loadImport;
	

	public ImportfirstrewierButtonPanel() {
		super( "Options");
		
		this.loadImport = new JButton("FirstReviewer Import");
			this.registerEventSources();
		
		this.init();
	}
	
	public void init() {
		this.setBackground(Color.orange);
		this.add(loadImport);
		
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(
				new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, loadImport));
	}
}
