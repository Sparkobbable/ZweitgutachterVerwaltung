package view.panels.prototypes;

import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.events.EventSource;
import view.MainWindow;
import view.ViewState;

/**
 * Abstract class for all different masks that can be set as content for the
 * {@link MainWindow}
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractViewPanel extends JPanel implements EventSource, PropertyChangeListener {

	private static final AtomicInteger VIEW_ID_GENERATOR = new AtomicInteger();
	/**
	 * Unique identifier for this view
	 */
	private Integer viewId;

	/**
	 * Creates a new Abstract View
	 * 
	 * @param title Title of this view that will be shown on the UI
	 */
	public AbstractViewPanel() {
		this.viewId = VIEW_ID_GENERATOR.incrementAndGet();
	}

	/**
	 * Creates a pop-up with a notification of the given type. If the given type is
	 * a QUESTION_MESSAGE the pop-up will have Options to choose.
	 * 
	 * @param message     Message shown in the pop-up
	 * @param messageType Must be part of the {@link JOptionPane} values
	 * @return Chosen option or an open confirmation if the messageType is not
	 *         QUESTION_MESSAGE
	 */
	public abstract int alert(String message, int messageType);

	/**
	 * @return The unique viewId of this View.
	 */
	public Integer getViewId() {
		return viewId;
	}

	/**
	 * Actions that prepare the window for getting shown. This method will be called
	 * whenever this view is made visible-
	 */
	public abstract void prepare();

	/**
	 * this method may be overwritten by stateful AbstractViewPanels and will be
	 * called to enter a given ViewState
	 */
	public void initializeState(ViewState viewState) {

	}

	/**
	 * This method can be used to give a wrapper object for this AbstractViewPanel
	 * that represents this View at a given state.
	 * 
	 * @param viewState
	 * @return
	 */
	public ViewPanelDecorator atState(ViewState viewState) {
		return new ViewPanelDecorator(this, viewState);
	}

}
