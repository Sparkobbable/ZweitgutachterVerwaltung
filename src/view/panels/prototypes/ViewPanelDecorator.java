package view.panels.prototypes;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;

import controller.events.Action;
import model.enums.EventId;
import view.ViewState;

/**
 * A wrapper class that represents an {@link AbstractViewPanel} at a given
 * {@link ViewState}
 * <p>
 * This class can be used if an AbstractViewPanel has different ViewStates. Each
 * ViewState should then be linked to an ApplicationState
 * 
 * @see AbstractViewPanel#atState(ViewState)
 *
 */
@SuppressWarnings("serial")
public class ViewPanelDecorator extends AbstractViewPanel {

	private AbstractViewPanel innerPanel;
	private ViewState viewState;

	public ViewPanelDecorator(AbstractViewPanel innerPanel, ViewState viewState) {
		this.innerPanel = innerPanel;
		this.viewState = viewState;
		this.setLayout(new BorderLayout());
	}

	@Override
	public void prepare() {
		this.add(innerPanel);
		this.innerPanel.initializeState(this.viewState);
		this.innerPanel.prepare();
	}

	// ----------------------------------------------
	// Delegates all other methods to the inner panel
	// ----------------------------------------------
	@Override
	public void addEventHandler(EventId eventId, Action action) {
		this.innerPanel.addEventHandler(eventId, action);
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.innerPanel.canOmit(eventId);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.innerPanel.propertyChange(evt);
	}

	@Override
	public int alert(String message, int messageType) {
		return this.innerPanel.alert(message, messageType);
	}

	@Override
	public String toString() {
		return String.format("ViewPanelDecorator(viewState=%s, inner=%s)", this.viewState, this.innerPanel);
	}

}
