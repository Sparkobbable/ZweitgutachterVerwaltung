package view;

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


@SuppressWarnings("serial") // should not be serialized
public class ImportfirstrewierPanel extends DefaultPanel {

	private JFileChooser chooseImport;
	private AbstractViewPanel buttons;


	public ImportfirstrewierPanel() {
		super("FirstReviewer Import");
		this.setBackground(Color.orange);
this.chooseImport = new JFileChooser();
		this.buttons = new ImportfirstrewierButtonPanel();

	

		this.registerEventSources();

		this.init();

	}

	public void init() {
		this.setBackground(Color.orange);
		this.setLayout(new GridLayout(2, 2));
		this.add(chooseImport);
		this.add(buttons);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Datei", "csv");
		chooseImport.setFileFilter(filter);
		chooseImport.setApproveButtonText("Select");
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ChooserEventSource(EventId.IMPORT_FIRST_REVIEWERS, chooseImport, () -> getFilePath()),
				buttons);

	}

	private String getFilePath() {
		return chooseImport.getSelectedFile().getAbsolutePath();
	}
}
