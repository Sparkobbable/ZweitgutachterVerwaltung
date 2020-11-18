package controller.propertychangelistener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface ChangeableProperties {
	/**
	 * Adds a propertyChangeListener to this Object that is called when any property
	 * is modified.
	 * 
	 * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
	 * @param propertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);

	/**
	 * Removes a propertyChangeListener from this Object 
	 * 	 * 
	 * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 * @param propertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);
}
