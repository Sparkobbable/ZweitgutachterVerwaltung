package view.panels.prototypes;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JTable;

import controller.events.EventSource;
import view.ViewProperties;
import view.widgets.SearchField;

/**
 * Abstract {@link DefaultPanel} serving actions for another {@link DefaultPanel} of the given type <T>. 
 * <p>
 * It also serves convenience methods and is supposed to be specified with the exact data and {@link Component}s
 * <p>
 * This {@link DefaultPanel} contains the actions for the given type <T>.
 *
 * @param <T>	The type of the objects that are presented in the {@link DefaultPanel} which features this actions
 */
@SuppressWarnings("serial") // should not be serialized
public abstract class AbstractActionPanel<T> extends DefaultPanel {

	protected Supplier<List<T>> selectedElementsSupplier;

	/**
	 * Initializes this panel, setting the supplier for the data
	 * @param selectedElementsSupplier	{@link Supplier} of the data, which is given to the {@link EventSource}
	 */
	public AbstractActionPanel(Supplier<List<T>> selectedElementsSupplier) {
		super("");
		this.selectedElementsSupplier = selectedElementsSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); 
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
	}
	
	/**
	 * Adds a UI-Element to this panel
	 * @param actions	{@link Component}s to be added
	 */
	protected void add(Component... actions) {
		Arrays.asList(actions).forEach(this::add);
	}

}
