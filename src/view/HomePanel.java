package view;

import java.awt.Color;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

/**
 * Basic home panel, allows navigating to different application sections
 */
@SuppressWarnings("deprecation")
public class HomePanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private JButton showReviewers;
	private JButton importFirstReviewers;
	private JButton analyse;
	private JButton choosejsonfilepath;

	public HomePanel(ViewId id) { // Maybe add a contructor with standard id?
		super(id, "Home");
		this.createUIElements();
		this.registerEventSources();
	}

	public void init() {
		super.init();
		this.setBackground(Color.GREEN); // TODO remove
		this.add(showReviewers);
		this.add(importFirstReviewers);
		this.add(analyse);
		this.add(choosejsonfilepath);
	}

	@Override
	protected void createUIElements() {
		this.showReviewers = new JButton("Gutachter anzeigen");
		this.importFirstReviewers = new JButton("Importiere Erstgutachter");
		this.analyse = new JButton("Analyse");
		this.choosejsonfilepath = new JButton("Systemzustand verwalten");
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(//
				new ButtonEventSource(EventId.SHOW_REVIEWERS, showReviewers),
				new ButtonEventSource(EventId.IMPORT_FIRST_REVIEWERS, importFirstReviewers),
				new ButtonEventSource(EventId.ANALYSE, analyse),
				new ButtonEventSource(EventId.CHOOSE_JSON_FILEPATH, choosejsonfilepath));
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
