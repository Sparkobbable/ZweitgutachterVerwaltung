package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultViewPanel;

public class StateChooserButtonsPanel extends DefaultViewPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton loadState;
	private JButton saveState;

	public StateChooserButtonsPanel() {
		super("Options");
		
		this.loadState = new JButton("Systemzustand laden");
		this.saveState = new JButton("Systemzustand speichern");
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
		return List.of(
				new ButtonEventSource(EventId.LOAD_STATE, loadState),
				new ButtonEventSource(EventId.SAVE_STATE, saveState));
	}
}
