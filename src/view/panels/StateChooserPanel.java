package view.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ChooserEventSource;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;

/**
 * Panel responsible for saving and loading the current system state.
 */
@SuppressWarnings("serial") // should not be serialized
public class StateChooserPanel extends DefaultPanel {

	private final JFileChooser chooseJson;
	private final AbstractViewPanel buttons;

	public StateChooserPanel() {
		super("Systemzustand Manager");

		this.chooseJson = new JFileChooser();
		this.buttons = new StateChooserButtonsPanel();
		this.registerEventSources();

		this.init();
	}

	private void init() {
		this.setBackground(Color.gray);
		this.setLayout(new GridLayout(2, 2));
		this.add(this.chooseJson);
		this.add(this.buttons);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Datei", "json");
		this.chooseJson.setFileFilter(filter);
		this.chooseJson.setApproveButtonText("Select");
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ChooserEventSource(EventId.CHOOSE_FILEPATH, chooseJson, () -> getFilePath()), buttons);
	}

	private String getFilePath() {
		return this.chooseJson.getSelectedFile().getAbsolutePath();
	}
}