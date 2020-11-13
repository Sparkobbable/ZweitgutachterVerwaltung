package view;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultViewPanel;

/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;
	private JButton showReviewers;
	private JButton showTheses;
	private JButton importFirstReviewers;
	private JButton analyse;
	private JButton choosejsonfilepath;

	public HomePanel() { // Maybe add a contructor with standard id?
		super("Home");
		this.setBackground(Color.GREEN); // TODO remove
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	
	private void createUIElements() {
		this.showReviewers = new JButton("Gutachter anzeigen");
		this.showTheses = new JButton("Bachelorarbeiten anzeigen");
		this.importFirstReviewers = new JButton("Importiere Erstgutachter");
		this.analyse = new JButton("Analyse");
		this.choosejsonfilepath = new JButton("Systemzustand verwalten");
	}
	
	private void addUIElements() {
		this.add(showReviewers);
		this.add(showTheses);
		this.add(importFirstReviewers);
		this.add(analyse);
		this.add(choosejsonfilepath);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(//
				new ButtonEventSource(EventId.SHOW_REVIEWERS, showReviewers),
				new ButtonEventSource(EventId.SHOW_THESES, showTheses),
				new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, importFirstReviewers),
				new ButtonEventSource(EventId.ANALYSE, analyse),
				new ButtonEventSource(EventId.CHOOSE_FILEPATH, choosejsonfilepath));
	}
}
