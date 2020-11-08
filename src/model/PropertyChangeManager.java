package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import util.Log;

public class PropertyChangeManager implements PropertyChangeListener {

	public Map<String, Consumer<PropertyChangeEvent>> delegations;

	public PropertyChangeManager() {
		this.delegations = new HashMap<>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Consumer<PropertyChangeEvent> consumer = this.delegations.get(evt.getPropertyName());
		if (consumer == null) {
			Log.warning(this, "Ignoring PropertyChangeEvent for property %s.", evt.getPropertyName());
		} else {
			consumer.accept(evt);
		}

//TODO		Optional.ofNullable(this.delegations.get(evt.getPropertyName())).ifPresent(consumer -> consumer.accept(evt));
	}

	public void onPropertyChange(String propertyName, Consumer<PropertyChangeEvent> delegation) {
		this.delegations.put(propertyName, delegation);
	}

}
