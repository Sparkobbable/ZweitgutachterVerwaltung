package view;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Action;
import model.EventSource;
import model.data.EventSourceHandler;
import model.enums.EventId;
import model.enums.ViewId;

/**
 * Abstract class for all different masks that can be set as content for the
 * {@link MainWindow}
 *
 */
public abstract class AbstractView extends JPanel implements EventSource {

	protected static final Border UNTITLED_BORDER = BorderFactory.createEtchedBorder();

	// TODO discuss if this class should inherit from JPanel (it probably shouldn't)
	private static final long serialVersionUID = 1L;

	/**
	 * Indicates whether this Panel has already been initialized.
	 */
	private boolean initialized = false;

	/**
	 * This map stores all event sources in this panel.
	 */
	protected EventSourceHandler eventHandler;
	private String title;

	/**
	 * Unique identifier for this view
	 */
	private ViewId viewId;

	/**
	 * Creates a new Abstract View
	 * @param viewId Id of this View. It must be ensured that this id is unique
	 * @param title Title of this view that will be shown on the UI
	 */
	public AbstractView(ViewId viewId, String title) {
		this.viewId = viewId;
		this.title = title;
		this.eventHandler = new EventSourceHandler();
//		this.createUIElements();
//		this.registerEventSources();
	}

	protected void registerEventSources() {
		this.eventHandler.registerAll(this.getEventSources());
	}

	/**
	 * Initializes the user interface by adding all its children.
	 * <p>
	 * For object creation, consider using {@link #createUIElements()} instead.
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
	 * Creates a Border around the Component which features a title.
	 * 
	 * @param title Title of the Component
	 * @return Returns the Border with the title
	 */
	protected TitledBorder titledBorder(String title) {
		return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT,
				TitledBorder.TOP);
	}

	/**
	 * All EventSources returned by this method will be registered in
	 * {@link AbstractView#AbstractView(ViewId, String)}
	 * 
	 * @return A list of all EventSources that shall be registered
	 */
	protected abstract List<EventSource> getEventSources();

	/**
	 * Detects if this view has already been initialized to prevent repeated
	 * initialization
	 * 
	 * @return true, if and only if this object has already been initialized by
	 *         calling the {@link #init()} method
	 */
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public String toString() {
		return String.format("View(viewId=%s, title=%s)", viewId, title);
	}

	/**
	 * @return The unique viewId of this View.
	 */
	public ViewId getViewId() {
		return viewId;
	}

	/**
	 * 
	 * Adds an action that is performed when the specified event is ommitted
	 * <p>
	 * TODO replace Runnable with custom class to avoid Threading confusion?
	 * 
	 * @param eventId Id of the Event to be observed
	 * @param action  Action that is performed on button press
	 * @throws IllegalArgumentException if no button with the given buttonId is
	 *                                  found in this object
	 */
	@Override
	public void addEventHandler(EventId eventId, Action action) {
		if (!eventHandler.canOmit(eventId)) {
			throw new IllegalArgumentException(String.format(
					"Button with ButtonId %s does not exist in Panel \"%s\". Could not add ActionListener.", eventId,
					viewId));
		}
		eventHandler.addEventHandler(eventId, action);
	}

	@Override
	public boolean canOmit(EventId event) {
		return eventHandler.canOmit(event);
	}

	/**
	 * This method should be used to create UI elements. It is automatically called
	 * in the constructor and will be called before {@link #getEventSources()}
	 */
	protected abstract void createUIElements();

}
