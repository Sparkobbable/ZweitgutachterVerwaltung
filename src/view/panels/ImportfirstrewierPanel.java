package view.panels;

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


@SuppressWarnings("serial") // should not be serialized
public class ImportfirstrewierPanel extends DefaultPanel {

	private JFileChooser chooseImport;
	private AbstractViewPanel buttons;


	public ImportfirstrewierPanel() {
		super("FirstReviewer Import");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new GridLayout(2, 2));
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}
	
	private void createUIElements() {
		this.chooseImport = new JFileChooser();
		this.buttons = new ImportfirstrewierButtonPanel();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Datei", "csv");
		chooseImport.setFileFilter(filter);
		chooseImport.setApproveButtonText("Select");
	}

	private void addUIElements() {
		this.add(chooseImport);
		this.add(buttons);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ChooserEventSource(EventId.CHOOSE_FILEPATH, chooseImport, () -> getFilePath()),
				buttons);

	}

	private String getFilePath() {
		return chooseImport.getSelectedFile().getAbsolutePath();
	}
}
