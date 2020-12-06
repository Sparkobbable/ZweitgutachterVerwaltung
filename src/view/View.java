package view;

import controller.events.Action;
import controller.events.EventSource;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.panels.prototypes.AbstractViewPanel;

/**
 * Interface of a View visible to the controller.
 * <p>
 * The view should update automatically when the presented data is changed, and
 * cannot be updated from the View interface.
 */
public interface View extends EventSource {

	public void addEventHandler(ApplicationState state, EventId eventId, Action action);

	/**
	 * @param message     Message shown in the pop-upmessageType Must be part of the
	 *                    JOptionPane values
	 * @param messageType Must be part of the JOptionPane values
	 */
	public int alert(String message, int messageType);

	/**
	 * Update the View by passing the information whether doing an undo operation is
	 * possible
	 * 
	 * @param b True if and only if the undo operation is possible
	 */
	public void setUndoable(boolean b);

	/**
	 * Update the View by passing the information whether doing an redo operation is
	 * possible
	 * 
	 * @param b true if and only if the redo operation is possible
	 */
	public void setRedoable(boolean b);

	/**
	 * Makes this view visible.
	 */
	public void setVisible();
}
