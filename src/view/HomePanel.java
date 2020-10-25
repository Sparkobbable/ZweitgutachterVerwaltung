package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private JButton showReviewers;
	private JButton importFirstReviewers;
	private JButton analyse;

	public HomePanel(ViewId id) { // Maybe add a contructor with standard id?
		super(id, "Home");

	}

	public void init() {
		super.init();
		this.setBackground(Color.GREEN); // TODO remove
		this.add(showReviewers);
		this.add(importFirstReviewers);
		this.add(analyse);
	}

	@Override
	protected void createUIElements() {
		this.showReviewers = new JButton("Gutachter anzeigen");
		this.importFirstReviewers = new JButton("Importiere Erstgutachter");
		this.analyse = new JButton("Analyse");
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(//
				new ButtonEventSource(EventId.SHOW_REVIEWERS, showReviewers),
				new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, importFirstReviewers),
				new ButtonEventSource(EventId.ANALYSE, analyse));
	}
}
