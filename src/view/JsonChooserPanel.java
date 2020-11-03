package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class JsonChooserPanel extends AbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton loadJson;
	private JButton saveJson;
	
	public JsonChooserPanel(ViewId viewId) {
		super(viewId, "Systemzustand Manager");
		this.createUIElements();
		this.registerEventSources();
	}
	
	public void init() {
		super.init();
		this.setBackground(Color.gray);
		this.add(loadJson);
		this.add(saveJson);
	}

	@Override
	protected void createUIElements() {
		this.loadJson = new JButton("Systemzustand laden");
		this.saveJson = new JButton("Systemzustand speichern");
		
	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return List.of(
				new ButtonEventSource(EventId.LOAD_JSON, loadJson),
				new ButtonEventSource(EventId.SAVE_JSON, saveJson));
	}
}
