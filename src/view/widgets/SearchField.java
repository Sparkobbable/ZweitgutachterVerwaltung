package view.widgets;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.search.SearchStrategy;


public class SearchField<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SearchStrategy<T> searchStrategy;
	
	//UI-Elements
	private JTextField searchText;
	private JButton searchButton;
	
	
	
	/**
	 * Creates a search widget containing a textfield and a searchbutton
	 * Needs wiring using {@link SearchFieldEventSource}
	 */
	public SearchField(SearchStrategy<T> searchStrategy, Consumer<String> onUpdate) {
		this.searchStrategy = searchStrategy;
		this.searchText = new JTextField();
		this.searchButton = new JButton(getSearchIcon());
		this.searchButton.addActionListener((e) -> onUpdate.accept(this.searchText.getText()));
		ActionListener update = (e) -> onUpdate.accept(this.searchText.getText());
		this.searchText.addActionListener(update);
		this.searchText.addActionListener(update);
		this.setLayout(new GridLayout(1, 2));
		
		this.addUiElements();
	}
	
	public void setSearchHandler(ActionListener actionListener, PropertyChangeListener propertyListener) {
		this.searchButton.addActionListener(actionListener);
	}

	private void addUiElements() {
		this.add(searchText);
		this.add(searchButton);
	}

	private ImageIcon getSearchIcon() {
		URL resource = this.getClass().getResource("../resource/images/search3.png");
		return new ImageIcon(resource);
	}
	
	public boolean matchesSearch(T obj) {
		return searchStrategy.match(obj, searchText.getText());
	}

}
