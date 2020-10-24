package view;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.constants.ButtonId;

/**
 * Abstract class for all different masks that can be set as content for the
 * {@link MainWindow}
 *
 * @param <T> - Specific object type
 */
public class AbstractView<T extends AbstractView<T>> extends JPanel {

	protected static final Border UNTITLED_BORDER = BorderFactory.createEtchedBorder();

	// TODO discuss if this class should inherit from JPanel (it probably shouldn't)
	private static final long serialVersionUID = 1L;

	/**
	 * Indicates whether this Panel has already been initialized.
	 */
	private boolean initialized = false;

	/**
	 * This map stores all buttons in this panel. Since it does not handle button
	 * creation or adding a button to a JPanel, it can and should store all Buttons
	 * located anywhere in this panel
	 */
	protected Map<ButtonId, JButton> buttons;
	private String title;

	/**
	 * Unique identifier for this view
	 */
	private String id;

	public AbstractView(String id, String title) {
		this.id = id;
		this.title = title;
		createButtons();
	}

	/**
	 * Initializes the user interface by adding all its children.
	 * <p>
	 * For object creation, consider using the constructor instead.
	 */
	public void init() {
		Logger.getLogger(AbstractView.class.getName()).info(String.format("Initializing view %s", this.title));
		if (this.initialized) {
			throw new IllegalStateException("This panel has already been initialized. It cannot be initialized again.");
		}
		this.initialized = true;
		this.setBorder(titledBorder(title));
	}

	/**
	 * 
	 * Adds an action to a button that is performed on button press TODO replace
	 * Runnable with custom class to avoid Threading confusion?
	 * 
	 * @param buttonId Id of the Button to be observed
	 * @param action   Action that is performed on button press
	 * @return itself for method chaining
	 * @throws IllegalArgumentException if no button with the given buttonId is
	 *                                  found in this object
	 */
	@SuppressWarnings("unchecked")
	public T onButtonClick(ButtonId buttonId, Runnable action) {
		if (!buttons.containsKey(buttonId)) {
			throw new IllegalArgumentException(String.format(
					"Button with ButtonId %s does not exist in Panel \"%s\". Could not add ActionListener.", buttonId,
					title));
		}
		buttons.get(buttonId).addActionListener(e -> action.run());

		return (T) this;
	}
	
	/**
	 * Creates a Border around the Component which features a title.
	 * @param title Title of the Component
	 * @return Returns the Border with the title
	 */
	protected TitledBorder titledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT,
				TitledBorder.TOP);
	}

	/**
	 * Creates Buttons and adds them to this panel. This Method should be Overridden
	 * by each Subclass that should have buttons.
	 * <p>
	 * It is automatically called in the AbstractPanel constructor.
	 */
	protected void createButtons() {
		buttons = new HashMap<>();
	}

	/**
	 * Adds a buttons to this panel.
	 * <p>
	 * This method should not be called outside the {@link #init()} method.
	 */
	protected void addButton(ButtonId buttonId) {
		this.add(buttons.get(buttonId));
	}

	/**
	 * @return true, if and only if this object has already been initialized by callinh the
	 *         {@link #init()} method
	 */
	public boolean isInitialized() {
		return initialized;
	}
	
	@Override
	public String toString() {
		return String.format("View(%s)", title);
	}

	public String getId() {
		return id;
	}
}
