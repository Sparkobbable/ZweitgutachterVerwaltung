package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultPanel;
import view.resource.ImageLoader;

/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;

	private JLabel headline;
	private JButton showReviewers;
	private JButton showTheses;
	private JButton importFirstReviewers;
	private JButton analyse;
	private JButton choosejsonfilepath;

	public HomePanel() {
		super("Home");
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.headline = new JLabel(ImageLoader.getInstance().loadImageIcon("homescreen"));
		this.showReviewers = this.buttonFactory.createSeamlessImageButton("gutachter");
		this.showTheses = this.buttonFactory.createSeamlessImageButton("thesis");
		this.importFirstReviewers = this.buttonFactory.createSeamlessImageButton("import");
		this.analyse = this.buttonFactory.createSeamlessImageButton("analyse");
		this.choosejsonfilepath = this.buttonFactory.createSeamlessImageButton("systemstate");

	}

	private void addUIElements() {
		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		this.add(headline, g);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0.25;
		this.add(showReviewers, c);

		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 1;
		d.gridy = 1;
		d.gridwidth = 1;
		d.weightx = 0.5;
		d.weighty = 0.25;
		this.add(showTheses, d);

		GridBagConstraints e = new GridBagConstraints();
		e.fill = GridBagConstraints.HORIZONTAL;
		e.gridx = 2;
		e.gridy = 1;
		e.gridwidth = 1;
		e.weightx = 0.5;
		e.weighty = 0.25;
		this.add(importFirstReviewers, e);

		GridBagConstraints f = new GridBagConstraints();
		f.fill = GridBagConstraints.HORIZONTAL;
		f.gridx = 3;
		f.gridy = 1;
		f.gridwidth = 1;
		f.weightx = 0.5;
		f.weighty = 0.25;
		this.add(analyse, f);

		GridBagConstraints h = new GridBagConstraints();
		h.fill = GridBagConstraints.HORIZONTAL;
		h.gridx = 1;
		h.gridy = 2;
		h.gridwidth = 2;
		h.weightx = 0.5;
		this.add(choosejsonfilepath, h);
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
