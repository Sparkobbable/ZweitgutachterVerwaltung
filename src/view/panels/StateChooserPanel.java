package view.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.events.EventSource;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ChooserEventSource;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;

/**
 * Panel responsible for saving and loading the current system state.
 */
@SuppressWarnings("serial") // should not be serialized
public class StateChooserPanel extends DefaultPanel {

	private JFileChooser chooseJson;
	private AbstractViewPanel buttons;

	public StateChooserPanel() {
		super("Systemzustand Manager");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new GridLayout(2, 2));
		
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.chooseJson = new JFileChooser();
		this.buttons = new StateChooserButtonsPanel();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Datei", "json");
		this.chooseJson.setFileFilter(filter);
		this.chooseJson.setApproveButtonText("Select");
	}
	
	private void addUIElements() {
		this.add(this.chooseJson);
		this.add(this.buttons);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ChooserEventSource(EventId.CHOOSE_FILEPATH, chooseJson, () -> getFilePath()), buttons);
	}

	private String getFilePath() {
		return this.chooseJson.getSelectedFile().getAbsolutePath();
	}
}