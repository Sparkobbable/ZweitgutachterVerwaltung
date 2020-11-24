package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.events.EventSource;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultPanel;

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
	
	

	public HomePanel() { // Maybe add a contructor with standard id?
		super("Home");
		this.setBackground(new Color(36, 50, 63)); // TODO remove
		this.setLayout(new GridBagLayout());

		this.createUIElements();
		this.formatUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	
	private void createUIElements() {
		this.headline = new JLabel();
		this.showReviewers = new JButton("");
		this.showTheses = new JButton("");
		this.importFirstReviewers = new JButton("");
		this.analyse = new JButton("");
		this.choosejsonfilepath = new JButton("");
	}
	
	private void formatUIElements() {
		Image icon = new ImageIcon(this.getClass().getResource("resource/images/homescreen.png")).getImage();
		this.headline.setIcon(new ImageIcon(icon));
		
		icon = new ImageIcon(this.getClass().getResource("resource/images/gutachter.png")).getImage();
		this.showReviewers.setIcon(new ImageIcon(icon));
		this.showReviewers.setBorderPainted(false);
		this.showReviewers.setFocusPainted(false);
		this.showReviewers.setContentAreaFilled(false);
		this.showReviewers.setBackground(View.button);
		this.showReviewers.setForeground(Color.WHITE);
		
		icon = new ImageIcon(this.getClass().getResource("resource/images/thesis.png")).getImage();
		this.showTheses.setIcon(new ImageIcon(icon));
		this.showTheses.setBorderPainted(false);
		this.showTheses.setFocusPainted(false);
		this.showTheses.setContentAreaFilled(false);
		this.showTheses.setBackground(View.button);
		this.showTheses.setForeground(Color.WHITE);
		
		icon = new ImageIcon(this.getClass().getResource("resource/images/import.png")).getImage();
		this.importFirstReviewers.setIcon(new ImageIcon(icon));
		this.importFirstReviewers.setBorderPainted(false);
		this.importFirstReviewers.setFocusPainted(false);
		this.importFirstReviewers.setContentAreaFilled(false);
		this.importFirstReviewers.setBackground(View.button);
		this.importFirstReviewers.setForeground(Color.WHITE);
		
		icon = new ImageIcon(this.getClass().getResource("resource/images/analyse.png")).getImage();
		this.analyse.setIcon(new ImageIcon(icon));
		this.analyse.setBorderPainted(false);
		this.analyse.setFocusPainted(false);
		this.analyse.setContentAreaFilled(false);
		this.analyse.setBackground(View.button);
		this.analyse.setForeground(Color.WHITE);
		
		icon = new ImageIcon(this.getClass().getResource("resource/images/systemstate.png")).getImage();
		this.choosejsonfilepath.setIcon(new ImageIcon(icon));
		this.choosejsonfilepath.setBorderPainted(false);
		this.choosejsonfilepath.setFocusPainted(false);
		this.choosejsonfilepath.setContentAreaFilled(false);
		this.choosejsonfilepath.setBackground(View.button);
		this.choosejsonfilepath.setForeground(Color.WHITE);
		
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
