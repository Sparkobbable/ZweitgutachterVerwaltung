package view.panels.overview;

import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;

import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

@SuppressWarnings("serial") // should not be serialized
public abstract class OverviewActionPanel<T> extends DefaultPanel {

	protected Supplier<List<T>> selectedElementsSupplier;

	public OverviewActionPanel(Supplier<List<T>> selectedElementsSupplier) {
		super("");
		this.selectedElementsSupplier = selectedElementsSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); 
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
	}

}
