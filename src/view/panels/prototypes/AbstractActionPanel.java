package view.panels.prototypes;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import view.ViewProperties;

@SuppressWarnings("serial") // should not be serialized
public abstract class AbstractActionPanel<T> extends DefaultPanel {

	protected Supplier<List<T>> selectedElementsSupplier;

	public AbstractActionPanel(Supplier<List<T>> selectedElementsSupplier) {
		super("");
		this.selectedElementsSupplier = selectedElementsSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); 
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
	}
	
	protected void add(Component... actions) {
		Arrays.asList(actions).forEach(this::add);
	}

}
