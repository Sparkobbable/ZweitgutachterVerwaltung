package view.panelstructure;

import java.beans.PropertyChangeEvent;

import model.Action;
import model.enums.EventId;
import view.ViewState;

@SuppressWarnings("serial")
public class ViewPanelDecorator extends AbstractViewPanel {

	private AbstractViewPanel innerPanel;
	private ViewState viewState;
	
	public ViewPanelDecorator(AbstractViewPanel innerPanel, ViewState viewState) {
		this.innerPanel = innerPanel;
		this.viewState = viewState;
	}
	
	@Override
	public void prepare() {
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


}
