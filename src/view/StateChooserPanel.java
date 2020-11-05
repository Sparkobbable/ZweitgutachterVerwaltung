package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ChooserEventSource;

public class StateChooserPanel extends AbstractView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser chooseJson;
	private AbstractView buttons;
	
	public StateChooserPanel(ViewId viewId) {
		super(viewId, "Systemzustand Manager");
		this.registerEventSources();
		
		this.chooseJson = new JFileChooser();
		this.buttons = new StateChooserButtonsPanel(ViewId.STATE_BUTTONS);
	}
	
	public void init() {
		this.setBackground(Color.gray);
		this.setLayout(new GridLayout(2, 2));
		this.add(chooseJson);
		this.add(buttons);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Datei", "json");
		chooseJson.setFileFilter(filter);
	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return List.of(
				new ChooserEventSource(EventId.CHOOSE_FILEPATH, chooseJson, () -> getFilePath()),
				buttons);
	}
	
	private String getFilePath() {
		return chooseJson.getSelectedFile().getAbsolutePath();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}