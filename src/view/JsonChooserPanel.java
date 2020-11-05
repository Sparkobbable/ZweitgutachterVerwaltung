package view;

import java.awt.Color;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class JsonChooserPanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private JButton loadJson;
	private JButton saveJson;
	
	public JsonChooserPanel(ViewId viewId) {
		super(viewId, "Systemzustand Manager");
		this.setBackground(Color.gray);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	
	protected void createUIElements() {
		this.loadJson = new JButton("Systemzustand laden");
		this.saveJson = new JButton("Systemzustand speichern");
	}
	
	public void addUIElements() {
		this.add(loadJson);
		this.add(saveJson);
	}


	@Override
	protected List<EventSource> getEventSources() {
		return List.of(
				new ButtonEventSource(EventId.LOAD_JSON, loadJson),
				new ButtonEventSource(EventId.SAVE_JSON, saveJson));
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
