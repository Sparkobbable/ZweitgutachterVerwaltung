package view;

import java.awt.Color;
import java.util.List;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class StateChooserButtonsPanel extends AbstractView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton loadState;
	private JButton saveState;

	public StateChooserButtonsPanel(ViewId viewId) {
		super(viewId, "Options");
		this.registerEventSources();
		
		this.loadState = new JButton("Systemzustand laden");
		this.saveState = new JButton("Systemzustand speichern");
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
