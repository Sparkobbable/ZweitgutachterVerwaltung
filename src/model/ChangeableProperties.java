package model;

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
}
