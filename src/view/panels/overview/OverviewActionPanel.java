package view.panels.overview;

import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;

import view.ViewProperties;
import view.panels.prototypes.DefaultPanel;

public abstract class OverviewActionPanel<T> extends DefaultPanel {
	private static final long serialVersionUID = 1L;

	protected Supplier<List<T>> selectedElementsSupplier;

	public OverviewActionPanel(Supplier<List<T>> selectedElementsSupplier) {
		super("");
		this.selectedElementsSupplier = selectedElementsSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
	}

}