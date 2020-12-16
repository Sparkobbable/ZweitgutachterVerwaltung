package model.propertychangelistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Class that handles listening to property changes and calling the correct
 * methods accordingly. For each Property Id, a list of Method is stored that
 * are called when a property with the fitting propertyId is modified.
 *
 */
public class PropertyChangeManager implements PropertyChangeListener {

	/**
	 * Map which stores a List of PropertyChangeHandler responsible for a Property
	 * <p>
	 * Each propertyChangeHandler consists of a consumer method to be called on
	 * property change as well as the source object whose property change should
	 * call the method.
	 * <p>
	 * The source object is not stored in the map's key since it may not easily be
	 * accessible (in the future TODO)
	 */
	public Map<String, List<PropertyChangeHandler>> propertyChangeHandlers;

	/**
	 * If this is set as source for a {@link PropertyChangeHandler}, the given
	 * method will be called on property changes of any object
	 */
	private static final Object ANY = new Object();

	public PropertyChangeManager() {
		this.propertyChangeHandlers = new HashMap<>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.propertyChangeHandlers.computeIfAbsent(evt.getPropertyName(), k -> new ArrayList<>()).stream()
				.filter(propertyChangeHandler -> propertyChangeHandler.getSource() != null)
				.filter(propertyChangeHandler -> propertyChangeHandler.getSource() == ANY
						|| propertyChangeHandler.getSource().equals(evt.getSource()))
				.forEach(propertyChangeHandler -> propertyChangeHandler.getConsumer().accept(evt));
	}

	/**
	 * Adds a method that will be called when the source Object's property with the
	 * given name is changed.
	 * 
	 * @param source       Object that should be observed
	 * @param propertyName Name of the property to be observed
	 * @param consumer     Method to be called on property change
	 */
	public void onPropertyChange(Supplier<Object> source, String propertyName, Consumer<PropertyChangeEvent> consumer) {
		this.propertyChangeHandlers.computeIfAbsent(propertyName, k -> new ArrayList<>())
				.add(new PropertyChangeHandler(source, consumer));
	}

	public void onPropertyChange(String propertyName, Consumer<PropertyChangeEvent> consumer) {
		this.onPropertyChange(() -> ANY, propertyName, consumer);
	}

	/**
	 * Stores a consumer and a source object
	 * 
	 */
	private class PropertyChangeHandler {
		private final Supplier<Object> source;
		private final Consumer<PropertyChangeEvent> consumer;

		public PropertyChangeHandler(Supplier<Object> source, Consumer<PropertyChangeEvent> consumer) {
			this.source = source;
			this.consumer = consumer;
		}

		public Object getSource() {
			return source.get();
		}

		public Consumer<PropertyChangeEvent> getConsumer() {
			return consumer;
		}
	}

}
