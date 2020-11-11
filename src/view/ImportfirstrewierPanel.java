package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;
import view.eventsources.ChooserEventSource;

public class ImportfirstrewierPanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private JFileChooser chooseImport;
	private AbstractView buttons;

	public ImportfirstrewierPanel(ViewId viewId) {
		super(viewId, "FirstReviewer Import");
		this.chooseImport = new JFileChooser();
		this.buttons = new ImportfirstrewierButtonPanel(ViewId.IMPORT_BUTTON);
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
