package view.panels;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import controller.events.EventSource;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.DefaultPanel;

/**
 * Inner Panel inside a {@link ImportFirstReviewerPanel} responsible for the
 * displayed buttons
 */
@SuppressWarnings("serial") // should not be serialized
public class ImportFirstReviewerButtonPanel extends DefaultPanel {

	private JButton loadImport;
	private JButton saveCSV;
	private JCheckBox overrideData;

	public ImportFirstReviewerButtonPanel() {
		super("");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.loadImport = new JButton("Importieren");
		this.saveCSV = new JButton("Exportieren");
		this.overrideData = new JCheckBox("Daten überschreiben");
		this.overrideData.setSelected(true);
	}

	private void addUIElements() {
		this.add(overrideData);
		this.add(loadImport);
		this.add(saveCSV);

	}

	private boolean isOverrideChecked() {
		return this.overrideData.isSelected();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.LOAD, loadImport, () -> isOverrideChecked()),
				new ButtonEventSource(EventId.SAVE, saveCSV));
	}
}
