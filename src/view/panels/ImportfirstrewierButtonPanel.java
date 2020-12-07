package view.panels;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import controller.events.EventSource;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.DefaultPanel;

@SuppressWarnings("serial") // should not be serialized
public class ImportfirstrewierButtonPanel extends DefaultPanel {

	private JButton loadImport;
	private JButton saveCSV;

	public ImportfirstrewierButtonPanel() {
		super("");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.loadImport = new JButton("Importieren");
		this.saveCSV = new JButton("Exportieren");
	}

	private void addUIElements() {
		this.add(loadImport);
		this.add(saveCSV);

	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.LOAD, loadImport),
				new ButtonEventSource(EventId.SAVE, saveCSV));
	}
}
