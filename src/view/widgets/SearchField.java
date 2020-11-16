package view.widgets;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.eventsources.SearchFieldEventSource;


public class SearchField extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//UI-Elements
	private JTextField searchField;
	private JButton searchButton;
	
	//Data
	
	/**
	 * Creates a search widget containing a textfield and a searchbutton
	 * Needs wiring using {@link SearchFieldEventSource}
	 */
	public SearchField() {
		this.searchField = new JTextField();
		this.searchButton = new JButton(getSearchIcon());
		
		this.setLayout(new FlowLayout());
		
		this.addUiElements();
	}
	
	public void setSearchHandler(ActionListener actionListener, PropertyChangeListener propertyListener) {
		this.searchButton.addActionListener(actionListener);
		this.searchField.addPropertyChangeListener(propertyListener);
	}

	private void addUiElements() {
		this.add(searchField);
		this.add(searchButton);
	}

	private ImageIcon getSearchIcon() {
		URL resource = this.getClass().getResource("../resource/images/search.png");
		return new ImageIcon(resource);
	}

	public String getSearchText() {
		return this.searchField.getText();
	}
}
