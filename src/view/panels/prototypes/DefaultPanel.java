package view.panels.prototypes;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import controller.events.Action;
import controller.events.CompositeEventSource;
import controller.events.EventSource;
import controller.propertychangelistener.ChangeableProperties;
import controller.propertychangelistener.PropertyChangeManager;
import model.enums.EventId;
import view.MainWindow;
import view.widgets.ButtonFactory;

/**
 * Abstract class for all different masks that can be set as content for the
 * {@link MainWindow}
 *
 */
@SuppressWarnings("serial")
public abstract class DefaultPanel extends AbstractViewPanel {

	protected static final Border UNTITLED_BORDER = BorderFactory.createEtchedBorder();

	/**
	 * This map stores all event sources in this panel.
	 */
	private CompositeEventSource eventSourceHandler;
	private PropertyChangeManager propertyChangeManager;
	protected ButtonFactory buttonFactory;
	
	private String title;

	/**
	 * Creates a new Abstract View
	 * 
	 * @param title Title of this view that will be shown on the UI
	 */
	public DefaultPanel(String title) {
		super();
		this.eventSourceHandler = new CompositeEventSource();
		this.propertyChangeManager = new PropertyChangeManager();
		this.buttonFactory = ButtonFactory.getInstance();
		this.title = title;

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
	 * {@link DefaultPanel#AbstractView(String)}
	 * 
	 * @return A list of all EventSources that shall be registered
	 */
	protected abstract List<EventSource> getEventSources();

	/**
	 * Creates a pop-up with a notification of the given type. If the given type is
	 * a QUESTION_MESSAGE the pop-up will have Options to choose.
	 * 
	 * @param message     Message shown in the pop-up
	 * @param messageType Must be part of the {@link JOptionPane} values
	 * @return Chosen option or an open confirmation if the messageType is not
	 *         QUESTION_MESSAGE
	 */
	@Override
	public int alert(String message, int messageType) {
		if (messageType == JOptionPane.QUESTION_MESSAGE) {
			return JOptionPane.showConfirmDialog(this, message, "thesisSpace", JOptionPane.YES_NO_OPTION);
		} else {
			JOptionPane.showMessageDialog(this, message, "thesisSpace", messageType);
			return JOptionPane.CLOSED_OPTION;
		}
	}

	@Override
	public String toString() {
		return String.format("%s(title=%s)", this.getClass().getName(), title);
	}

	/**
	 * Adds the current view as an observer to the specified observable.
	 * 
	 * @param observables Needs the values to observer
	 */
	protected void observe(ChangeableProperties observable) {
		observable.addPropertyChangeListener(this);
	}

	protected void stopObserving(ChangeableProperties observable) {
		observable.removePropertyChangeListener(this);
	}

	/**
	 * Adds the current view as an observer to the specified observable values.
	 * 
	 * @param observables Needs the values to observer
	 */
	protected void observe(Collection<? extends ChangeableProperties> observables) {
		observables.forEach(this::observe);
	}

	/**
	 * Removes the current view as an observer from the specified observable values.
	 * 
	 * @param observables Needs the values to observer
	 */
	protected void stopObserving(Collection<? extends ChangeableProperties> observables) {
		observables.forEach(this::stopObserving);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.propertyChangeManager.propertyChange(evt);
	}

	protected void onPropertyChange(Supplier<Object> source, String propertyName,
			Consumer<PropertyChangeEvent> delegation) {
		this.propertyChangeManager.onPropertyChange(source, propertyName, delegation);
	}

	protected void onPropertyChange(String propertyName, Consumer<PropertyChangeEvent> delegation) {
		this.propertyChangeManager.onPropertyChange(propertyName, delegation);
	}

	/**
	 * Actions that prepare the window for getting shown. This method will be called
	 * whenever this view is made visible-
	 */
	@Override
	public void prepare() {
		// this method may be overwritten by the implementing classes
	}

}
