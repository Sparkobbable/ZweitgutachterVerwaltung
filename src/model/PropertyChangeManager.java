package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PropertyChangeManager implements PropertyChangeListener {

	public Map<String, List<Consumer<PropertyChangeEvent>>> delegations;

	public PropertyChangeManager() {
		this.delegations = new HashMap<>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.delegations.computeIfAbsent(evt.getPropertyName(), k -> new ArrayList<>()).forEach(c -> c.accept(evt));
	}

	public void onPropertyChange(String propertyName, Consumer<PropertyChangeEvent> delegation) {
		this.delegations.computeIfAbsent(propertyName, k -> new ArrayList<>()).add(delegation);
	}

}
