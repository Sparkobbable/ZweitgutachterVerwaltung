package view.widgets;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.search.SearchStrategy;

/**
 * Panel that displays a search field and that should be checked when displaying
 * searchable data.
 * 
 * @param <T> Class of Elements that can be searched
 */
public class SearchField<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private SearchStrategy<T> searchStrategy;
	private ButtonFactory buttonFactory;
	// UI-Elements
	private JTextField searchText;
	private JButton searchButton;

	/**
	 * Creates a search widget containing a text field and a search button.
	 * 
	 * @param searchStrategy Strategy used for filtering elements for a given search
	 *                       text
	 * @param onUpdate       method that will be called when the search text is
	 *                       altered
	 */
	public SearchField(SearchStrategy<T> searchStrategy, Consumer<String> onUpdate) {
		this.buttonFactory = ButtonFactory.getInstance();
		this.searchStrategy = searchStrategy;
		this.searchText = new JTextField();
		this.searchButton = this.buttonFactory.createImageButton("search3");
		ActionListener update = (e) -> onUpdate.accept(this.searchText.getText());
		this.searchButton.addActionListener(update);
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

	public boolean matchesSearch(T obj) {
		return searchStrategy.match(obj, searchText.getText());
	}

}
