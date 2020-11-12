package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultViewPanel;

public class ImportfirstrewierPanel extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;
	private JButton loadReview;

	public ImportfirstrewierPanel() {
		super("FirstReviewer Import");
		this.setBackground(Color.orange);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	protected void createUIElements() {
		this.loadReview = new JButton("Import Review");
	}

	public void addUIElements() {
		this.add(loadReview);

	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, loadReview));

	}
}
