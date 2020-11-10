package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Action;
import model.EventSource;
import model.data.CompositeEventSource;
import model.enums.EventId;
import model.enums.ViewId;

/**
 * Abstract class for all different masks that can be set as content for the
 * {@link MainWindow}
 *
 */
@SuppressWarnings("deprecation")
public abstract class AbstractView extends JPanel implements EventSource, Observer {

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
	protected CompositeEventSource eventSourceHandler;
	private String title;

	/**
	 * Unique identifier for this view
	 */
	private ViewId viewId;

	/**
	 * Creates a new Abstract View
	 * 
	 * @param viewId Id of this View. It must be ensured that this id is unique
	 * @param title  Title of this view that will be shown on the UI
	 */
	public AbstractView(ViewId viewId, String title) {
		this.viewId = viewId;
		this.title = title;
		this.eventSourceHandler = new CompositeEventSource();
		this.setBorder(titledBorder(title));
	}

	protected void registerEventSources() {
		this.eventSourceHandler.registerAll(this.getEventSources());
	}

	/**
	 * Creates a Border around the Component which features a title.
	 * 
	 * @param title Title of the Component
	 * @return Returns the Border with the title
	 */
	protected static TitledBorder titledBorder(String title) {
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
	 * Creates a pop-up with a notification of the given type.
	 * If the given type is a QUESTION_MESSAGE the pop-up will have Options to choose.
	 * 
	 * @param message Message shown in the pop-up
	 * @param messageType Must be part of the {@link JOptionPane} values
	 * @return Chosen option or an open confirmation if the messageType is not QUESTION_MESSAGE 
	 */
	public int alert(String message, int messageType) {
		if(messageType == JOptionPane.QUESTION_MESSAGE) {
			return JOptionPane.showConfirmDialog(this, message, "thesisSpace", JOptionPane.YES_NO_OPTION);
		} else {
			JOptionPane.showMessageDialog(this, message, "thesisSpace", messageType);
			return JOptionPane.CLOSED_OPTION;
		}
	}

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

	@Override
	public abstract void update(Observable o, Object arg);

	/**
	 * Adds the current view as an observer to the specified observable values.
	 * 
	 * @param observables Needs the values to observer
	 */
	protected void addObservables(Observable... observables) {
		List.of(observables).forEach(o -> o.addObserver(this));
	}
	/*
	 * -----------------------------------------------------------------------------
	 * -- | Delegate methods to the responsible Objects
	 * -----------------------------------------------------------------------------
	 * --
	 */

	@Override
	public void addEventHandler(EventId eventId, Action action) {
		this.eventSourceHandler.addEventHandler(eventId, action);
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.eventSourceHandler.canOmit(eventId);
	}

}
